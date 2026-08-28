package parking.reservation;

import parking.core.Vehicle;
import parking.core.ParkingSpace;
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
        if(startTime == null){
            throw new IllegalArgumentException("Start time cannot be null.");
        }

        //Validate end time
        if(endTime == null){
            throw new IllegalArgumentException("End time cannot be null.");
        }

        //End time must be after start time
        if(!endTime.isAfter(startTime)){
            throw new IllegalArgumentException(
                "End time must be after start time."
            );
        }
    }

    //Set values
    this.reservationId = UUID.randomUUID().toString();
    this.vehicle = vehicle;
    this.parkingSpace = parkingSpace;
    this.startTime = startTime;
    thid.endTime = endTime;

    //Initial state
    this.status = ReservationStatus.ACTIVE;

    //Calculate reservation price
    this.price = calculateReservationPrice();

}

//Getters

public String getReservationId() {
    return reservationId;
}

public Vehicle getVehicle(){
    return vehicle;
}

public ParkingSpace getParkingSpace() {
    return parkingSpace;
}

public LocalDateTime getStartTime(){
    return startTime;
}

public LocalDateTime getEndTime() {
    return endTime;
}

public double getPrice(){
    return price;
}

//Status methods

public boolean isActive() {
    return status == ReservationStatus.ACTIVE;
}

public boolean isExpired(){
    return status == ReservationStatus.EXPIRED;
}

public boolean isCacelled() {
    return status == ReservationStatus.CANCELLED;
}

public boolean isUsed() {
    return status == ReservationStatus.USED;
}

//Cancel reservation

public void cancel() {
    if (status == ReservationStatus.USED){
        throw new IllegalStateException(
            "A used reservation cannot be cancelled."
        );
    }
}

if (status == ReservationStatus.EXPIRED){
    throw new IllegalStateException(
        "An expired reservation cannot be cancelled."
         );
}

status = ReservationStatus.CANCELLED;

}

// Mark reservation as used

public void markAsUsed(){
    if(status != ReservationStatus.ACTIVE){
        throw new IllegalStateException(
            "Only an active reservation can be marked as used."
        );
    }

    status = ReservationStatus.USED;
}

//Check reservation expiration

public void checkExpiration(LocalDateTime currentTime){
    if(currentTime == null){
        throw new IllegalArgumentException(
           "Current time cannot be null." 
        );
    }
    if(status == ReservationStatus.ACTIVE && currentTime.isAfter(endTime)){
        status = ReservationStatus.EXPIRED;
    }
}

//No-show detection

public boolean hasPassedNoShowPeriod(LocalDateTime currentTime){

    if(currentTime == null){
        throw new IllegalArgumentException(
            "Current time cannot be null."
        );
    }

    //No-show period = 60mins
    LocalDateTime noShowTime = startTime.plusMinutes(60);

    return status == ReservationStatus.ACTIVE && currentTime.isAfter(noShowTime);

}

// Apply no-show charge

public void processNoShow(LocalDateTime currentTime){

    if(currentTime == null){
        throw new IllegalArgumentException(
            "Current time cannot be null."
        );
    }
    if(hasPassedNoShowPeriod(currentTime)){
        //Charge for one hour
        price += NO_SHOW_CHARGE;

        //Cancel reservation
        status = ReservstionStatus.CANCELLED;
    }
}

//Reservation pricing

private double calculateReservationPrice(){
    long minutes = Duration.between (startTime, endTime). toMinutes();

    double hours = minutes / 60.0;

    double normalParkingCharge = hours * NORMAL_RATE_PER_HOUR;

    return normalParkingCharge + RESERVATION_SURCHARGE;
}

//Extra parking time

public long calculateExtraMinutes(LocalDateTime actualExitTime){
    if(actualExitTime == null) {
        throw new IllegalArgumentException(
            "Acutal exit time cannot be null."
        );
    }

    if(!actualExitTime.isAfter(endTime)){
        return 0;
    }

    return Duration.between(
        endTime,
        actualExitTime
    ).toMinutes();
}

//Extra parking hours

public double calculateExtraMinutes(LocalDateTime actualExitTime){

    long extraMinutes = calculateExtraMinutes(actualExitTime);

    return extraMinutes / 60.0;
}

//Check vehicle 

public boolean belongsToVehicle(String vehicleNumber){

    if(vehicleNumber == null || vehicleNumber.trim().isEmpty()){
        return false;
    }

    return vehicle.getVehicleNumber()
        .equalsIgnoreCase(vehicleNumber.trim());
}

// Display reservation details

@Override
public String toString(){

    return "Reservation{" +
        "reservationId= " + reservationId + '\'' +
        ", vehicle=" + vehicle +
        ", parkingSpace=" + parkingSpace +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", price=" + price +
        ", status=" + status +
        '}';

}
}

