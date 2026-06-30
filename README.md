PHK Bank — Internet Banking System
A desktop Internet Banking application built with Java Swing (UI) and JDBC/MySQL (backend), featuring separate portals for customers and administrators.

Java Swing MySQL

Features
Customer Portal
Secure login and registration with auto-generated account numbers
Dashboard with account overview, balance, and recent transactions
Balance inquiry
Fund transfers between accounts
Full transaction history
Deposit / withdraw funds
Profile management
Change password
Admin Portal
Separate secure admin login
Dashboard with account health stats (active vs. frozen accounts)
View and manage all registered customers
Freeze / activate customer accounts
View all transactions across the bank
Tech Stack
Language: Java
UI: Java Swing (custom-painted components, no external UI libraries)
Database: MySQL (via JDBC)
Architecture: DAO pattern (CustomerDAO, AccountDAO) separating UI from data access
Project Structure
src/
└── com/bank/
    ├── ui/
    │   ├── LoginFrame.java          # Customer login screen
    │   ├── RegisterFrame.java       # Customer registration screen
    │   ├── CustomerDashboard.java   # Customer portal (post-login)
    │   └── AdminDashboard.java      # Admin login + admin portal
    ├── dao/
    │   ├── CustomerDAO.java         # Customer DB operations (login, register, profile, etc.)
    │   └── AccountDAO.java          # Account/transaction DB operations
    ├── model/
    │   ├── Customer.java
    │   ├── Account.java
    │   └── Transaction.java
    └── util/
        └── DBConnection.java        # JDBC connection helper
Database Setup
Create a MySQL database, e.g. bank_db.
Create the required tables: customer, account, transaction, admin (with columns matching the fields used in Customer, Account, Transaction, and the admin login query in AdminDashboard).
Update your DB credentials in com.bank.util.DBConnection.
Insert at least one row into the admin table to be able to log in to the admin portal.
Getting Started
Prerequisites
JDK 8 or higher
MySQL Server
MySQL Connector/J (JDBC driver) on the classpath
Run
# Compile
javac -cp ".;mysql-connector-j-x.x.x.jar" -d bin src/com/bank/**/*.java

# Run
java -cp "bin;mysql-connector-j-x.x.x.jar" com.bank.ui.LoginFrame
On Linux/macOS, replace ; with : in the classpath.

The application launches maximized. From the login screen you can:

Log in as an existing customer
Register a new account
Click Admin Login to access the admin portal
Notes
All UI components are custom-painted (gradients, rounded buttons, etc.) to ensure consistent styling across operating systems and Look-and-Feels.
Account numbers are auto-generated on registration (ACC + 6-digit random number).
Currency is displayed in Indian Rupees (₹).
License
This project is open source. Feel free to use and modify it for learning or personal projects.
