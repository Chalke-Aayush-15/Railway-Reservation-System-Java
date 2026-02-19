# 🚂 Railway Reservation System

A comprehensive **Railway Management System** built with Java that enables users to book train tickets, manage reservations, and perform administrative tasks through a console-based interface.

---

## 📌 Table of Contents

- [About the Project](#about-the-project)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)
- [Author](#author)

---

## 📖 About the Project

The **Railway Reservation System** is a Java-based application designed to simulate the core functionalities of a real-world railway ticketing system. It allows passengers to search for trains, book tickets, cancel reservations, and check PNR status, while administrators can manage train schedules and passenger data.

---

## ✨ Features

### 👤 Passenger Features
- **User Registration & Login** — Secure account creation and authentication
- **Search Trains** — Find available trains between source and destination
- **Book Tickets** — Reserve seats in different classes (Sleeper, AC, General)
- **Cancel Reservation** — Cancel booked tickets and receive refund information
- **PNR Status Check** — View current reservation status using PNR number
- **View Booking History** — Access all past and current bookings

### 🛠️ Admin Features
- **Add / Remove Trains** — Manage train records in the system
- **View All Passengers** — Access complete passenger and booking details
- **Update Train Schedule** — Modify departure/arrival times and routes
- **Manage Seat Availability** — Update and track seat inventory per train

---

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| **Java (JDK 8+)** | Core application logic |
| **JDBC** | Database connectivity |
| **MySQL** | Data storage and management |
| **OOP Concepts** | Inheritance, Encapsulation, Polymorphism |
| **Collections Framework** | Data handling and manipulation |

---

## ✅ Prerequisites

Before running the project, ensure you have the following installed:

- [Java JDK 8+](https://www.oracle.com/java/technologies/downloads/)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [MySQL Connector/J (JDBC Driver)](https://dev.mysql.com/downloads/connector/j/)
- An IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/), or a terminal with `javac`

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Chalke-Aayush-15/Railway-Reservation-System-Java.git
cd Railway-Reservation-System-Java
```

### 2. Set Up the Database

Open MySQL and run the following:

```sql
CREATE DATABASE railway_db;
USE railway_db;
```

Then import the provided SQL schema file (if available):

```bash
mysql -u root -p railway_db < schema.sql
```

### 3. Configure Database Connection

Update the database credentials in the connection configuration file (e.g., `DBConnection.java` or `config.properties`):

```java
String url = "jdbc:mysql://localhost:3306/railway_db";
String username = "your_mysql_username";
String password = "your_mysql_password";
```

### 4. Add JDBC Driver

Place the MySQL Connector `.jar` file in the project's `lib/` directory and add it to your classpath.

### 5. Compile and Run

```bash
# Compile
javac -cp .:lib/mysql-connector-java.jar src/*.java -d out/

# Run
java -cp .:lib/mysql-connector-java.jar:out/ Main
```

> **Windows users:** Replace `:` with `;` in the classpath.

---

## 📁 Project Structure

```
Railway-Reservation-System-Java/
│
├── src/
│   ├── Main.java               # Entry point of the application
│   ├── Admin.java              # Admin functionalities
│   ├── Passenger.java          # Passenger functionalities
│   ├── Train.java              # Train model class
│   ├── Booking.java            # Booking/reservation logic
│   ├── DBConnection.java       # Database connection handler
│   └── ...
│
├── lib/
│   └── mysql-connector-java.jar
│
├── schema.sql                  # Database schema
├── README.md
└── .gitignore
```

---

## 💻 Usage

When you run the application, you will be presented with a main menu:

```
========================================
     RAILWAY RESERVATION SYSTEM
========================================
  1. Passenger Login
  2. New Passenger Registration
  3. Admin Login
  4. Exit
========================================
Enter your choice:
```

Follow the on-screen prompts to navigate through the system.

---

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature-name`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature-name`)
5. Open a Pull Request

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).

---

## 👨‍💻 Author

**Aayush Chalke**

- GitHub: [@Chalke-Aayush-15](https://github.com/Chalke-Aayush-15)

---

> ⭐ If you found this project helpful, please consider giving it a star on GitHub!
