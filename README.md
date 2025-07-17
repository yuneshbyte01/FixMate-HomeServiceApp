# FixMate - Home Service App (Backend)

A backend system built using **Spring Boot** to manage home service requests, allowing users to book electricians, plumbers, and other professionals. Includes admin dashboards for managing services and bookings.

---

## 🚀 Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Spring DevTools
- Spring Security

---

## 🧰 Features Implemented

### User Authentication
- Register/Login with role: `USER` or `ADMIN`
- Passwords securely stored with BCrypt
- Role-based access control using Spring Security

### Service Type Management (Admin)
- Add, update, delete service categories (e.g., Electrician, Plumber)
- View available service types (public)

### Service Booking (User)
- Users can book services by selecting type, date, time, and description
- Bookings default to `PENDING` status
- Users can view their own bookings

### Admin Booking Dashboard
- Admin can view all bookings
- Admin can update booking status: `ACCEPTED`, `REJECTED`, `COMPLETED`

---

## 🛠️ How to Run

### 1. Clone the repo
`git clone https://github.com/yuneshbyte01/FixMate-HomeServiceApp.git`

### 2. Create MySQL DB:
`CREATE DATABASE fixmate_db;`

### 3. Update `application.properties` with your DB credentials
    spring.datasource.url=jdbc:mysql://localhost:3306/fixmate_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password

### 4. Run the application
`mvn spring-boot:run` or from your IDE

### 5. Test endpoint:
`GET http://localhost:8080/api/ping`

---

## 📂 Project Structure (Backend)
    com.fixmate
    ├── controller         # REST endpoints (auth, services, bookings)
    ├── model              # Entity & Enum classes
    ├── repository         # Spring Data JPA interfaces
    ├── service            # Business logic
    ├── security           # Spring Security config and UserDetails
    └── FixMateApplication.java

---

## 📂 Project Structure (Backend)
    com.fixmate
    ├── controller         # REST endpoints (auth, services, bookings)
    ├── model              # Entity & Enum classes
    ├── repository         # Spring Data JPA interfaces
    ├── service            # Business logic
    ├── security           # Spring Security config and UserDetails
    └── FixMateApplication.java

---
