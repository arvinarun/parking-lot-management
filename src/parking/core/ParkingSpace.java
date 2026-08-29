package parking.core;

public class ParkingSpace {
    private int spaceNumber;
    private VehicleClass allowedClass;
    private boolean isOccupied;
    private boolean isReserved;
    private Vehicle occupiedVehicle;
    private Vehicle reservedVehicle;

    public ParkingSpace(int spaceNumber, VehicleClass allowedClass) {
        this.spaceNumber = spaceNumber;
        this.allowedClass = allowedClass;
    }

    public int getSpaceNumber() {
        return spaceNumber;
    }
    public VehicleClass getAllowedClass() {
        return allowedClass;
    }
    public boolean isOccupied() {
        return isOccupied;
    }
    public boolean isReserved() {
        return isReserved;
    }
    public Vehicle getOccupiedVehicle() {
        return occupiedVehicle;
    }
    public Vehicle getReservedVehicle() {
        return reservedVehicle;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if(vehicle == null) {
            return false;
        }
        if(isOccupied) {
            return false;
        }
        if(isReserved && (reservedVehicle == null || !reservedVehicle.getVehicleNumber().equalsIgnoreCase(vehicle.getVehicleNumber()))) {
            return false;
        }
        if(vehicle.getVehicleClass() != allowedClass) {
            return false;
        }

        occupiedVehicle = vehicle;
        isOccupied = true;
        isReserved = false;
        reservedVehicle = null;

        return true;
    }

    public boolean removeVehicle() {
        if (!isOccupied) {
            return false;
        }

        occupiedVehicle = null;
        isOccupied = false;

        return true;
    }

    public boolean addReservation(Vehicle vehicle) {
        if(vehicle == null) {
            return false;
        }
        if(isOccupied || isReserved) {
            return false;
        }
        if(vehicle.getVehicleClass() != allowedClass) {
            return false;
        }

        isReserved = true;
        reservedVehicle = vehicle;

        return true;
    }

    public boolean cancelReservation() {
        if(!isReserved) {
            return false;
        }

        isReserved = false;
        reservedVehicle = null;
        
        return true;
    }
}