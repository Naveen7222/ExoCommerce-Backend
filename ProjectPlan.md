# ExoCommerce Project Plan

## Architecture
  - Type: Microservices Architecture
  - Authentication: JWT (Spring Security)
  - Communication: REST APIs (JSON)
  - API Gateway: Spring Cloud Gateway
  - Service Discovery:  Eureka Server
  - Database per Service: MySQL (isolated per microservice)

## 💻 Tech Stack

### Backend
- Framework: Spring Boot 3.2.0
- Language: Java 17
- Build Tool: Maven 3.9.x
- Dependencies:
  - spring-boot-starter-web (3.2.0)
  - spring-boot-starter-data-jpa (3.2.0)
  - spring-boot-starter-security (3.2.0)
  - spring-boot-starter-validation (3.2.0)
  - mysql-connector-j (runtime)
  - lombok (optional)
  - spring-boot-starter-test (test)


### Frontend
- Framework: React 19.2.0
- Styling: Tailwind CSS 4.1.17
- State Management: Redux Toolkit 2.10.1
- HTTP Client: Axios 1.13.2
- Router: React Router 7.9.5
- Build Tool: Vite 7.2.2



### 🗓️ Notes / TODOs
  - [ ] Setup backend skeleton
  - [ ] Initialize React frontend
  - [ ] Connect frontend to backend
  - [ ] Add Docker support




  ### 📦 Version Reference
- Java: 17
- Maven: 3.9.x
- Node.js: 20.x
- npm: 10.x
- MySQL: 8.x