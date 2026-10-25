import axios from 'axios';

// Automatically uses relative '/api/v1' in production (single origin),
// or the environment variable VITE_API_BASE_URL in development.
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api/v1';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 60000, // 60s timeout for remote cloud LLM invocations
});

export const sendChatMessage = async (message, conversationId) => {
  const payload = {
    message,
    conversationId: conversationId || undefined,
  };
  const response = await apiClient.post('/helpdesk/chat', payload);
  return response.data;
};

export const getTickets = async (status = null) => {
  const params = status ? { status } : {};
  const response = await apiClient.get('/tickets', { params });
  return response.data;
};

export const getTicketStats = async () => {
  const response = await apiClient.get('/tickets/stats');
  return response.data;
};

export const getTicketById = async (id) => {
  const response = await apiClient.get(`/tickets/${id}`);
  return response.data;
};

export const getTicketsByEmail = async (email) => {
  const response = await apiClient.get('/tickets/user', {
    params: { email },
  });
  return response.data;
};

export const createTicket = async (ticketData) => {
  const response = await apiClient.post('/tickets', ticketData);
  return response.data;
};

export const updateTicket = async (id, ticketData) => {
  const response = await apiClient.put(`/tickets/${id}`, ticketData);
  return response.data;
};

export const updateTicketStatus = async (id, status) => {
  const response = await apiClient.patch(`/tickets/${id}/status`, { status });
  return response.data;
};

export const deleteTicket = async (id) => {
  const response = await apiClient.delete(`/tickets/${id}`);
  return response.data;
};

export default apiClient;
