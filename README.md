# Ticket Booking System (Java + MySQL)

This is a simple Java Swing project that lets users book tickets for Train, Bus, or Flight. Bookings are saved in a MySQL database using JDBC.

---

## Features

- Select mode of transport: Train, Bus, or Flight
- Choose From and To locations
- See available seats
- Calculate fare based on distance and transport mode
- Save bookings in the database
- Show ticket details in a popup

---

## Technologies Used

- Java (Swing)
- JDBC
- MySQL
- SQL Workbench

---

## Database Setup

1. **Create database** (in MySQL):

```sql
CREATE DATABASE ticket_booking_db;
```

2. **Create table**:

```sql
USE ticket_booking_db;

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

---

## How to Run

1. Make sure MySQL server is running.
2. Import the project into your IDE.
3. Update `DBUtil.java` with your database username and password:

```java
Connection conn = DriverManager.getConnection(
  "jdbc:mysql://localhost:3306/ticket_booking_db", "root", "your_password");
```

4. Run `Main.java` to start the app.

---

## Project Structure

```
src/
├── com.ticket.ui          --> GUI using Java Swing
├── com.ticket.dao         --> Database operations
├── com.ticket.model       --> Booking model class
└── DBUtil.java            --> MySQL connection setup
```
---