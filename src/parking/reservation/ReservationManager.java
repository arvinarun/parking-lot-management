package parking.reservation;

import parking.core.ParkingLot;
import parking.core.ParkingSpace;
import parking.core.Vehicle;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReservationManager {

    //List holding all reservations
    private ArrayList <Reservation> reservations;
    private ParkingLot parkingLot;

    // Connects attributes to a spefic space in parking lot
    public ReservationManager (ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.reservations = new ArrayList<> ();
    }

    // Creates reservation is valid space
    public Reservation createReservation (Vehicle vehicle, LocalDateTime startTime, LocalDateTime endTime) {
        if (vehicle == null) {
            return null;
        }

        ParkingSpace space = parkingLot.findAvailableSpace (vehicle.getVehicleClass());

        if (space == null) {
            return null;
        }

        Reservation reservation = new Reservation(
                vehicle,
                space,
                startTime,
                endTime
        );

        reservations.add (reservation);

        return reservation;
    }

    // Finds a reservation
    public Reservation findReservation (String reservationId) {
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

    // Finds active reservation
    public Reservation findReservationByVehicle (String vehicleNumber) {
        if (vehicleNumber == null) {
            return null;
        }

        for (Reservation reservation : reservations) {
            if (reservation.belongsToVehicle(vehicleNumber) && reservation.isActive()) {
                return reservation;
            }
        }

        return null;
    }

    //Marks reservation used
    public boolean marksReservationUsed (String vehicleNumber) {
        Reservation reservation = findReservationByVehicle (vehicleNumber);

        if (reservation == null) {
            return false;
        }

        return reservation.markAsUsed();
    }

    // Cancels reservation
    public boolean cancelReservation (String reservationId) {

        Reservation reservation = findReservation (reservationId);

        if (reservation == null) {
            return false;
        }

        return reservation.cancel();
    }

    public void checkReservations (LocalDateTime currentTime) {

        for (Reservation reservation : reservations) {

            if (reservation.isActive()) {
                reservation.checkExpiration(currentTime);
                reservation.processNoShow(currentTime);
            }
        }
    }

    // Returns full list of reservations
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
}