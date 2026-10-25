import React from 'react';
import { X, Calendar, User, Tag, ShieldCheck } from 'lucide-react';

export default function TicketDetailModal({ ticket, onClose, onStatusChange }) {
  if (!ticket) return null;

  return (
    <div className="fixed inset-0 z-50 bg-slate-900/40 backdrop-blur-sm flex items-center justify-center p-4">
      <div className="bg-white w-full max-w-lg rounded-2xl border border-slate-200 shadow-xl overflow-hidden">
        <div className="px-6 py-4 border-b border-slate-100 flex items-center justify-between bg-slate-50">
          <div className="flex items-center gap-2">
            <span className="font-mono text-sm font-bold text-indigo-600 bg-indigo-50 px-2.5 py-1 rounded-lg border border-indigo-200/60">
              #{ticket.id}
            </span>
            <span className="text-xs font-semibold text-slate-500">Ticket Details</span>
          </div>
          <button
            onClick={onClose}
            className="text-slate-400 hover:text-slate-600 p-1 rounded-lg"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        <div className="p-6 space-y-4 text-xs">
          <div>
            <h4 className="text-base font-bold text-slate-900">{ticket.summary}</h4>
            <div className="flex items-center gap-2 mt-2">
              <span className="px-2 py-0.5 rounded-full font-bold bg-indigo-50 text-indigo-700 border border-indigo-200/50">
                {ticket.category || 'General'}
              </span>
              <span
                className={`px-2 py-0.5 rounded-full font-bold ${
                  ticket.priority === 'URGENT'
                    ? 'bg-rose-100 text-rose-700'
                    : ticket.priority === 'HIGH'
                    ? 'bg-orange-100 text-orange-700'
                    : ticket.priority === 'MEDIUM'
                    ? 'bg-blue-100 text-blue-700'
                    : 'bg-slate-100 text-slate-600'
                }`}
              >
                {ticket.priority} Priority
              </span>
            </div>
          </div>

          <div className="grid grid-cols-2 gap-3 p-3 bg-slate-50 rounded-xl border border-slate-200/70">
            <div className="flex items-center space-x-2 text-slate-600">
              <User className="w-4 h-4 text-slate-400" />
              <div>
                <div className="text-[10px] text-slate-400 uppercase font-semibold">Requester</div>
                <div className="font-medium text-slate-800">{ticket.email}</div>
              </div>
            </div>

            <div className="flex items-center space-x-2 text-slate-600">
              <ShieldCheck className="w-4 h-4 text-slate-400" />
              <div>
                <div className="text-[10px] text-slate-400 uppercase font-semibold">Current Status</div>
                <div className="font-bold text-indigo-600">{ticket.status}</div>
              </div>
            </div>

            <div className="flex items-center space-x-2 text-slate-600">
              <Calendar className="w-4 h-4 text-slate-400" />
              <div>
                <div className="text-[10px] text-slate-400 uppercase font-semibold">Created On</div>
                <div>{ticket.createdOn ? new Date(ticket.createdOn).toLocaleString() : '—'}</div>
              </div>
            </div>

            <div className="flex items-center space-x-2 text-slate-600">
              <Calendar className="w-4 h-4 text-slate-400" />
              <div>
                <div className="text-[10px] text-slate-400 uppercase font-semibold">Last Updated</div>
                <div>{ticket.updatedOn ? new Date(ticket.updatedOn).toLocaleString() : '—'}</div>
              </div>
            </div>
          </div>

          <div>
            <div className="font-semibold text-slate-700 mb-1">Description & Incident Notes:</div>
            <div className="bg-slate-50 p-3.5 rounded-xl border border-slate-200 text-slate-700 whitespace-pre-wrap leading-relaxed max-h-48 overflow-y-auto">
              {ticket.description || 'No description provided.'}
            </div>
          </div>

          <div className="pt-2 flex items-center justify-between border-t border-slate-100">
            <span className="font-semibold text-slate-600">Update Status:</span>
            <div className="flex items-center space-x-1.5">
              {['OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED'].map((st) => (
                <button
                  key={st}
                  onClick={() => {
                    onStatusChange(ticket.id, st);
                    onClose();
                  }}
                  className={`px-2.5 py-1 rounded-lg font-semibold text-[11px] transition ${
                    ticket.status === st
                      ? 'bg-indigo-600 text-white'
                      : 'bg-slate-100 hover:bg-slate-200 text-slate-700'
                  }`}
                >
                  {st}
                </button>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
