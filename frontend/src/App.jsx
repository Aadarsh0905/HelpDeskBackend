import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import Sidebar from './components/Sidebar';
import Chatbot from './components/Chat/Chatbot';
import Dashboard from './components/Dashboard/Dashboard';
import TicketList from './components/Tickets/TicketList';
import CreateTicketModal from './components/Tickets/CreateTicketModal';
import TicketDetailModal from './components/Tickets/TicketDetailModal';
import { getTicketStats, updateTicketStatus } from './services/api';

export default function App() {
  const [activeTab, setActiveTab] = useState('chat');
  const [conversationId, setConversationId] = useState(() => {
    return localStorage.getItem('helpdesk_conversation_id') || `session-${Date.now()}`;
  });
  const [openTicketCount, setOpenTicketCount] = useState(0);
  const [selectedTicket, setSelectedTicket] = useState(null);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [refreshTrigger, setRefreshTrigger] = useState(0);

  useEffect(() => {
    localStorage.setItem('helpdesk_conversation_id', conversationId);
  }, [conversationId]);

  const fetchStats = async () => {
    try {
      const stats = await getTicketStats();
      setOpenTicketCount(stats.openTickets + stats.inProgressTickets);
    } catch (err) {
      console.warn('Could not fetch initial stats:', err.message);
    }
  };

  useEffect(() => {
    fetchStats();
  }, [refreshTrigger]);

  const handleResetChat = () => {
    const newId = `session-${Date.now()}`;
    setConversationId(newId);
    localStorage.setItem('helpdesk_conversation_id', newId);
    window.location.reload();
  };

  const handleStatusChangeFromModal = async (ticketId, newStatus) => {
    try {
      await updateTicketStatus(ticketId, newStatus);
      setRefreshTrigger((prev) => prev + 1);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="min-h-screen flex flex-col bg-slate-50">
      <Navbar conversationId={conversationId} onResetChat={handleResetChat} />

      <div className="flex-1 max-w-7xl w-full mx-auto flex flex-col md:flex-row overflow-hidden">
        {/* Sidebar */}
        <Sidebar
          activeTab={activeTab}
          setActiveTab={(tab) => {
            if (tab === 'create') {
              setIsCreateModalOpen(true);
            } else {
              setActiveTab(tab);
            }
          }}
          openTicketCount={openTicketCount}
        />

        {/* Mobile Navigation */}
        <div className="md:hidden flex items-center justify-around bg-white border-b border-slate-200 px-3 py-2 text-xs">
          <button
            onClick={() => setActiveTab('chat')}
            className={`px-3 py-1.5 rounded-lg font-semibold ${
              activeTab === 'chat' ? 'bg-indigo-600 text-white' : 'text-slate-600'
            }`}
          >
            AI Assistant
          </button>
          <button
            onClick={() => setActiveTab('dashboard')}
            className={`px-3 py-1.5 rounded-lg font-semibold ${
              activeTab === 'dashboard' ? 'bg-indigo-600 text-white' : 'text-slate-600'
            }`}
          >
            Dashboard
          </button>
          <button
            onClick={() => setActiveTab('tickets')}
            className={`px-3 py-1.5 rounded-lg font-semibold ${
              activeTab === 'tickets' ? 'bg-indigo-600 text-white' : 'text-slate-600'
            }`}
          >
            Tickets
          </button>
          <button
            onClick={() => setIsCreateModalOpen(true)}
            className="px-3 py-1.5 rounded-lg font-semibold bg-indigo-50 text-indigo-700"
          >
            + New
          </button>
        </div>

        {/* Main Content Area */}
        <main className="flex-1 p-4 sm:p-6 lg:p-8 overflow-y-auto">
          {activeTab === 'chat' && (
            <Chatbot
              key={conversationId}
              conversationId={conversationId}
              setConversationId={setConversationId}
              onTicketCreated={() => {
                setRefreshTrigger((prev) => prev + 1);
              }}
            />
          )}

          {activeTab === 'dashboard' && (
            <Dashboard
              key={refreshTrigger}
              onViewAllTickets={() => setActiveTab('tickets')}
              onSelectTicket={(ticket) => setSelectedTicket(ticket)}
            />
          )}

          {activeTab === 'tickets' && (
            <TicketList
              key={refreshTrigger}
              onSelectTicket={(ticket) => setSelectedTicket(ticket)}
              onCreateNew={() => setIsCreateModalOpen(true)}
            />
          )}
        </main>
      </div>

      {/* Modals */}
      <CreateTicketModal
        isOpen={isCreateModalOpen}
        onClose={() => setIsCreateModalOpen(false)}
        onCreated={() => {
          setRefreshTrigger((prev) => prev + 1);
          setActiveTab('tickets');
        }}
      />

      <TicketDetailModal
        ticket={selectedTicket}
        onClose={() => setSelectedTicket(null)}
        onStatusChange={handleStatusChangeFromModal}
      />
    </div>
  );
}
