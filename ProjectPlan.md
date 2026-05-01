# ExoCommerce Project Plan

## Architecture
- Type: Microservices Architecture
- Authentication: JWT (Spring Security)
- Communication: REST APIs (JSON)
- API Gateway: Spring Cloud Gateway
- Service Discovery: Netflix Eureka Server
- Database per Service: MySQL (isolated per microservice)

---

## 💻 Tech Stack

### Backend
- Language: Java 17
- Framework: Spring Boot 3.2.0
- Build Tool: Maven 3.9.x
- Spring Cloud: 2023.0.x

### Core Dependencies:
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-cloud-starter-gateway
- spring-cloud-starter-netflix-eureka-server
- spring-cloud-starter-netflix-eureka-client
- jjwt (JWT handling)
- mysql-connector-j
- lombok

---

### Frontend
- Framework: React 19.2.0
- Router: React Router DOM 7.9.5
- Styling: Tailwind CSS 4.1.17
- HTTP Client: Axios 1.13.2
- Authentication Handling: JWT (jwt-decode 4.0.0)
- Build Tool: Vite 7.2.2
- Linting: ESLint 9.x

**Frontend Overview:**
- JWT-based authentication flow
- Protected routes using React Router
- Global cart state managed using Context API
- Authentication state handled using localStorage
- API communication through Axios with interceptors
- Responsive UI using Tailwind CSS

---

## 🧩 Backend Services
- Eureka Server (Service Discovery)
- API Gateway
- Auth Service
- User Service
- Product Service
- Cart Service
- Order Service

---

## 🔗 GitHub Repositories
- Backend Repository:  
  https://github.com/Naveen7222/ExoCommerce-Backend

- Frontend Repository:  
  https://github.com/Naveen7222/ExoCommerce-Frontend

---

## 🗓️ Notes / TODOs
- [x] Setup backend services
- [x] Configure Eureka Server
- [x] Implement API Gateway routing
- [x] JWT-based authentication
- [x] Role-based authorization
- [x] Frontend integration with backend
- [ ] Docker support (optional)

---

## 📦 Version Reference
- Java: 17
- Spring Boot: 3.2.0
- Spring Cloud: 2023.0.x
- Maven: 3.9.x
- Node.js: 20.x
- npm: 10.x
- MySQL: 8.x