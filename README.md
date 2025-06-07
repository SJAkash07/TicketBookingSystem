
#  Ticket Booking System (Java + Swing + MySQL)

This is a GUI-based Ticket Booking System built using Java Swing and MySQL. Users can book tickets for Train, Bus, or Flight, and all bookings are stored in a MySQL database via JDBC.

---

## Features

-  Book tickets for Train, Bus, or Flight
-  Choose From and To destinations
-  See real-time available seats
-  Fare calculation based on mode and distance
-  Validations for input (e.g., name, email, and routes)
-  Login functionality with email & password
-  Ticket confirmation popup on successful booking
-  Clean and aesthetic user interface with consistent design

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
CREATE DATABASE ticket_booking_db;
```

2. **Create the table for bookings:**

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

## How to Run the Application

1. Ensure MySQL server is running.
2. Import the project into your IDE (e.g., Eclipse or IntelliJ).
3. Update the credentials in `DBUtil.java`:

```java
Connection conn = DriverManager.getConnection(
  "jdbc:mysql://localhost:3306/ticket_booking_db", "root", "your_password");
```

4. Run the `Main.java` file to launch the application.

---

## Project Structure

```
src/
├── com.ticket.ui          # GUI components (Login, Booking screen)
├── com.ticket.dao         # Data Access Objects for DB operations
├── com.ticket.module      # Booking model class
└── DBUtil.java            # Handles database connection setup
```

---

## Improvements from Previous Version

- Added a **Login Screen** with proper input validation and feedback
- Enhanced UI design using **modern fonts, padding, colors, and hover effects**
- Implemented dynamic **seat availability logic**
- Added **fare calculation** based on distance and mode
- Included **form validation** using regex and input listeners
- Separated logic into **modular DAO and model classes**
