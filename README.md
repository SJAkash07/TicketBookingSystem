# Ticket Booking System (Java + Swing + MySQL)

This is a GUI-based Ticket Booking System built using Java Swing and MySQL. Users can book tickets for Train, Bus, or Flight, and all bookings are stored in a MySQL database via JDBC.

---

## Features

- Book tickets for Train, Bus, or Flight
- User login system with email and password
- Choose From and To destinations
- See real-time available seats
- Fare calculation based on mode and distance
- Validations for input (e.g., name, email, and routes)
- Ticket confirmation popup on successful booking
- Clean and aesthetic user interface with consistent design

---

## Technologies Used

- **Java** (Swing for GUI)
- **JDBC** (Java Database Connectivity)
- **MySQL** (Relational database)
- **SQL Workbench** (for schema management)

---

## Database Setup

1. **Create the database:**

```sql
CREATE DATABASE ticketdb;
```

2. **Create the `bookings` table:**

```sql
USE ticketdb;

CREATE TABLE bookings (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  mode VARCHAR(20),
  from_place VARCHAR(50),
  to_place VARCHAR(50),
  seat_no INT,
  distance INT,
  fare DOUBLE
);
```

3. **Create the `users` table (for login system):**

```sql
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL
);
```

---

## How to Run the Application

1. Ensure MySQL server is running.
2. Import the project into your IDE (e.g., Eclipse or IntelliJ).
3. Update the credentials in `DBUtil.java`:

```java
Connection conn = DriverManager.getConnection(
  "jdbc:mysql://localhost:3306/ticketdb", "root", "your_password");
```

4. Run the `Main.java` file to launch the application.

---

## Project Structure

```
src/
├── dao        
  BookingDAO.java
	DBUtil.java
	UserDAO.java	
├── model 
  Booking.java
├── ticket
	Main.java
└── ui
	BookingScreen.java
	LoginScreen.java
	AuthScreen.java

---

## Improvements from Previous Version

- Added a **Login Screen** with input validation and database check
- Integrated a **users table** for secure login authentication
- Enhanced UI using modern fonts, spacing, color palette, and hover effects
- Implemented dynamic **seat availability** logic
- Added **fare calculation** based on mode and distance
- Real-time **form validation** using regex and listeners
- Refactored code into **modular DAO and model structure**
