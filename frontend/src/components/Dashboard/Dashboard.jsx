import React, { useEffect, useState } from 'react';
import {
  LifeBuoy,
  Clock,
  CheckCircle,
  AlertTriangle,
  RefreshCw,
  FolderOpen,
  ArrowUpRight,
} from 'lucide-react';
import { getTicketStats, getTickets } from '../../services/api';

export default function Dashboard({ onViewAllTickets, onSelectTicket }) {
  const [stats, setStats] = useState({
    totalTickets: 0,
    openTickets: 0,
    inProgressTickets: 0,
    resolvedTickets: 0,
    closedTickets: 0,
  });
  const [recentTickets, setRecentTickets] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadDashboardData = async () => {
    setLoading(true);
    try {
      const [statsData, ticketsData] = await Promise.all([
        getTicketStats(),
        getTickets(),
      ]);
      setStats(statsData);
      setRecentTickets(ticketsData.slice(0, 5));
    } catch (err) {
      console.error('Failed to load dashboard data:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDashboardData();
  }, []);

  const statCards = [
    {
      title: 'Total Tickets',
      value: stats.totalTickets,
      icon: FolderOpen,
      color: 'text-indigo-600',
      bg: 'bg-indigo-50',
      border: 'border-indigo-100',
    },
    {
      title: 'Open Issues',
      value: stats.openTickets,
      icon: AlertTriangle,
      color: 'text-amber-600',
      bg: 'bg-amber-50',
      border: 'border-amber-100',
    },
    {
      title: 'In Progress',
      value: stats.inProgressTickets,
      icon: Clock,
      color: 'text-blue-600',
      bg: 'bg-blue-50',
      border: 'border-blue-100',
    },
    {
      title: 'Resolved',
      value: stats.resolvedTickets,
      icon: CheckCircle,
      color: 'text-emerald-600',
      bg: 'bg-emerald-50',
      border: 'border-emerald-100',
    },
    {
      title: 'Closed',
      value: stats.closedTickets,
      icon: LifeBuoy,
      color: 'text-slate-600',
      bg: 'bg-slate-100',
      border: 'border-slate-200',
    },
  ];

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 bg-white p-6 rounded-2xl border border-slate-200 shadow-sm">
        <div>
          <h2 className="text-xl font-bold text-slate-900">Help Desk Operations Overview</h2>
          <p className="text-sm text-slate-500 mt-1">
            Real-time MySQL data integrated with Spring AI autonomous conversational memory.
          </p>
        </div>
        <button
          onClick={loadDashboardData}
          disabled={loading}
          className="flex items-center space-x-2 text-xs font-semibold text-indigo-600 bg-indigo-50 hover:bg-indigo-100 px-4 py-2.5 rounded-xl border border-indigo-200/60 transition"
        >
          <RefreshCw className={`w-3.5 h-3.5 ${loading ? 'animate-spin' : ''}`} />
          <span>Refresh Data</span>
        </button>
      </div>

      <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4">
        {statCards.map((card, i) => {
          const Icon = card.icon;
          return (
            <div
              key={i}
              className={`p-5 rounded-2xl bg-white border ${card.border} shadow-sm flex flex-col justify-between`}
            >
              <div className="flex items-center justify-between">
                <span className="text-xs font-semibold text-slate-500 uppercase tracking-wider">
                  {card.title}
                </span>
                <div className={`p-2 rounded-xl ${card.bg} ${card.color}`}>
                  <Icon className="w-4 h-4" />
                </div>
              </div>
              <div className="text-3xl font-extrabold text-slate-900 mt-4">
                {loading ? '...' : card.value}
              </div>
            </div>
          );
        })}
      </div>

      {stats.totalTickets > 0 && (
        <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm space-y-3">
          <div className="flex justify-between items-center text-sm font-semibold text-slate-700">
            <span>Ticket Resolution Pipeline</span>
            <span className="text-xs font-normal text-slate-500">
              {stats.resolvedTickets + stats.closedTickets} of {stats.totalTickets} resolved / closed
            </span>
          </div>
          <div className="w-full h-3 bg-slate-100 rounded-full overflow-hidden flex">
            <div
              style={{ width: `${(stats.openTickets / stats.totalTickets) * 100}%` }}
              className="bg-amber-500"
              title={`Open: ${stats.openTickets}`}
            ></div>
            <div
              style={{ width: `${(stats.inProgressTickets / stats.totalTickets) * 100}%` }}
              className="bg-blue-500"
              title={`In Progress: ${stats.inProgressTickets}`}
            ></div>
            <div
              style={{ width: `${(stats.resolvedTickets / stats.totalTickets) * 100}%` }}
              className="bg-emerald-500"
              title={`Resolved: ${stats.resolvedTickets}`}
            ></div>
            <div
              style={{ width: `${(stats.closedTickets / stats.totalTickets) * 100}%` }}
              className="bg-slate-400"
              title={`Closed: ${stats.closedTickets}`}
            ></div>
          </div>
          <div className="flex flex-wrap gap-4 text-xs pt-1">
            <div className="flex items-center gap-1.5">
              <span className="w-2.5 h-2.5 rounded-full bg-amber-500"></span>
              <span className="text-slate-600">Open ({stats.openTickets})</span>
            </div>
            <div className="flex items-center gap-1.5">
              <span className="w-2.5 h-2.5 rounded-full bg-blue-500"></span>
              <span className="text-slate-600">In Progress ({stats.inProgressTickets})</span>
            </div>
            <div className="flex items-center gap-1.5">
              <span className="w-2.5 h-2.5 rounded-full bg-emerald-500"></span>
              <span className="text-slate-600">Resolved ({stats.resolvedTickets})</span>
            </div>
            <div className="flex items-center gap-1.5">
              <span className="w-2.5 h-2.5 rounded-full bg-slate-400"></span>
              <span className="text-slate-600">Closed ({stats.closedTickets})</span>
            </div>
          </div>
        </div>
      )}

      <div className="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
        <div className="px-6 py-4 border-b border-slate-100 flex items-center justify-between">
          <h3 className="font-bold text-slate-800 text-sm">Recently Created Tickets</h3>
          <button
            onClick={onViewAllTickets}
            className="text-xs font-semibold text-indigo-600 hover:text-indigo-800 flex items-center gap-1"
          >
            <span>View All in Center</span>
            <ArrowUpRight className="w-3.5 h-3.5" />
          </button>
        </div>

        <div className="overflow-x-auto">
          {recentTickets.length === 0 ? (
            <div className="p-8 text-center text-sm text-slate-400">
              No tickets recorded in the MySQL database yet. Start a chat with Liza or click "Create Ticket" to get started!
            </div>
          ) : (
            <table className="w-full text-left text-xs">
              <thead className="bg-slate-50 text-slate-500 font-semibold border-b border-slate-100">
                <tr>
                  <th className="py-3 px-4">Ticket ID</th>
                  <th className="py-3 px-4">Summary</th>
                  <th className="py-3 px-4">User Email</th>
                  <th className="py-3 px-4">Priority</th>
                  <th className="py-3 px-4">Status</th>
                  <th className="py-3 px-4 text-right">Action</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100">
                {recentTickets.map((t) => (
                  <tr key={t.id} className="hover:bg-slate-50/80 transition">
                    <td className="py-3 px-4 font-mono font-semibold text-slate-700">#{t.id}</td>
                    <td className="py-3 px-4 max-w-xs truncate font-medium text-slate-900">{t.summary}</td>
                    <td className="py-3 px-4 text-slate-600">{t.email}</td>
                    <td className="py-3 px-4">
                      <span
                        className={`px-2 py-0.5 rounded-full font-bold ${
                          t.priority === 'URGENT'
                            ? 'bg-rose-100 text-rose-700'
                            : t.priority === 'HIGH'
                            ? 'bg-orange-100 text-orange-700'
                            : t.priority === 'MEDIUM'
                            ? 'bg-blue-100 text-blue-700'
                            : 'bg-slate-100 text-slate-700'
                        }`}
                      >
                        {t.priority}
                      </span>
                    </td>
                    <td className="py-3 px-4">
                      <span
                        className={`px-2 py-0.5 rounded-full font-bold ${
                          t.status === 'OPEN'
                            ? 'bg-amber-100 text-amber-700'
                            : t.status === 'IN_PROGRESS'
                            ? 'bg-blue-100 text-blue-700'
                            : t.status === 'RESOLVED'
                            ? 'bg-emerald-100 text-emerald-700'
                            : 'bg-slate-100 text-slate-600'
                        }`}
                      >
                        {t.status}
                      </span>
                    </td>
                    <td className="py-3 px-4 text-right">
                      <button
                        onClick={() => onSelectTicket(t)}
                        className="text-xs font-semibold text-indigo-600 hover:text-indigo-800"
                      >
                        Inspect
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </div>
  );
}
