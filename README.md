# 🏦 PHK Bank - Internet Banking System

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java">
  <img src="https://img.shields.io/badge/MySQL-Database-blue?style=for-the-badge&logo=mysql">
  <img src="https://img.shields.io/badge/JDBC-Backend-success?style=for-the-badge">
  <img src="https://img.shields.io/badge/Java%20Swing-Desktop%20Application-red?style=for-the-badge">
  <img src="https://img.shields.io/badge/Status-Completed-brightgreen?style=for-the-badge">
</p>

## 📌 Overview

PHK Bank is a desktop Internet Banking System developed using Java Swing and JDBC with MySQL. It simulates real-world banking operations including customer registration, login, account management, fund transfer, deposits, withdrawals, and transaction history.

The application follows the DAO (Data Access Object) design pattern to separate business logic from database operations.

---

# ✨ Features

## 👤 Customer Module

- Customer Registration
- Secure Login
- Account Dashboard
- Deposit Money
- Withdraw Money
- Fund Transfer
- Balance Inquiry
- Transaction History
- Profile Management
- Change Password

---

## 👨‍💼 Admin Module

- Admin Login
- View All Customers
- Freeze/Activate Accounts
- View Bank Transactions
- Dashboard Statistics
- Customer Management

---

# 🛠 Tech Stack

| Technology | Usage |
|------------|-------|
| Java | Programming Language |
| Java Swing | User Interface |
| JDBC | Database Connectivity |
| MySQL | Database |
| IntelliJ IDEA | IDE |
| Git | Version Control |
| GitHub | Repository Hosting |

---

# 📂 Project Structure

```
InternetBankingSystem
│
├── sql
│   └── banking_db.sql
│
├── src
│   └── main
│       └── java
│           └── com.bank
│               ├── dao
│               ├── model
│               ├── ui
│               └── util
│
└── README.md
```

---

# 🗄 Database

Import

```
sql/banking_db.sql
```

into MySQL Workbench.

Update your database credentials inside

```
DBConnection.java
```

```java
String url = "jdbc:mysql://localhost:3306/banking_db";
String username = "root";
String password = "root";
```

---

# 🚀 Installation

### Clone Repository

```bash
git clone https://github.com/Pavul123/PHK-Bank.git
```

### Open Project

```
IntelliJ IDEA
```

### Configure Database

- Install MySQL
- Import banking_db.sql
- Update DB credentials

### Run Application

Run

```
LoginFrame.java
```

---

# 📸 Screenshots

Add screenshots here.

Example:

```
images/login.png
images/dashboard.png
images/admin.png
```

Then include them like:

```markdown
## Login Screen

![Login](images/login.png)

## Dashboard

![Dashboard](images/dashboard.png)
```

---

# 🏛 Architecture

```
User
   │
   ▼
Java Swing UI
   │
   ▼
DAO Layer
   │
   ▼
JDBC
   │
   ▼
MySQL Database
```

---

# 🔐 Security

- Login Authentication
- Account Validation
- Database Integrity
- Separate Admin Access

---

# 📈 Future Enhancements

- Spring Boot Backend
- REST APIs
- React Frontend
- JWT Authentication
- Email Notifications
- Mobile Banking App
- QR Payments
- Online Banking Portal

---

# 👨‍💻 Developer

**Pavul**

Java Full Stack Developer

GitHub:
https://github.com/Pavul123

---

# ⭐ Support

If you like this project,

⭐ Star this repository.

It motivates further development.

---

# 📜 License

This project is created for learning and educational purposes.
