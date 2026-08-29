package parking.core;
public class Vehicle {

    private String vehicleNumber;
    private String vehicleType;
    private VehicleClass vehicleClass;

    public Vehicle(String vehicleNumber, String vehicleType, VehicleClass vehicleClass) {
        this.vehicleNumber =  vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleClass = vehicleClass;
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
}