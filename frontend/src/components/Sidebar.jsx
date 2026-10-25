import React from 'react';
import { Bot, LayoutDashboard, Ticket, PlusCircle, Database, Cloud } from 'lucide-react';

export default function Sidebar({ activeTab, setActiveTab, openTicketCount }) {
  const navItems = [
    { id: 'chat', label: 'AI Assistant (Liza)', icon: Bot, badge: null },
    { id: 'dashboard', label: 'Dashboard', icon: LayoutDashboard, badge: null },
    { id: 'tickets', label: 'Ticket Center', icon: Ticket, badge: openTicketCount > 0 ? openTicketCount : null },
    { id: 'create', label: 'Create Ticket', icon: PlusCircle, badge: null },
  ];

  return (
    <aside className="w-64 bg-white border-r border-slate-200 hidden md:flex flex-col justify-between p-4 flex-shrink-0">
      <div>
        <div className="px-3 py-2 text-xs font-bold uppercase tracking-wider text-slate-400">
          Navigation
        </div>
        <nav className="space-y-1 mt-2">
          {navItems.map((item) => {
            const Icon = item.icon;
            const isActive = activeTab === item.id;
            return (
              <button
                key={item.id}
                onClick={() => setActiveTab(item.id)}
                className={`w-full flex items-center justify-between px-3.5 py-2.5 rounded-xl text-sm font-medium transition-all ${
                  isActive
                    ? 'bg-indigo-600 text-white shadow-sm shadow-indigo-200'
                    : 'text-slate-600 hover:bg-slate-100 hover:text-slate-900'
                }`}
              >
                <div className="flex items-center space-x-3">
                  <Icon className={`w-4 h-4 ${isActive ? 'text-white' : 'text-slate-500'}`} />
                  <span>{item.label}</span>
                </div>
                {item.badge !== null && (
                  <span
                    className={`text-xs px-2 py-0.5 rounded-full font-bold ${
                      isActive ? 'bg-indigo-700 text-white' : 'bg-indigo-100 text-indigo-700'
                    }`}
                  >
                    {item.badge}
                  </span>
                )}
              </button>
            );
          })}
        </nav>
      </div>

      <div className="bg-slate-50 rounded-xl p-3.5 border border-slate-200">
        <div className="flex items-center space-x-2 text-xs font-semibold text-slate-700 mb-2">
          <Cloud className="w-4 h-4 text-emerald-600" />
          <span>System Architecture</span>
        </div>
        <div className="space-y-1.5 text-xs text-slate-500">
          <div className="flex justify-between items-center">
            <span>Database</span>
            <span className="font-semibold text-emerald-600 flex items-center gap-1">
              <span className="w-1.5 h-1.5 rounded-full bg-emerald-500 animate-pulse"></span> TiDB Cloud (AWS)
            </span>
          </div>
          <div className="flex justify-between items-center">
            <span>Cluster / DB</span>
            <span className="font-semibold text-slate-700">banking-system / helpdesk</span>
          </div>
          <div className="flex justify-between items-center">
            <span>LLM Model</span>
            <span className="font-semibold text-slate-700">Ollama (llama3.1)</span>
          </div>
          <div className="flex justify-between items-center">
            <span>Chat Memory</span>
            <span className="font-semibold text-slate-700">TiDB Relational JDBC</span>
          </div>
        </div>
      </div>
    </aside>
  );
}
