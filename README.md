# Parking Lot Management System

## Project Overview

The **Parking Lot Management System** is a Java-based standalone prototype developed for the **Object Oriented Programming (OOP)** module at the **National Institute of Business Management (NIBM)**.

The system models a real-world parking facility and aims to provide a simple, clean, and user-friendly parking experience. The main goal is to automate common parking operations so that the user has to interact with the system as little as possible.

This project is a **prototype/model system** developed for academic purposes. It does not represent a complete real-world parking implementation. In a real environment, technologies such as vehicle number-plate recognition, parking sensors, payment systems, QR codes, web portals, and databases could be integrated.

---

## Why This Project Is Being Developed

Traditional parking facilities can require users to manually search for parking spaces, remember where they parked, make reservations through separate systems, and complete payments at physical counters.

This project explores how **Object-Oriented Programming principles in Java** can be used to model and automate these processes within a single parking management system.

The project also provides practical experience in applying:

* Classes and Objects
* Encapsulation
* Inheritance
* Polymorphism
* Object relationships
* Collections and data structures
* Event handling and GUI development
* Error handling
* CRUD operations

---

# Version 1

Version 1 focuses on building the **core parking management model** before implementing the complete user interface and additional services.

### Current Features

#### Vehicle Management

The system represents vehicles using a `Vehicle` object containing:

* Vehicle number
* Vehicle type
* Vehicle class

Vehicle classes currently include:

* `LIGHT`
* `MOTORCYCLE`
* `HEAVY`

Vehicle type is used to identify specific vehicle types such as cars, SUVs, Jeeps, pickup trucks, motorcycles, and heavy vehicles, while the vehicle class is used for parking-space allocation.

#### Parking Space Management

Each parking space is represented as a `ParkingSpace` object.

A parking space currently contains:

* Space number
* Allowed vehicle class
* Occupied status
* Reserved status
* Currently occupied vehicle

The parking space can:

* Accept a suitable vehicle
* Reject an unsuitable vehicle
* Prevent parking in occupied spaces
* Prevent parking in reserved spaces
* Remove an occupying vehicle
* Add a reservation status
* Cancel a reservation

#### Parking Lot Management

The parking lot is currently modelled as:

* **2 floors**
* **100 fixed parking spaces**
* **60 Light Vehicle spaces**
* **30 Motorcycle spaces**
* **10 Heavy Vehicle spaces**

The `ParkingLot` manages the collection of parking spaces and can:

* Find an available space for a vehicle class
* Park a vehicle in a suitable space
* Remove a vehicle
* Find where a vehicle is currently parked
* Track the total number of parking spaces

---

# Planned System Features

The following features are part of the planned parking management system and will be implemented as the project progresses.

### Automatic Vehicle Entry

When a vehicle enters:

1. The vehicle number is recorded.
2. The vehicle type is provided.
3. The vehicle class is determined.
4. The system checks for an existing reservation.
5. A suitable parking space is identified.
6. The user receives the relevant parking information.

### Parking Recommendation

The system will recommend an available parking space based on the vehicle's class.

The objective is to recommend a space that is **as close as possible to an outlet entrance**, reducing the distance the customer needs to walk.

### Reservation System

Users will be able to reserve parking spaces before arriving.

The system will:

* Check reservations when a vehicle enters.
* Identify the reserved parking space.
* Display the reservation duration and price.
* Apply additional reservation charges.
* Calculate additional charges when the reserved duration is exceeded.
* Charge a one-hour no-show fee if the user does not arrive.
* Cancel the reservation after the 60-minute no-show period.

### Lost Vehicle Assistance

Users who forget where their vehicle is parked will be able to provide:

* Vehicle number
* Vehicle type

The system will use this information to locate the vehicle.

If invalid information is entered, the system will reject the input and ask the user to try again.

### Payment System

The planned system will support:

* Payment through an online portal accessed using a QR code.
* Payment through a physical checkout area.
* Updating the parking system when a physical payment has been completed.

### Vehicle Exit

When a vehicle reaches the exit:

1. The system checks whether payment has been completed.
2. If payment has been completed, the vehicle can exit.
3. If payment has not been completed, the system provides access to the online payment portal.
4. The vehicle is allowed to exit after successful payment.

---

# User Experience

The intended user experience is:

> **Clean, simple, direct, and mostly automatic.**

The system should avoid unnecessary interaction and provide only the information the user needs at each stage.

The intended flow is:

```text
Vehicle Enters
      ↓
Vehicle Identified
      ↓
Reservation Checked
      ↓
Suitable Space Found
      ↓
Parking Information Provided
      ↓
Vehicle Parks
      ↓
Payment
      ↓
Vehicle Exits
```

Additional services such as vehicle location and reservations should be accessible without requiring the user to install a separate application.

The planned real-world concept is a **web-based portal accessed through QR codes**, rather than a downloadable mobile application.

---

# Prototype Limitations

This project is a **Java prototype for academic purposes**.

The prototype will use **keyboard input** rather than real-world hardware and external services.

The real-world system could use:

* Automatic Number Plate Recognition (ANPR)
* Parking sensors
* Vehicle classification systems
* QR scanners
* Web portals
* Payment gateways
* Databases
* Automated barriers

These technologies are outside the current prototype scope.

The prototype will initially focus on managing the **current vehicles and parking state** rather than maintaining a complete permanent historical database.

---

# Project Structure

The project is separated into packages so that different parts of the system can be developed independently.

```text
src
└── parking
    ├── core
    │   ├── Vehicle.java
    │   ├── VehicleClass.java
    │   ├── ParkingSpace.java
    │   └── ParkingLot.java
    │
    ├── reservation
    │   └── Reservation.java
    │
    └── ...
```

The project will continue to be expanded as additional system features are implemented.

---

# Current OOP Design

The current core classes have the following responsibilities:

```text
Vehicle
   │
   │ referenced by
   ↓
ParkingSpace
   │
   │ managed by
   ↓
ParkingLot
```

### Vehicle

Represents an individual vehicle and stores its identifying information.

### ParkingSpace

Represents a physical parking space and controls its own parking and reservation state.

### ParkingLot

Manages the collection of parking spaces and coordinates vehicle parking and locating operations.

This separation allows each class to have a clear responsibility and demonstrates the application of object-oriented design principles.

---

# Team Members

| Member        | Student ID    |
| ------------- | ------------- |
| **Arvin**     | CODSE261F-010 |
| **Sonal**     | CODSE261F-069 |
| **Methuli**   | CODSE261F-016 |
| **Harshitha** | CODSE261F-007 |

---

# Development Workflow

The project is developed collaboratively using Git and GitHub.

The `main` branch is treated as the stable/shared branch.

Team members work on individual feature branches:

```text
main
├── feature/arvin
├── feature/sonal
├── feature/methuli
└── feature/harshitha
```

Changes are submitted through **Pull Requests** and reviewed before being merged into `main`.

This workflow helps the team work independently while reducing conflicts and keeping the main branch stable.

---

# Technologies

* **Java**
* **Git**
* **GitHub**

The project is intentionally being developed using **Java only** for the application prototype.

---

# Project Status

**Current Stage: Core OOP Model**

Implemented:

* Vehicle entity
* Vehicle classification enum
* Parking space entity
* Parking space state management
* Vehicle parking/removal
* Parking space reservation state
* 100-space parking lot model
* Vehicle-class-based space allocation
* Vehicle location lookup
* Collaborative GitHub workflow

Next stages will build the remaining parking management features around this core model.
