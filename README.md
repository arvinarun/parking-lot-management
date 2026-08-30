# Smart Parking Management System

Hey there! This is our object-oriented programming (OOP) coursework project for a Smart Parking Management System. It's a Java Swing desktop application designed to handle day-to-day parking operations like vehicle entry, live spot tracking, advance reservations, payment processing, and owner lookups.

---

## 📌 Project Overview

The main idea behind this project is to automate how a multi-floor parking facility operates. It manages 100 dedicated parking spaces split across 2 floors:
* **Floor 1:** Spaces 1 to 50
* **Floor 2:** Spaces 51 to 100

The system handles three main vehicle classes (**LIGHT**, **MOTORCYCLE**, and **HEAVY**) and keeps track of pricing, space availability, and reservation timeouts automatically.

---

## 🛠️ Features

* **Vehicle Entry & Exit:** Quick check-in form for drivers and automatic space assignment based on vehicle size. Calculates total stay duration and final fees on exit.
* **Pre-Booking / Reservations:** Drivers can reserve a spot in advance. Includes built-in logic to handle 60-minute no-shows (applies a surcharge and frees the spot up).
* **Payment Engine:** Computes walk-in and reservation rates (base rates + hourly rates rounded up to the nearest LKR 10). Supports both Cash (Physical) and Online payments.
* **Vehicle Locator:** Helps drivers find where they parked by searching their plate number, driver name, or vehicle type.
* **Admin Dashboard:** Real-time system monitoring with interactive tables showing live parking spaces, active/past reservations, payment ledgers, and vehicle history log. Auto-refreshes every 30 seconds.

---

## 📂 Project Structure

The project is structured into clear packages separating the business logic from the UI:

```text
src/
└── parking/
    ├── core/          # Main domain models (ParkingLot, ParkingSpace, Vehicle, VehicleClass)
    ├── reservation/   # Booking logic (Reservation, ReservationManager, ReservationStatus)
    ├── payment/       # Billing logic (Payment, PaymentManager, PaymentMethod, PaymentStatus)
    ├── location/      # Vehicle lookup helper (VehicleLocator)
    ├── gui/           # Custom Java Swing UI components (MainWindow, ManagementWindow, Panels)
    └── Main.java      # Main entry point of the application
