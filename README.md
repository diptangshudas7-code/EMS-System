# 🏢 Employee Management System (EMS)

  > **Case Study 2 — Advance Java Project**
  > Assignment No. 10 | Individual Submission

  A web-based **Employee Management System** built using **Java 17, Jakarta Servlets, JSP, JDBC (MySQL) and the JavaMail API**, designed to
  demonstrate authentication, CRUD operations, pagination/sorting, and email notifications — all deployed on **Apache Tomcat**.

  ---

  ## 📑 Table of Contents
  1. [Introduction](#-introduction)
  2. [Objectives](#-objectives)
  3. [Technology Stack](#-technology-stack)
  4. [Project Structure](#-project-structure)
  5. [Database Design](#-database-design)
  6. [Module-wise Description](#-module-wise-description)
     - [1. Login Authentication](#1-login-authentication)
     - [2. CRUD Operations with JDBC](#2-crud-operations-with-jdbc)
     - [3. Pagination and Sorting](#3-pagination-and-sorting)
     - [4. Email Notification](#4-email-notification)
     - [5. Deployment on Apache Tomcat](#5-deployment-on-apache-tomcat)
  7. [Setup & Installation](#-setup--installation)
  8. [Running the Application](#-running-the-application)
  9. [Screenshots / Walkthrough](#-screenshots--walkthrough)
  10. [Testing](#-testing)
  11. [Author](#-author)

  ---

  ## 📖 Introduction
  The **Employee Management System (EMS)** is a web application that enables two types of users:
  - **Employee** — logs in to view their personal profile (Name, Department, Salary, Email).
  - **Admin** — manages the complete employee database: create, read, update and delete records with pagination and column sorting.

  The application is built entirely on the **Jakarta EE** (formerly Java EE) platform and follows a clean **MVC-style architecture** (Servlet =
  Controller, JSP = View, DAO + Model = Data layer).

  ---

  ## 🎯 Objectives
  - Implement **role-based login authentication** using Servlets, JDBC and HTTP Sessions.
  - Perform **CRUD operations** on employee records with proper validation and exception handling.
  - Provide **pagination and column sorting** on the admin dashboard.
  - Trigger **email notifications** to employees using the **JavaMail API**.
  - Package the application as a **WAR file** and **deploy on Apache Tomcat**.

  ---

  ## 🛠 Technology Stack
  | Layer            | Technology                                    |
  |------------------|-----------------------------------------------|
  | Language         | Java 17                                       |
  | Web Framework    | Jakarta Servlet 6.0, JSP                      |
  | Build Tool       | Apache Maven 3.x                              |
  | Database         | MySQL 8.x (via `mysql-connector-j` 8.3.0)     |
  | Email            | Jakarta Mail 2.0.1 (formerly JavaMail)        |
  | Server           | Apache Tomcat 10+ (Jakarta EE 10 compatible)  |
  | Packaging        | WAR (Web Application Archive)                 |

  ---

  ## 📂 Project Structure
  ```
  Employee Management System/
  ├── pom.xml
  ├── README.md
  └── src/
      └── main/
          ├── java/com/ems/
          │   ├── dao/
          │   │   ├── EmployeeDAO.java        # CRUD + pagination + sort
          │   │   └── UserDAO.java            # Authentication
          │   ├── model/
          │   │   ├── Employee.java           # POJO
          │   │   └── User.java               # POJO (username, password, role)
          │   ├── servlet/
          │   │   ├── LoginServlet.java       #  /login
          │   │   ├── LogoutServlet.java      #  /logout
          │   │   ├── EmployeeListServlet.java#  /employees (list + paginate)
          │   │   ├── AddEmployeeServlet.java #  /addEmployee
          │   │   ├── UpdateEmployeeServlet.java #  /updateEmployee
          │   │   └── DeleteEmployeeServlet.java #  /deleteEmployee
          │   └── util/
          │       ├── DBConnection.java       # MySQL connection helper
          │       └── EmailUtil.java          # JavaMail helper
          └── webapp/
              ├── login.jsp
              ├── employeeDashboard.jsp
              ├── adminDashboard.jsp
              ├── addEmployee.jsp
              ├── editEmployee.jsp
              └── WEB-INF/
                  ├── web.xml
                  └── lib/
                      ├── mysql-connector-j-9.7.0.jar
                      └── jakarta.mail-2.0.1.jar
  ```

  ---

  ## 🗄 Database Design

  ### Create the database & tables
  ```sql
  CREATE DATABASE ems_db;
  USE ems_db;

  CREATE TABLE users (
      id INT AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR(50) NOT NULL,
      password VARCHAR(50) NOT NULL,
      role VARCHAR(50) NOT NULL          -- 'admin' or 'employee'
  );

  CREATE TABLE employees (
      id INT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      department VARCHAR(100),
      salary DOUBLE,
      email VARCHAR(100),
      username VARCHAR(50)
  );
  ```

  ### Sample seed data
  ```sql
  -- Admin
  INSERT INTO users VALUES (null, 'admin', 'admin123', 'admin');

  -- Employees
  INSERT INTO users VALUES (null, 'samay', 'samay123', 'employee');

  ## 🧩 Module-wise Description

  # 1. Login Authentication
  - `LoginServlet` (`/login`) accepts `username` and `password` via POST.
  - `UserDAO.authenticate(...)` runs a parameterised SQL query against the `users` table.
  - On success, an `HttpSession` is created with attributes `user` and `role`.
  - Role-based redirect:
    - `admin` → 'adminDashboard.jsp'
    - `employee` → `employeeDashboard.jsp`
  - On failure, the user is sent back to `login.jsp` with an "Invalid credentials" message.

  # 2. CRUD Operations with JDBC
  All DB calls go through `EmployeeDAO` using `PreparedStatement` (SQL-injection safe).

  | Operation | Servlet                       | DAO Method                  |
  |-----------|-------------------------------|-----------------------------|
  | Create    | `AddEmployeeServlet`          | `EmployeeDAO.add(emp)`      |
  | Read      | `EmployeeListServlet`         | `EmployeeDAO.getAll(...)`   |
  | Update    | `UpdateEmployeeServlet`       | `EmployeeDAO.update(emp)`   |
  | Delete    | `DeleteEmployeeServlet`       | `EmployeeDAO.delete(id)`    |
  | View Own  | `employeeDashboard.jsp`       | `EmployeeDAO.getByUsername` |

  - Validation: `NumberFormatException` for non-numeric salary/id is caught in servlets.
  - All exceptions are logged via `printStackTrace()` so DB errors are surfaced.

  # 3. Pagination and Sorting
  Implemented inside `EmployeeListServlet` + `EmployeeDAO.getAll(page, pageSize, sortBy)`.

  - Page size = 5 (configurable).
  - Total pages = `ceil(totalCount / pageSize)`.
  - Sortable columns = only `name`, `department`, `salary` are accepted (whitelisted in DAO to prevent SQL injection);
any other value falls back to `id`.
  - Column headers in `adminDashboard.jsp` are clickable links:
    ```html
    <a href="employees?page=1&sortBy=name">Name</a>
    <a href="employees?page=1&sortBy=department">Department</a>
    <a href="employees?page=1&sortBy=salary">Salary</a>
    ```
  - Pagination links at the bottom preserve the current `sortBy`.

  # 4. Email Notification
  - After a successful `Add`, `AddEmployeeServlet` calls:
    ```java
    EmailUtil.sendEmail(email, "Welcome to EMS",
        "Hi " + name + ", your employee profile has been created.");
    ```
  - `EmailUtil` uses **Gmail SMTP** (`smtp.gmail.com:587`) with **TLS** and an **App Password** (2FA must be enabled on the
sender's Gmail account).
  - The same helper can be invoked from `UpdateEmployeeServlet` for edit notifications.

  > ⚠️ Security note: The current `EmailUtil.java` has the App Password inline for academic/demo purposes. So for production, its
best to move the credentials to environment variables / JNDI.

  #  5. Deployment on Apache Tomcat
  1. Build the WAR:
     ```bash
     mvn clean package
     ```
     → produces `target/EmployeeManagementSystem-1.0.war`
  2. Copy the WAR to Tomcat:
     ```bash
     cp target/EmployeeManagementSystem-1.0.war $CATALINA_HOME/webapps/
     ```
  3. Start Tomcat:
     ```bash
     $CATALINA_HOME/bin/startup.bat     
     ```
  4. Open the browser(Chrome):
     ```
     http://localhost:8080/EmployeeManagementSystem-1.0/
     ```

  ---

  ## ⚙ Setup & Installation

  # Prerequisites
  - **JDK 17+**
  - **Apache Maven 3.6+**
  - **Apache Tomcat 10+** (Jakarta EE 10)
  - **MySQL Server 8.x**

  # Steps
  1. Clone / extract the project.
  2. Create the database using the SQL script in [Database Design](#-database-design).
  3. Configure DB credentials in `src/main/java/com/ems/util/DBConnection.java`:
     ```java
     private static final String URL  = "jdbc:mysql://localhost:3306/ems_db?...";
     private static final String USER = "root";
     private static final String PASS = "Arko2004";
     ```
  4. Configure email credentials in `src/main/java/com/ems/util/EmailUtil.java`:
     ```java
     String from = "diptangshudas7@gmail.com";
     String pass = "whwg yrlv exhv hmya"; 
     ```
  5. Build:
     ```bash
     mvn clean package
     ```

  ---

  ## ▶ Running the Application
  1. Deploy `target/EmployeeManagementSystem-1.0.war` to Tomcat.
  2. Visit `http://localhost:8080/EmployeeManagementSystem-1.0/`.
  3. Login with:
     - Admin: `admin` / `admin123`
     - Employee: `samay` / `samay123'

  ---

  ## 🖼 Screenshots / Walkthrough
  | Screen                | URL                                   |
  |-----------------------|---------------------------------------|
  | Login                 | `/login.jsp`                          |
  | Admin Dashboard       | `/employees?page=1&sortBy=name`       |
  | Add Employee          | `/addEmployee.jsp`                    |
  | Edit Employee         | `/editEmployee.jsp?id=...`            |
  | Employee Profile      | `/employeeDashboard.jsp`              |

  *(Add screenshots here before submission — drag images into the repo and link them.)*

  ---

  ## ✅ Testing
  | # | Test Case                                     | Expected Result                                | Status |
  |---|-----------------------------------------------|------------------------------------------------|--------|
  | 1 | Login with valid admin credentials            | Redirect to Admin Dashboard                    | ✅ Pass |
  | 2 | Login with valid employee credentials         | Redirect to Employee Dashboard                 | ✅ Pass |
  | 3 | Login with wrong password                      | "Invalid credentials" message on `login.jsp`   | ✅ Pass |
  | 4 | Add new employee                              | Email triggered; row appears in admin list     | ✅ Pass |
  | 5 | Edit employee                                 | Updated values reflected in list               | ✅ Pass |
  | 6 | Delete employee                               | Row removed from list                          | ✅ Pass |
  | 7 | Sort by Name / Department / Salary            | List re-orders correctly                       | ✅ Pass |
  | 8 | Pagination links (1, 2, 3, …)                 | Correct page slice displayed                   | ✅ Pass |
  | 9 | Direct URL access by non-admin                | Redirected to `login.jsp`                      | ✅ Pass |
  |10| Logout                                        | Session invalidated, redirected to login       | ✅ Pass |

  ---



  ## 👤 Author
  Name: Diptangshu Das
  Assignment: Case Study 2 — Employee Management System (Assignment No. 10)
  Course: Advance Java with Web Application 
  Team Type: Individual
