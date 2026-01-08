# 📝 Blogify – Blogging Platform

Blogify is a **production-ready blogging platform** built using **Spring Boot**, **Spring Security**, **Hibernate**, **Thymeleaf**, and **MySQL**.  
It supports **authentication, role-based access control, rich-text posts with images, admin moderation, and cloud image storage**.

🚀 **Live Demo:** *https://blogify-springboot.up.railway.app/*  
📦 **Tech Stack:** Java, Spring Boot, Spring Security, Hibernate, Thymeleaf, MySQL, Cloudinary, Bootstrap

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
## ✨ Features

### 👤 User Features
- User registration & login
- Secure authentication using **Spring Security**
- Profile management with profile photo upload
- Create, edit, and delete blog posts
- Rich-text editor with image uploads (CKEditor)
- Password reset via email
- Responsive UI

### 🛡 Admin Features
- Admin dashboard
- View all users & posts
- Delete any post
- Role-based access control (USER / EDITOR / ADMIN)

### 🖼 Image Handling
- Image upload via **Cloudinary**
- Automatic image resizing & compression
- Cloud image cleanup when post is deleted

### ⚙ System Features
- Role-based authorization
- Secure password hashing (BCrypt)
- Environment-based configuration
- Database seeding for initial users & posts
- Production-ready logging setup

---

## 🧰 Tech Stack

| Layer | Technology |
|-----|-----------|
| Backend | Java 21, Spring Boot |
| Security | Spring Security |
| ORM | Hibernate / JPA |
| Database | MySQL (Production), H2 (Dev) |
| Frontend | Thymeleaf, Bootstrap |
| Image Storage | Cloudinary |
| Editor | CKEditor |
| Build Tool | Maven |
| Deployment | Railway |

---


## 🧩 Application Architecture

The application follows a layered architecture:

- Controller Layer: Handles HTTP requests
- Service Layer: Contains business logic
- Repository Layer: Interacts with the database
- Model Layer: Represents database entities

---

## 🔐 Security Overview

-	Password hashing using BCrypt
-	Role-based authorization with @PreAuthorize
-	Only owners or admins can edit/delete posts
-	Secure password reset tokens with expiry
-	CSRF protection enabled

---

## 🗄️ Database Overview

- User information is stored in the `account` table
- Hibernate automatically creates and manages database tables
- H2 Console is enabled for easy database inspection

---

## 🖼 Image Upload Flow

- Upload via CKEditor
- Stored on Cloudinary
- Resized & compressed automatically
- Deleted from Cloudinary when post is removed

---

## ✅ Conclusion
This project demonstrates the fundamentals of Spring Boot web development,
including MVC architecture, database integration, security, and template rendering.
It serves as a strong foundation for building real-world Java web applications.

---

## 🔧 Prerequisites to run this 

- Java 21
- Maven 3.9+
- MySQL 8+
- Git

---

## 🛠 Environment Setup

### 1️⃣ Clone the Repository
```
git clone https://github.com/your-username/Blogify.git
cd Blogify
```
### 2️⃣ Configure application.properties
```
spring.application.name=Blogify
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/blogify
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

cloudinary.cloud-name=YOUR_CLOUD_NAME
cloudinary.api-key=YOUR_API_KEY
cloudinary.api-secret=YOUR_API_SECRET

spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_EMAIL_PASSWORD

```

### ▶️ Running the Application
#### Using Maven 
```./mvnw spring-boot:run
```
Visit 👉 http://localhost:8080

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

