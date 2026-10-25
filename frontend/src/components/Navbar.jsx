import React from 'react';
import { Bot, RefreshCw, LifeBuoy } from 'lucide-react';

export default function Navbar({ conversationId, onResetChat }) {
  return (
    <header className="bg-white border-b border-slate-200 sticky top-0 z-30">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
        <div className="flex items-center space-x-3">
          <div className="h-10 w-10 rounded-xl bg-gradient-to-tr from-indigo-600 to-violet-500 flex items-center justify-center text-white shadow-md shadow-indigo-100">
            <LifeBuoy className="w-5 h-5" />
          </div>
          <div>
            <h1 className="text-lg font-bold text-slate-900 tracking-tight flex items-center gap-2">
              Substring HelpDesk
              <span className="text-xs font-semibold px-2 py-0.5 rounded-full bg-indigo-50 text-indigo-700 border border-indigo-200/50">
                AI + MySQL
              </span>
            </h1>
            <p className="text-xs text-slate-500">Autonomous IT & Service Support System</p>
          </div>
        </div>

        <div className="flex items-center space-x-3">
          {conversationId && (
            <div className="hidden sm:flex items-center text-xs text-slate-500 bg-slate-100 px-3 py-1.5 rounded-lg border border-slate-200">
              <span className="w-2 h-2 rounded-full bg-emerald-500 mr-2 animate-pulse"></span>
              Session: <span className="font-mono ml-1 font-semibold text-slate-700">{conversationId.substring(0, 8)}...</span>
            </div>
          )}

          <button
            onClick={onResetChat}
            className="flex items-center space-x-1.5 text-xs font-medium text-slate-700 hover:text-indigo-600 bg-slate-100 hover:bg-slate-200/80 px-3 py-1.5 rounded-lg transition-colors"
            title="Start a fresh conversation"
          >
            <RefreshCw className="w-3.5 h-3.5" />
            <span>New Chat</span>
          </button>
        </div>
      </div>
    </header>
  );
}
