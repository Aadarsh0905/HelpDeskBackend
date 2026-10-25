import React, { useState, useEffect, useRef } from 'react';
import { Bot, User, Send, Sparkles, AlertCircle, Loader2 } from 'lucide-react';
import { sendChatMessage } from '../../services/api';

export default function Chatbot({ conversationId, setConversationId, onTicketCreated }) {
  const [messages, setMessages] = useState([
    {
      id: 'welcome',
      sender: 'assistant',
      text: "Hello! I am Liza, your Help Desk Assistant at Substring Technologies. How can I help you today? You can report an issue, inquire about an existing ticket, or ask any technical support questions.",
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    },
  ]);
  const [inputMessage, setInputMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const messagesEndRef = useRef(null);

  const quickPrompts = [
    "Check tickets for user aadarsh@gmail.com",
    "My laptop screen is flickering and I need help",
    "Wi-Fi connection keeps dropping on the 2nd floor",
    "What is the status of ticket #1?",
  ];

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages, loading]);

  const handleSend = async (messageToSend = null) => {
    const text = (messageToSend || inputMessage).trim();
    if (!text || loading) return;

    const userMsg = {
      id: Date.now().toString(),
      sender: 'user',
      text,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    };

    setMessages((prev) => [...prev, userMsg]);
    setInputMessage('');
    setLoading(true);
    setError(null);

    try {
      const data = await sendChatMessage(text, conversationId);
      if (data.conversationId && data.conversationId !== conversationId) {
        setConversationId(data.conversationId);
      }

      const assistantMsg = {
        id: (Date.now() + 1).toString(),
        sender: 'assistant',
        text: data.response || "I have processed your request.",
        timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      };

      setMessages((prev) => [...prev, assistantMsg]);

      if (
        data.response &&
        (data.response.toLowerCase().includes('ticket id') ||
          data.response.toLowerCase().includes('created a new ticket') ||
          data.response.toLowerCase().includes('updated'))
      ) {
        onTicketCreated?.();
      }
    } catch (err) {
      console.error('Chat error:', err);
      const errMsg =
        err.response?.data?.message ||
        err.message ||
        'Unable to reach the backend service. Please ensure the Spring Boot server and Ollama are running.';
      setError(errMsg);
      setMessages((prev) => [
        ...prev,
        {
          id: (Date.now() + 1).toString(),
          sender: 'assistant',
          isError: true,
          text: `⚠️ Error: ${errMsg}`,
          timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
        },
      ]);
    } finally {
      setLoading(false);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSend();
    }
  };

  return (
    <div className="flex flex-col h-[calc(100vh-8rem)] bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
      <div className="px-6 py-4 bg-gradient-to-r from-slate-900 via-indigo-950 to-slate-900 text-white flex items-center justify-between">
        <div className="flex items-center space-x-3">
          <div className="h-10 w-10 rounded-xl bg-indigo-500/20 border border-indigo-400/30 flex items-center justify-center text-indigo-300">
            <Bot className="w-6 h-6" />
          </div>
          <div>
            <h2 className="text-base font-semibold flex items-center gap-2">
              Liza <span className="text-xs px-2 py-0.5 rounded-full bg-emerald-500/20 text-emerald-300 border border-emerald-400/30">Online</span>
            </h2>
            <p className="text-xs text-slate-300">Substring Technologies AI Helpdesk Specialist</p>
          </div>
        </div>
        <div className="text-xs text-slate-400 hidden sm:block">
          Powered by Spring AI + Ollama Tool Calling
        </div>
      </div>

      <div className="flex-1 overflow-y-auto p-4 sm:p-6 space-y-4 chat-scroll bg-slate-50/50">
        {messages.map((msg) => {
          const isUser = msg.sender === 'user';
          return (
            <div
              key={msg.id}
              className={`flex items-start gap-3 ${isUser ? 'flex-row-reverse' : 'flex-row'}`}
            >
              <div
                className={`w-8 h-8 rounded-lg flex items-center justify-center flex-shrink-0 text-xs font-bold ${
                  isUser
                    ? 'bg-indigo-600 text-white'
                    : msg.isError
                    ? 'bg-rose-100 text-rose-700'
                    : 'bg-emerald-600 text-white'
                }`}
              >
                {isUser ? <User className="w-4 h-4" /> : <Bot className="w-4 h-4" />}
              </div>

              <div
                className={`max-w-[80%] rounded-2xl px-4 py-3 text-sm leading-relaxed ${
                  isUser
                    ? 'bg-indigo-600 text-white rounded-tr-none shadow-sm'
                    : msg.isError
                    ? 'bg-rose-50 text-rose-800 border border-rose-200 rounded-tl-none'
                    : 'bg-white text-slate-800 border border-slate-200 shadow-sm rounded-tl-none whitespace-pre-wrap'
                }`}
              >
                {msg.text}
                <div
                  className={`text-[10px] mt-1.5 font-medium ${
                    isUser ? 'text-indigo-200 text-right' : 'text-slate-400'
                  }`}
                >
                  {msg.timestamp}
                </div>
              </div>
            </div>
          );
        })}

        {loading && (
          <div className="flex items-start gap-3">
            <div className="w-8 h-8 rounded-lg bg-emerald-600 text-white flex items-center justify-center flex-shrink-0">
              <Bot className="w-4 h-4" />
            </div>
            <div className="bg-white border border-slate-200 rounded-2xl rounded-tl-none px-4 py-3 shadow-sm flex items-center space-x-2 text-slate-500 text-sm">
              <Loader2 className="w-4 h-4 animate-spin text-indigo-600" />
              <span>Liza is thinking, checking database, or updating tickets...</span>
            </div>
          </div>
        )}

        <div ref={messagesEndRef} />
      </div>

      <div className="px-4 py-2 bg-white border-t border-slate-100 flex items-center gap-2 overflow-x-auto text-xs">
        <span className="text-slate-400 flex items-center gap-1 flex-shrink-0 font-medium">
          <Sparkles className="w-3.5 h-3.5 text-amber-500" /> Suggestions:
        </span>
        {quickPrompts.map((prompt, i) => (
          <button
            key={i}
            onClick={() => handleSend(prompt)}
            disabled={loading}
            className="flex-shrink-0 bg-slate-100 hover:bg-indigo-50 hover:text-indigo-600 text-slate-600 px-3 py-1 rounded-full border border-slate-200/80 transition-all text-xs"
          >
            {prompt}
          </button>
        ))}
      </div>

      <div className="p-4 bg-white border-t border-slate-200">
        <form
          onSubmit={(e) => {
            e.preventDefault();
            handleSend();
          }}
          className="flex items-center space-x-2"
        >
          <input
            type="text"
            value={inputMessage}
            onChange={(e) => setInputMessage(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder="Type your message, issue, or ticket query here... (e.g. 'My email is not working')"
            disabled={loading}
            className="flex-1 bg-slate-50 border border-slate-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-100 rounded-xl px-4 py-3 text-sm text-slate-800 placeholder-slate-400 outline-none transition"
          />
          <button
            type="submit"
            disabled={!inputMessage.trim() || loading}
            className="bg-indigo-600 hover:bg-indigo-700 disabled:bg-slate-300 text-white px-5 py-3 rounded-xl font-medium text-sm transition shadow-sm shadow-indigo-100 flex items-center space-x-2"
          >
            <span>Send</span>
            <Send className="w-4 h-4" />
          </button>
        </form>
      </div>
    </div>
  );
}
