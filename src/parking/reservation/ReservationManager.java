package parking.reservation;

import parking.core.ParkingLot;
import parking.core.ParkingSpace;
import parking.core.Vehicle;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReservationManager {

    private ArrayList<Reservation> reservations;
    private ParkingLot parkingLot;

    public ReservationManager(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.reservations = new ArrayList<>();
    }

    public Reservation createReservation(Vehicle vehicle, LocalDateTime startTime, LocalDateTime endTime) {
        if (vehicle == null) {
            return null;
        }

        ParkingSpace space = parkingLot.findAvailableSpace(vehicle.getVehicleClass());

        if (space == null) {
            return null;
        }

        Reservation reservation = new Reservation(
                vehicle,
                space,
                startTime,
                endTime
        );

        reservations.add(reservation);

        return reservation;
    }

    public Reservation findReservation(String reservationId) {
        if (reservationId == null) {
            return null;
        }

        for (Reservation reservation : reservations) {
            if (reservation.getReservationId().equalsIgnoreCase(reservationId.trim())) {
                return reservation;
            }
        }

        return null;
    }

    public Reservation findReservationByVehicle(String vehicleNumber) {
        if (vehicleNumber == null) {
            return null;
        }

        for (Reservation reservation : reservations) {
            if (reservation.belongsToVehicle(vehicleNumber) &&
                reservation.isActive()) {

                return reservation;
            }
        }

        return null;
    }

    public boolean cancelReservation(String reservationId) {

        Reservation reservation = findReservation(reservationId);

        if (reservation == null) {
            return false;
        }

        return reservation.cancel();
    }

    public void checkReservations(LocalDateTime currentTime) {

        for (Reservation reservation : reservations) {

            if (reservation.isActive()) {
                reservation.checkExpiration(currentTime);
                reservation.processNoShow(currentTime);
            }
        }
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
}