package parking.reservation;

import parking.core.Vehicle;
import parking.core.ParkingSpace;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {

    private final String reservationId;
    private final Vehicle vehicle;
    private final ParkingSpace parkingSpace;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private double price;
    private ReservationStatus status;

    private static final double NORMAL_RATE_PER_HOUR = 100.00;
    private static final double RESERVATION_SURCHARGE = 50.00;
    private static final double NO_SHOW_CHARGE = 100.00;

    public Reservation(Vehicle vehicle, ParkingSpace parkingSpace, LocalDateTime startTime, LocalDateTime endTime) {

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }

        if (parkingSpace == null) {
            throw new IllegalArgumentException("Parking space cannot be null.");
        }

        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be null.");
        }

        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be null.");
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                "End time must be after start time."
            );
        }

        this.reservationId = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.parkingSpace = parkingSpace;
        this.startTime = startTime;
        this.endTime = endTime; // Fixed typo: thid -> this

        this.status = ReservationStatus.ACTIVE;
        this.price = calculateReservationPrice();
    }

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

    public boolean isActive() {
        return status == ReservationStatus.ACTIVE;
    }

    public boolean isExpired() {
        return status == ReservationStatus.EXPIRED;
    }

    public boolean isCancelled() { // Fixed typo in method name: isCacelled -> isCancelled
        return status == ReservationStatus.CANCELLED;
    }

    public boolean isUsed() {
        return status == ReservationStatus.USED;
    }

    public void cancel() {
        if (status == ReservationStatus.USED) {
            throw new IllegalStateException(
                "A used reservation cannot be cancelled."
            );
        }

        if (status == ReservationStatus.EXPIRED) {
            throw new IllegalStateException(
                "An expired reservation cannot be cancelled."
            );
        }

        status = ReservationStatus.CANCELLED;
    }

    public void markAsUsed() {
        if (status != ReservationStatus.ACTIVE) {
            throw new IllegalStateException(
                "Only an active reservation can be marked as used."
            );
        }

        status = ReservationStatus.USED;
    }

    public void checkExpiration(LocalDateTime currentTime) {
        if (currentTime == null) {
            throw new IllegalArgumentException(
                "Current time cannot be null."
            );
        }
        if (status == ReservationStatus.ACTIVE && currentTime.isAfter(endTime)) {
            status = ReservationStatus.EXPIRED;
        }
    }

    public boolean hasPassedNoShowPeriod(LocalDateTime currentTime) {
        if (currentTime == null) {
            throw new IllegalArgumentException(
                "Current time cannot be null."
            );
        }

        LocalDateTime noShowTime = startTime.plusMinutes(60);

        return status == ReservationStatus.ACTIVE && currentTime.isAfter(noShowTime);
    }

    public void processNoShow(LocalDateTime currentTime) {
        if (currentTime == null) {
            throw new IllegalArgumentException(
                "Current time cannot be null."
            );
        }
        if (hasPassedNoShowPeriod(currentTime)) {
            price += NO_SHOW_CHARGE;
            status = ReservationStatus.CANCELLED; // Fixed typo: ReservstionStatus -> ReservationStatus
        }
    }

    private double calculateReservationPrice() {
        long minutes = Duration.between(startTime, endTime).toMinutes(); // Fixed spacing issue: . toMinutes() -> .toMinutes()
        double hours = minutes / 60.0;
        double normalParkingCharge = hours * NORMAL_RATE_PER_HOUR;

        return normalParkingCharge + RESERVATION_SURCHARGE;
    }

    public long calculateExtraMinutes(LocalDateTime actualExitTime) {
        if (actualExitTime == null) {
            throw new IllegalArgumentException(
                "Actual exit time cannot be null." // Fixed typo: Acutal -> Actual
            );
        }

        if (!actualExitTime.isAfter(endTime)) {
            return 0;
        }

        return Duration.between(endTime, actualExitTime).toMinutes();
    }

    public double calculateExtraHours(LocalDateTime actualExitTime) {
        long extraMinutes = calculateExtraMinutes(actualExitTime);
        return extraMinutes / 60.0;
    }

    public boolean belongsToVehicle(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            return false;
        }

        return vehicle.getVehicleNumber()
            .equalsIgnoreCase(vehicleNumber.trim());
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "reservationId='" + reservationId + '\'' + // Fixed missing opening quote before reservationId
            ", vehicle=" + vehicle +
            ", parkingSpace=" + parkingSpace +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", price=" + price +
            ", status=" + status +
            '}';
    }
}