# MediCall

## 1. Project Overview

---
During patient transport, issues arise when hospitals cannot admit patients or lack available medical staff, causing a loss of the critical "golden time".
MediCall allows paramedics to simply input patient condition and location; then, AI simultaneously contacts nearby hospital emergency rooms to
automatically recommend the first available hospital that responds.

### Other Parts
- [Atempo-Client](https://github.com/gdsc-konkuk/24-25-proj-Atempo-Client)
- [Atempo-AI](https://github.com/gdsc-konkuk/24-25-proj-Atempo-AI)

## 2. Tech Stack

---
### 🛠️ Backend Development
<p>
    <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logoColor=white">
    <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logoColor=white">
</p>

### 🗄️ Database & Caching
<p>
    <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
    <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
</p>

### 🔐 Authentication & Security
<p>
    <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white">
    <img src="https://img.shields.io/badge/OAuth2-EB5424?style=for-the-badge&logo=auth0&logoColor=white">
    <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
</p>

### 🔗 Communication Protocols
<p>
    <img src="https://img.shields.io/badge/HTTP-4285F4?style=for-the-badge&logoColor=white">
    <img src="https://img.shields.io/badge/SSE-1E90FF?style=for-the-badge">
</p>

### 🛠️ API Frameworks & Design
<p>
    <img src="https://img.shields.io/badge/REST API-3E4EE3?style=for-the-badge">
    <img src="https://img.shields.io/badge/Fast API-009688?style=for-the-badge&logo=fastapi&logoColor=white">
</p>

### 🌐 Third-Party Integration
<p>
    <img src="https://img.shields.io/badge/Twilio-F22F46?style=for-the-badge&logo=twilio&logoColor=white">
</p>

### 🧑‍💻 DevOps & Collaboration
<p>
    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
    <img src="https://img.shields.io/badge/GCP-4285F4?style=for-the-badge&logo=googlecloud&logoColor=white">
    <img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
    <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
</p>

### 📄 Documentation
<p>
    <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black">
</p>


## 3. Architecture

---
## BackEnd System Architecture
![Atempo_BackEnd_Architecture_v1.png](./docs/Atempo_BackEnd_Architecture_v1.png)

## Atempo Service Architecture
![Atempo_Service_Architecture_v1.png](./docs/Atempo_Service_Architecture_v1.png)

## 4. Service Flow

---
```
[Paramedics - Flutter App] 
  → Send patient condition and location
       ↓
[Server - Gemini AI] 
  → Select hospital list and generate guidance message
       ↓
[Twilio] 
  → Parallel calls; hospital responds with dial (1: Accept / 2: Reject)
       ↓
[Analyze response results + calculate distance]
       ↓
[Final hospital recommendation + Google Maps route guidance]
```

![Atempo_BackEnd_Service_Flow_v1.png](./docs/Atempo_BackEnd_Service_Flow_v1.png)

## 5. API Documentation

---
- [api-docs.json](./docs/api-docs.json)
- [Swagger UI](http://Avenir.my:8080/swagger-ui.html)

## 6. Database Schema

---
![Medicall_ERD.png](./docs/Medicall_ERD.png)
