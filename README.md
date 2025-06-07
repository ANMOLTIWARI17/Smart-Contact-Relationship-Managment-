# 📇 Smart Contact Relationship Management System

A full-stack web application for managing personal or business contacts securely and efficiently. This system allows users to register, log in, and manage their contacts with features like image upload, search, and secure authentication.

---

## 🚀 Features

- User Registration and Login with Spring Security
- Role-based Authorization (Admin/User)
- Add, View, Update, Delete Contacts
- Upload and display profile pictures for contacts
- Search contacts by name or email
- Responsive UI with Tailwind CSS
- Contact Dashboard with quick stats
- Form validation and error handling
- Secure password handling with BCrypt

---

## 🛠 Tech Stack

*Backend:*
- Java
- Spring Boot
- Spring MVC
- Spring Security
- Hibernate / JPA

*Frontend:*
- Thymeleaf
- HTML5/CSS3
- Tailwind CSS

*Database:*
- MySQL

*Tools:*
- Maven
- Git & GitHub
- IntelliJ IDEA / VS Code
- Postman (for API testing)

---

## 📦 Project Setup Instructions

1. *Clone the Repository*

bash
git clone https://github.com/ANMOLTIWARI17/Smart-Contact-Relationship-Managment-.git
cd Smart-Contact-Relationship-Managment-

2. Database Setup

Create a new MySQL database named:
cmd --  smart_contact_manager;
Configure your application.properties file with your DB credentials.

#properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_contact_manager
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

3. Build & Run the Project
mvn clean install
mvn spring-boot:run
Application will run on: http://localhost:8080

🔐 Default Credentials (for demo)
Role	Username	Password
Admin	admin@gmail.com	admin123
User	user@gmail.com	user123

You can modify this as per your setup.

👤 Author
Anmol Tiwari
📧 Email: tanmol927@gmail.com
🔗 GitHub: @ANMOLTIWARI17

⭐ Show Your Support
If you like this project, please consider giving it a ⭐ on GitHub!
