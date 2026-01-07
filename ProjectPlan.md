# ExoCommerce Project Plan

## Architecture
- Type: Microservices Architecture
- Authentication: JWT (Spring Security)
- Communication: REST APIs (JSON)
- API Gateway: Spring Cloud Gateway
- Service Discovery: Netflix Eureka Server
- Inter-service Communication: OpenFeign
- Load Balancing: Spring Cloud LoadBalancer
- Database per Service: MySQL (isolated per microservice)

---

## 💻 Tech Stack

### Backend
- Language: Java 17
- Framework: Spring Boot 3.2.0
- Build Tool: Maven 3.9.x
- Spring Cloud: 2023.0.x
- Dependencies:
    - spring-boot-starter-web (3.2.0)
    - spring-boot-starter-data-jpa (3.2.0)
    - spring-boot-starter-security (3.2.0)
    - spring-boot-starter-validation (3.2.0)
    - spring-boot-starter-actuator (3.2.0)
    - spring-cloud-starter-gateway
    - spring-cloud-starter-netflix-eureka-server
    - spring-cloud-starter-netflix-eureka-client
    - spring-cloud-starter-openfeign
    - spring-cloud-starter-loadbalancer
    - jjwt-api (0.11.5)
    - jjwt-impl (0.11.5)
    - jjwt-jackson (0.11.5)
    - mysql-connector-j (runtime)
    - lombok (optional)
    - spring-boot-starter-test (test)

---

### Frontend
- Framework: React 19.2.0
- Router: React Router DOM 7.9.5
- State Management: Redux Toolkit 2.10.1
- Styling: Tailwind CSS 4.1.17
- HTTP Client: Axios 1.13.2
- Authentication Handling: JWT (jwt-decode 4.0.0)
- Build Tool: Vite 7.2.2
- Linting: ESLint 9.x

**Frontend Overview:**
- JWT-based authentication flow
- Protected routes using React Router
- Global state handled via Redux Toolkit
- API communication through Axios
- Responsive UI using Tailwind CSS

---

## 🧩 Backend Services
- Eureka Service (Service Discovery)
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
