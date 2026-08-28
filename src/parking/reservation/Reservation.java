package parking.reservation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation{
    // Attributes

    private final String reservationId;
    private final Vehicle vehicle;
    private final ParkingSpace parkingSpace;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private double price;
    private ReservationStatus status;

    //Project pricing configuration
    //Replace these values with the rates
    //agreed by your team
    private static final double NORMAL_RATE_PER_HOUR = 100.00;
    private static final double RESERVATION_SURCHARGE = 50.00;
    private static final double NO_SHOW_CHARGE = 100.00;

    // Constructor

    public Reservation(
        Vehicle vehicle,
        ParkingSpace parkingSpace,
        LocalDateTime startTime,
        LocalDateTime endTime
    ) {
        //Validate vehicle
        if(vehicle == null){
            throw new IllegalArgumentException("Vehicle cannot be null. ");
        }

        //Validate parking space
        if (parkingSpace == null){
            throw new IllegalArgumentException("Parking space cannot be null.");
        }

        //Validate start time
        if(StartTime == null){
            throw new IllegalArgumentException("Start time cannot be null.");
        }

        //Validate end time
        if(endTime == null){
            throw new IllegalArgumentException("End time cannot be null.")
        }

        //End time must be after start time
        if(!endTime.isAfter(startTime)){
            throw new IllegalArgumentException(
                "End time must be after start time."
            );
        }
    }
}