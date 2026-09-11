# Customer Feedback System - Spring Boot

This project implements the requirements from the supplied Customer Feedback document:

- User registration
- User login
- User can add feedback
- User can view previously submitted feedback
- User can edit their own feedback
- Admin dashboard
- Admin can CRUD feedback (create is represented by user submission; admin has read/update/delete management)
- MariaDB persistence
- Spring Security authentication and role-based authorization

## Technology

- Java 17
- Spring Boot
- Spring MVC + Thymeleaf
- Spring Data JPA / Hibernate
- Spring Security
- MariaDB
- Maven

## 1. Create database

Run in MariaDB:

```sql
CREATE DATABASE customer_feedback;
```

The application uses:

- database: `customer_feedback`
- username: `root`
- password: empty by default

If your MariaDB password is not empty, edit:

`src/main/resources/application.properties`

## 2. Run

```bash
mvn spring-boot:run
```

Or run `CustomerFeedbackApplication` from IntelliJ/Eclipse.

Open:

http://localhost:8080

## 3. Admin login

A default admin is created automatically on first startup:

- Email: admin@gmail.com
- Password: admin123

Change this password for a real deployment.

## 4. User flow

1. Open `/register`
2. Create a user account
3. Login
4. Add feedback
5. View submitted feedback
6. Edit feedback

## 5. Admin flow

Login with the admin account and open:

`/admin`

Admin can view, edit, and delete all feedback.

## Database tables

JPA automatically creates/updates:

- `users`
- `feedback`

Relationship:

`User 1 ---- * Feedback`

The implementation follows the supplied PDF requirements. The PDF specifically describes login, registration, adding/viewing/editing feedback, and an admin dashboard with CRUD operations. 
