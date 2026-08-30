package parking.core;

import java.time.LocalDateTime;
public class Vehicle {
    // User name
    private String name;
    // Vehicle plate number
    private String vehicleNumber;
    // Type of vehicle
    private String vehicleType;
    // Group Classification based off vehicle type
    private VehicleClass vehicleClass;
    // Time of vehicle entry
    private LocalDateTime entryTime;

    // Constructor to set vehicle attributes, and entry time recorded
    public Vehicle (String name, String vehicleNumber, String vehicleType, VehicleClass vehicleClass) {
        this.name = name;
        this.vehicleNumber =  vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleClass = vehicleClass;
        this.entryTime = LocalDateTime.now();
    }

    //Getters
    public String getName() {
        return name;
    }
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    public String getVehicleType() {
        return vehicleType;
    }
    public VehicleClass getVehicleClass() {
        return vehicleClass;
    }
    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}