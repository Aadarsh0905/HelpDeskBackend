# 🎧 Substring HelpDesk - Full-Stack AI-Powered Support System

A complete full-stack Help Desk application combining **Spring Boot 4 / Spring AI**, **TiDB Cloud (Serverless MySQL)**, **Ollama (`llama3.1`)**, and **React 18 (Vite + Tailwind CSS)**.

---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                 React Frontend (Vite)                       │
│  - AI Chatbot UI (Liza) with Conversational State           │
│  - Real-time Operations Dashboard (Metrics & Analytics)     │
│  - Ticket Management Center (CRUD, Filtering, Status Patch) │
└───────────────────────────┬─────────────────────────────────┘
                            │ REST / JSON (Port 8080)
┌───────────────────────────▼─────────────────────────────────┐
│              Spring Boot Backend (Java 21)                  │
│  - AiController (/api/v1/helpdesk/chat, /response, /stream) │
│  - TicketController (/api/v1/tickets CRUD)                  │
│  - Spring AI ChatClient with MessageChatMemoryAdvisor       │
│  - Autonomous Tools: TicketDatabaseTool, EmailTool          │
└───────────────┬─────────────────────────────┬───────────────┘
                │                             │
┌───────────────▼───────────────────────────┐ ┌───────────────▼───────────────┐
│  TiDB Cloud (AWS Tokyo) MySQL Cluster     │ │    Local Ollama Engine        │
│  Cluster: banking-system-db               │ │ - Model: `llama3.1`           │
│  Database: `helpdesk`                     │ │ - Autonomous Function         │
│  - `help_desk_ticket` table               │ │   / Tool Calling              │
│  - `SPRING_AI_CHAT_MEMORY` (Relational)   │ └───────────────────────────────┘
└───────────────────────────────────────────┘
```

---

## 🗄️ Database: TiDB Cloud Details

* **Cluster**: `banking-system-db`
* **Host**: `gateway01.ap-northeast-1.prod.aws.tidbcloud.com`
* **Port**: `4000`
* **Database**: `helpdesk`
* **Username**: `34f53Yxe3VE5yJg.root`
* **TLS / SSL**: Enabled (`sslMode=VERIFY_IDENTITY&enabledTLSProtocols=TLSv1.2,TLSv1.3`)

---

## 🚀 Quick Start Guide

### 1. Prerequisites
- **Java 21** & **Maven 3.8+** (or Eclipse IDE / STS)
- **Node.js 18+** & **npm**
- **Ollama** running locally with `llama3.1` (`ollama run llama3.1`)

---

### 2. Start the Spring Boot Backend
In Eclipse / STS:
- Right-click project `helpdesk-backend` -> **Run As** -> **Spring Boot App** (or run `HelpdeskBackendApplication.java`).

Or using Maven CLI:
```bash
mvn clean spring-boot:run
```
The backend starts at **`http://localhost:8080`** and connects automatically to your TiDB Cloud database.

---

### 3. Start the React Frontend (Development Mode)
```bash
cd helpdesk-frontend
npm install
npm run dev
```
Open **`http://localhost:5173`**.

---

## ☁️ 1-Click Deployment to Render

1. Push your repository to GitHub:
   ```bash
   git add .
   git commit -m "Configure TiDB Cloud database and Render blueprint"
   git push origin main
   ```
2. In [Render.com](https://render.com), click **New +** -> **Blueprint**.
3. Select your repository.
4. Render automatically reads `render.yaml`. The TiDB Cloud connection is **pre-configured**.
5. When prompted for `SPRING_AI_OLLAMA_BASE_URL`, enter your Ngrok tunnel URL (e.g. `https://xxxx.ngrok-free.app` after running `ngrok http 11434` locally).
6. Click **Apply**. Render builds the React frontend, packages the Spring Boot jar, and deploys everything into a single URL!
