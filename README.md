# Blogify – Spring Boot Web Application

## 📌 Project Overview
This is a beginner-friendly Spring Boot web application that demonstrates
user registration, login, authentication, and role-based access control.
The project follows the MVC (Model-View-Controller) architecture and
uses modern Spring technologies.

---

## 🛠️ Technologies Used and Their Role

### 🔹 Spring Boot
Spring Boot is used to create and run the application quickly without
manual configuration. It provides an embedded Tomcat server and
auto-configuration support.

### 🔹 Spring MVC
Spring MVC handles HTTP requests and responses.
Controllers manage user requests and return views to the browser.

### 🔹 Spring Data JPA
Spring Data JPA is used to interact with the database.
It eliminates the need to write SQL queries and provides repository
interfaces for CRUD operations.

### 🔹 Hibernate
Hibernate is the JPA implementation used in this project.
It maps Java objects (entities) to database tables and automatically
generates SQL queries.

### 🔹 H2 Database
H2 is an in-memory database used for development and testing.
It allows quick setup without installing an external database.

### 🔹 Spring Security
Spring Security is used to handle authentication and authorization.
It manages login, logout, password encryption, and protects secured URLs.

### 🔹 BCrypt Password Encoder
Passwords are encrypted using BCrypt to ensure secure storage of
user credentials in the database.

### 🔹 Thymeleaf
Thymeleaf is a server-side template engine used to render dynamic HTML pages.
It integrates seamlessly with Spring MVC and Spring Security.

---

## 🧩 Application Architecture

The application follows a layered architecture:

- Controller Layer: Handles HTTP requests
- Service Layer: Contains business logic
- Repository Layer: Interacts with the database
- Model Layer: Represents database entities

---

## 🔐 Security Overview

- Users register and login using email and password
- Passwords are stored in encrypted form
- Role-based authorization is implemented using `ROLE_USER`
- Protected pages can be accessed only after authentication

---

## 🗄️ Database Overview

- User information is stored in the `account` table
- Hibernate automatically creates and manages database tables
- H2 Console is enabled for easy database inspection

---

## ✅ Conclusion
This project demonstrates the fundamentals of Spring Boot web development,
including MVC architecture, database integration, security, and template rendering.
It serves as a strong foundation for building real-world Java web applications.


---

## Some points added in Hinglish for better understanding
Spring Security ek powerful framework hai jo Java applications mein authentication (login) aur authorization (access control) provide karta hai. 
Iska use hum tab karte hain jab hume apne web application ki security ko ensure karna hota hai, jaise ki user login, role-based access control, etc.

JPA ek Java standard hai jo Java objects ko database tables ke saath connect karta hai, taaki hume SQL manually na likhna pade. 
Iska use hum tab karte hain jab hume apne Java application mein database operations ko asaani se manage karna hota hai.

JDBC mai hum directly SQL queries likhte hain database se data retrieve ya manipulate karne ke liye. 
JPA mai hum Java objects ke through database operations karte hain, jisse code zyada readable aur maintainable ho jata hai.

Hibernate Java objects aur database ke beech translator hai. Java object ko table me convert karna. Table ke data ko Java object me laana.SQL automatically likhna. 
Hibernate JPA ka ek implementation hai jo JPA specifications ko follow karta hai aur additional features provide karta hai.

JPA = Interface
Hibernate = Class jo interface implement karta hai

Thymeleaf ek Server-Side Template Engine hai (Java/Spring ke liye).HTML pages ko dynamic banana. Java data ko HTML me inject karna.

Thymeleaf = HTML + Java data

Thyemeleaf aur jsp me ye fark hai ki Thymeleaf modern, clean aur userfriendly syntax hai aur ismai java code directly html me likh sakte hai jabki jsp me java code alag se likhna padta hai using scriptlets.

