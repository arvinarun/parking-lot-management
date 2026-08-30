package parking.payment;

import parking.core.ParkingLot;
import parking.core.ParkingSpace;
import parking.core.Vehicle;
import parking.reservation.Reservation;
import parking.reservation.ReservationManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

// Manages creating, finding, and marking payments
public class PaymentManager {

    private static final double NORMAL_RATE_PER_HOUR = 100.00;

    // List holding all payments created
    private ArrayList<Payment> payments;
    private ParkingLot parkingLot;
    private ReservationManager reservationManager;

    // Constructor links this to the parking lot and reservation manager
    public PaymentManager(ParkingLot parkingLot, ReservationManager reservationManager) {
        this.parkingLot = parkingLot;
        this.reservationManager = reservationManager;
        this.payments = new ArrayList<>();
    }

    // Creates a payment
    public Payment createPayment(String vehicleNumber, PaymentMethod paymentMethod) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            return null;
        }

        vehicleNumber = vehicleNumber.trim();

        // Check for reservation
        Reservation reservation = reservationManager.findReservationByVehicle(vehicleNumber);

        double amount;
        Vehicle vehicle;

        if (reservation != null) {
            // Apply any extra charge
            reservation.applyExtraCharge(LocalDateTime.now());
            amount = reservation.getPrice();
            vehicle = reservation.getVehicle();
        } else {
            ParkingSpace space = parkingLot.findVehicle(vehicleNumber);

            if (space == null) {
                return null;
            }

            vehicle = space.getOccupiedVehicle();
            amount = calculateWalkInAmount(vehicle);
        }

        Payment payment = new Payment(vehicle, amount, paymentMethod);
        payments.add(payment);

        return payment;
    }

    private double calculateWalkInAmount(Vehicle vehicle) {
        long minutes = Duration.between(vehicle.getEntryTime(), LocalDateTime.now()).toMinutes();
        double ratePerMinute = NORMAL_RATE_PER_HOUR / 60.0;

        return minutes * ratePerMinute;
    }

    public Payment findPaymentByVehicle(String vehicleNumber) {
        if (vehicleNumber == null) {
            return null;
        }

        vehicleNumber = vehicleNumber.trim();

        Payment found = null;

        for (Payment payment : payments) {
            if (payment.getVehicle().getVehicleNumber().equalsIgnoreCase(vehicleNumber)) {
                found = payment;
            }
        }

        return found;
    }

    // Marks payment complete
    public boolean markAsPaid(String vehicleNumber) {
        Payment payment = findPaymentByVehicle(vehicleNumber);

        if (payment == null) {
            return false;
        }

        payment.markAsPaid();
        return true;
    }

    public boolean isPaid(String vehicleNumber) {
        Payment payment = findPaymentByVehicle(vehicleNumber);

        if (payment == null) {
            return false;
        }

        return payment.isPaid();
    }

    // Returns the list of payments
    public ArrayList<Payment> getPayments() {
        return payments;
    }
}