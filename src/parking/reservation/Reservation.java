package parking.reservation;

import parking.core.Vehicle;
import parking.core.ParkingSpace;

import java.time.Duration;
import java.time.LocalDateTime;
public class Reservation {
    // generate reservation IDs
    private static int nextReservationId = 1;

    private String reservationId;
    private Vehicle vehicle;
    private ParkingSpace parkingSpace;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private ReservationStatus status;

    // Rate per hour
    private static final double NORMAL_RATE_PER_HOUR = 100.00;
    // Extra reservation charge
    private static final double RESERVATION_SURCHARGE = 50.00;
    // No show charge
    private static final double NO_SHOW_CHARGE = 100.00;

    // Constructor for reservation attributes
    public Reservation (Vehicle vehicle, ParkingSpace parkingSpace, LocalDateTime startTime, LocalDateTime endTime) {
        this.reservationId = "R" + nextReservationId++;
        this.vehicle = vehicle;
        this.parkingSpace = parkingSpace;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = ReservationStatus.ACTIVE;
        this.price = calculateReservationPrice();

        parkingSpace.addReservation(vehicle);
    }

    // Getters
    public String getReservationId() {
        return reservationId;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public double getPrice() {
        return price;
    }
    public ReservationStatus getStatus() {
        return status;
    }
    public boolean isActive() {
        return status == ReservationStatus.ACTIVE;
    }
    public boolean isUsed() {
        return status == ReservationStatus.USED;
    }
    public boolean isCancelled() {
        return status == ReservationStatus.CANCELLED;
    }
    public boolean isExpired() {
        return status == ReservationStatus.EXPIRED;
    }

    // Cancel reservation
    public boolean cancel() {
        if (status != ReservationStatus.ACTIVE) {
            return false;
        }

        parkingSpace.cancelReservation();
        status = ReservationStatus.CANCELLED;

        return true;
    }

    // Marks reservation as used
    public boolean markAsUsed() {
        if (status != ReservationStatus.ACTIVE) {
            return false;
        }

        status = ReservationStatus.USED;

        return true;
    }

    // Removes reservation for no show
    public void checkExpiration (LocalDateTime currentTime) {
        if (status == ReservationStatus.ACTIVE && currentTime.isAfter(endTime)) {

            parkingSpace.cancelReservation();
            status = ReservationStatus.EXPIRED;
        }
    }

    // 60 min no show
    public boolean hasPassedNoShowPeriod (LocalDateTime currentTime) {
        LocalDateTime noShowTime = startTime.plusMinutes(60);

        return status == ReservationStatus.ACTIVE && currentTime.isAfter(noShowTime);
    }

    // Applies no show charge
    public void processNoShow (LocalDateTime currentTime) {
        if (hasPassedNoShowPeriod(currentTime)) {

            price += NO_SHOW_CHARGE;

            parkingSpace.cancelReservation();
            status = ReservationStatus.CANCELLED;
        }
    }

    // Base reservation charge
    private double calculateReservationPrice() {
        long minutes = Duration.between(startTime,endTime).toMinutes();
        double hours = minutes / 60.0;
        double parkingCharge = hours * NORMAL_RATE_PER_HOUR;

        return parkingCharge + RESERVATION_SURCHARGE;
    }

    // Calculated extra time
    public long calculateExtraMinutes (LocalDateTime actualExitTime) {
        if (!actualExitTime.isAfter(endTime)) {
            return 0;
        }

        return Duration.between(endTime, actualExitTime).toMinutes();
    }

    public double calculateExtraHours (LocalDateTime actualExitTime) {
        long extraMinutes = calculateExtraMinutes(actualExitTime);

        return extraMinutes / 60.0;
    }

    // Apply charge for extra time
    public void applyExtraCharge (LocalDateTime actualExitTime) {
        double extraHours = calculateExtraHours(actualExitTime);

        if (extraHours > 0) {
            price += extraHours * NORMAL_RATE_PER_HOUR;
        }
    }

    public boolean belongsToVehicle (String vehicleNumber) {
        if (vehicleNumber == null) {
            return false;
        }

        return vehicle.getVehicleNumber().equalsIgnoreCase(vehicleNumber.trim());
    }

    @Override
    public String toString() {
        return "Reservation{" + "reservationId='" + reservationId + '\'' + ", vehicle=" + vehicle.getVehicleNumber() +
                ", parkingSpace=" + parkingSpace.getSpaceNumber() + ", startTime=" + startTime + ", endTime=" + endTime +
                ", price=" + price + ", status=" + status + '}';
    }
}