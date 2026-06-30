# Internet Banking System - Setup Guide

## Prerequisites
- Java JDK 17 or above
- IntelliJ IDEA (Community or Ultimate)
- MySQL Server 8.x
- MySQL Workbench (optional but recommended)

---

## STEP 1: Setup MySQL Database
1. Open MySQL Workbench or MySQL CLI
2. Run the file: `sql/banking_db.sql`
   - This creates the database, all tables, and sample data

---

## STEP 2: Open Project in IntelliJ IDEA
1. Open IntelliJ IDEA
2. Click "Open" → select the `InternetBankingSystem` folder
3. IntelliJ will detect it as a Maven project → click "Trust Project"
4. Wait for Maven to download dependencies (mysql-connector-j)

---

## STEP 3: Configure Database Password
1. Open: `src/main/java/com/bank/util/DBConnection.java`
2. Change: `private static final String PASSWORD = "your_password_here";`
   to your actual MySQL root password

---

## STEP 4: Add MySQL JDBC Driver (if not using Maven)
If you are NOT using Maven:
1. Download mysql-connector-j-8.3.0.jar from MySQL website
2. In IntelliJ: File → Project Structure → Libraries → + → Java
3. Select the downloaded JAR

---

## STEP 5: Run the Application
1. Open: `src/main/java/com/bank/ui/LoginFrame.java`
2. Right-click → "Run 'LoginFrame.main()'"

---

## Default Login Credentials
- Customer: john@example.com / pass123
- Admin: admin / admin123

---

## Project Structure
```
InternetBankingSystem/
├── pom.xml
├── sql/
│   └── banking_db.sql
└── src/main/java/com/bank/
    ├── model/
    │   ├── Customer.java
    │   ├── Account.java
    │   └── Transaction.java
    ├── dao/
    │   ├── CustomerDAO.java
    │   └── AccountDAO.java
    ├── ui/
    │   ├── LoginFrame.java
    │   ├── RegisterFrame.java
    │   ├── CustomerDashboard.java
    │   └── AdminDashboard.java  (contains AdminLoginFrame)
    └── util/
        └── DBConnection.java
```
