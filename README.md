# Parking Lot Management System

A Java-based **Parking Lot Management System** developed as a group coursework project for the **Diploma in Software Engineering (DSE)** program at the **National Institute of Business Management (NIBM)**.

## Project Details

* **Course:** Diploma in Software Engineering (DSE)
* **Module:** Object Oriented Programming
* **Batch:** 26.1
* **Assessment:** Course Work
* **Project:** Parking Lot Management System
* **Development Language:** Java
* **Application Type:** Standalone Java prototype
* **Development Approach:** Object-Oriented Programming

## Project Overview

The Parking Lot Management System is designed to model the management of a limited-capacity parking facility serving an outlet such as a hotel, shopping mall, or other commercial establishment.

The system aims to provide a **clean, straightforward, and low-distraction parking experience**, where the majority of parking operations are handled automatically by the system.

The application will simulate the processes involved in vehicle entry, parking allocation, reservations, payment, vehicle location, and exit management.

This project is a **prototype/model system for educational purposes**. It is not intended to be a real-world parking infrastructure implementation.

In a real-world deployment, technologies such as number-plate recognition cameras, parking sensors, QR scanners, payment gateways, databases, and other hardware or external services could be integrated. For this coursework, these real-world technologies will be **simulated through keyboard-based input and Java application logic for the length of this project**.

---

## Version 1 Scope

Version 1 will focus on the core functionality required to model a parking management system.

### Vehicle Entry

The system will:

* Record the vehicle registration/number plate.
* Record the vehicle type.
* Determine the vehicle class.
* Check for an existing reservation.
* Assign an available parking space suitable for the vehicle class.
* Prefer a parking space closest to the relevant outlet entrance.
* Display a welcome message.
* Display the assigned parking location.
* Display the applicable parking rate.

### Vehicle Classification

Vehicles will primarily be managed according to their **vehicle class**, with specific vehicle types used for identification.

The initial vehicle classes are:

* Light Vehicles
* Motorcycles
* Heavy Vehicles

Examples of specific vehicle types may include cars, SUVs, jeeps, pickup trucks, motorcycles, and heavy vehicles.

The parking facility will have a **fixed and limited number of parking spaces**, with the capacity intended to approximately represent:

* 60% Light Vehicles
* 30% Motorcycles
* 10% Heavy Vehicles

The exact number and arrangement of spaces will be determined during the system design stage.

### Parking Reservations

Users will be able to reserve a parking space in advance.

When a reserved vehicle enters the parking lot, the system will:

* Identify the reservation using the vehicle number.
* Display the reserved parking location.
* Display the reservation duration.
* Display the reservation price.
* Apply an additional charge for reservations compared with normal parking.
* Calculate additional charges when the reserved duration is exceeded.

If a reserved vehicle does not arrive:

* The reservation will remain active for the first 60 minutes.
* The applicable charge for one hour will be applied.
* The reservation will then be cancelled after the 60-minute no-show period.

### Vehicle Location

A customer who forgets where their vehicle is parked will be able to access a simulated parking portal through a QR-code concept.

The real-world QR code is **not being implemented as physical infrastructure in Version 1**. The functionality will instead be represented within the Java prototype.

The customer will provide:

* Vehicle number
* Vehicle type

If the information matches an active parking record, the system will display the vehicle's parking location.

Invalid or incorrect information will be rejected and the user will be asked to try again.

### Payment

The system will model two payment methods:

**Online Payment**

A QR-code-based portal will represent access to an online payment system where the customer can make a card payment.

**Physical Payment**

A physical checkout area will be represented within the system. Once payment is made, the parking system will be updated to reflect the completed payment.

### Vehicle Exit

When a vehicle reaches the exit, the system will check its payment status.

* If payment has been completed, the vehicle will be allowed to exit.
* If payment has not been completed, the system will provide access to the simulated online payment process.
* The vehicle will only be allowed to exit after the required payment has been completed.

### Input Validation

The system will validate user inputs throughout the application.

Invalid inputs will not be accepted. The user will be asked to provide valid information before the system continues with the relevant operation.

---

## Project Purpose

This project is being developed to practically apply the principles of **Object-Oriented Programming in Java** to a real-world problem.

The project will provide practical experience with concepts including:

* Classes and objects
* Encapsulation
* Abstraction
* Inheritance
* Polymorphism
* Interfaces
* Abstract classes
* Packages
* Association
* Aggregation
* Composition
* Collections
* Validation and error handling
* File handling where appropriate
* Java GUI development
* Event-driven programming

OOP concepts will only be used where they are appropriate to the system. The project will not force an OOP feature into the design simply for the purpose of demonstrating it.

The system will also provide practical experience in designing a maintainable Java application, separating responsibilities between components, handling user input, testing functionality, and developing a graphical user interface.

---

## System Limitations

Version 1 is an **educational prototype** and does not represent a production-ready parking management solution.

The system will not directly implement real-world hardware or external infrastructure such as:

* Automatic number-plate recognition cameras
* Physical parking sensors
* Real QR-code scanning infrastructure
* Real banking/payment gateways
* Government vehicle-registration databases
* External cloud services
* Real-time physical parking gates

These technologies may be represented or simulated through Java application logic and user input.

The prototype will focus primarily on modelling the **software behaviour and object-oriented design** of the parking management system.

---

## Team Members

| Name      | Student ID    |
| --------- | ------------- |
| Arvin     | CODSE261F-010 |
| Sonal     | CODSE261F-069 |
| Methuli   | CODSE261F-016 |
| Harshitha | CODSE261F-007 |

---

## Development

The project is developed collaboratively using Git and GitHub.

The `main` branch is used as the stable/shared branch. Team members work on individual feature branches and submit Pull Requests before changes are merged into `main`.

### General Workflow

```text
main
 │
 ├── feature/arvin
 ├── feature/sonal
 ├── feature/methuli
 └── feature/harshitha
        │
        ↓
   Pull Request
        │
        ↓
       main
```

This workflow allows changes to be reviewed before becoming part of the shared project.

---

## Project Status

**Current Stage:** Requirements and system analysis

The initial GitHub repository and collaborative development workflow have been established. The next stages will involve analysing the system requirements, identifying real-world entities, defining responsibilities and relationships, designing the object-oriented structure, and then implementing the application incrementally in Java.
