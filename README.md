# Parking Lot Management System

## Overview

This is a Java-based Parking Lot Management System developed for our Object Oriented Programming coursework.

The main idea of the project is to create a simple system that can handle the basic operations of a parking lot, such as vehicle entry, parking space allocation, reservations, payments, vehicle exit, and finding a parked vehicle.

The system is made as a desktop application using Java Swing. For this version, keyboard inputs are used to simulate the real-world systems that could later use things like number plate scanners, parking sensors, QR scanners, and databases.

---

## Main Features

### Vehicle Entry

* Enter the vehicle owner's name.
* Enter the vehicle number.
* Select the vehicle type.
* The system identifies the vehicle class.
* An available parking space is automatically assigned.
* The system shows the assigned space and floor.

### Parking Space Management

* Parking spaces are divided according to vehicle class.
* The system keeps track of free, occupied, and reserved spaces.
* Different vehicle types are directed to suitable parking areas.

### Reservations

* Users can reserve a parking space before arriving.
* A reservation includes:

  * Name
  * Vehicle number
  * Vehicle type
  * Start date and time
  * End date and time
* The system calculates the reservation price.
* Reserved vehicles are given priority when they enter.
* The system also handles reservation expiry and no-shows.

### Payments

* Payments can be made for parked vehicles.
* Different payment methods are available.
* The system calculates the amount that needs to be paid.
* Payment records are stored while the program is running.

### Vehicle Exit

* A vehicle number is entered to leave the parking lot.
* The system checks whether the payment has been completed.
* A vehicle can only exit after the required payment is confirmed.

### Find Vehicle

* Users can search for a parked vehicle.
* The system uses the vehicle information to locate it.
* The parking location is shown to the user.

### Management

The management section allows staff to view the current system information, including:

* Parking spaces
* Reservations
* Payments
* Vehicle history
* Entry and exit times

The management data can also be refreshed manually, while some information is updated automatically.

---

## Project Structure

The project is divided into different packages so that each part of the system has its own responsibility.

```text
parking-lot-management
│
└── src
    └── parking
        │
        ├── Main.java
        │
        ├── core
        │   ├── ParkingLot.java
        │   ├── ParkingSpace.java
        │   ├── Vehicle.java
        │   └── VehicleClass.java
        │
        ├── reservation
        │   ├── Reservation.java
        │   └── ReservationManager.java
        │
        ├── payment
        │   ├── Payment.java
        │   ├── PaymentManager.java
        │   └── PaymentMethod.java
        │
        ├── location
        │   └── VehicleLocator.java
        │
        └── gui
            ├── MainWindow.java
            ├── ParkingWindow.java
            ├── ServiceWindow.java
            ├── ManagementWindow.java
            │
            ├── EntryPanel.java
            ├── PaymentPanel.java
            ├── ExitPanel.java
            ├── ReservationPanel.java
            └── FindVehiclePanel.java
```

### Package Responsibilities

**core**
Contains the main parking lot objects such as vehicles, parking spaces, and the parking lot itself.

**reservation**
Handles creating, storing, and managing parking reservations.

**payment**
Handles payment creation, payment methods, and payment status.

**location**
Handles finding the location of a parked vehicle.

**gui**
Contains all the Swing windows and panels used to interact with the system.

---

## Basic System Flow

```text
                    Main
                     │
                     ▼
              MainWindow
                     │
        ┌────────────┼────────────┐
        ▼            ▼            ▼
     Parking      Services    Management
    Operations
        │            │            │
   ┌────┼────┐    ┌──┴────┐    ┌──┴────────────────┐
   ▼    ▼    ▼    ▼       ▼    ▼       ▼      ▼
 Entry Payment Exit Reserve Find  Spaces Reservations
                              Vehicle  Payments History
```

The different parts of the system share the same `ParkingLot`, `ReservationManager`, and `PaymentManager` objects. This allows changes made in one part of the application to be reflected in the other parts.

---

## Technologies Used

* Java
* Java Swing
* Object-Oriented Programming
* Git & GitHub

The current version does not use a database. Data is stored in the Java objects while the application is running.

---

## OOP Concepts Used

The project was designed around the main OOP concepts we learned during the module, including:

* Classes and Objects
* Encapsulation
* Constructors
* Inheritance where applicable
* Enums
* Object relationships
* Composition
* Methods and method responsibilities
* Separation of responsibilities using packages

The system is also divided into managers and entities so that each class has a clear purpose instead of putting the whole system into one large class.

---

## Group Members

| Member    | Student ID    | Main Area               |
| --------- | ------------- | ----------------------- |
| Arvin     | CODSE261F-010 | Core Architecture & GUI |
| Sonal     | CODSE261F-069 | Reservation System      |
| Methuli   | CODSE261F-016 | Payment System          |
| Harshitha | CODSE261F-007 | Vehicle Locator         |

---

## Current Version

This is **Version 1.0** of the project.

The current version focuses on creating a working prototype of the main parking lot operations. Real-world hardware such as parking sensors, scanners, and QR readers are represented through keyboard input and Java Swing interfaces.

There is also a small hidden feature in the application for anyone curious enough to look for it.

---

## Future Improvements

Some possible improvements for future versions would be:

* Connecting the system to a database.
* Using real number plate recognition.
* Adding parking sensors.
* Adding QR code scanning.
* Adding user accounts and staff accounts.
* Adding a proper payment gateway.
* Adding a more detailed parking map.
* Allowing the system to support a larger number of parking floors and spaces.

---

## Conclusion

The Parking Lot Management System was built to give us practical experience in applying OOP concepts to a real-world problem. Instead of making separate programs for each feature, the different parts of the system communicate with each other to create one complete parking management system.
