package parking.core;

public class ParkingSpace {

    private int spaceNumber;
    private VehicleClass allowedClass;
    private boolean isOccupied;
    private boolean isReserved;
    private Vehicle occupiedVehicle;

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

    public boolean parkVehicle(Vehicle vehicle) {
        
        if(vehicle == null) {
            return false;
        }
        if(isOccupied) {
            return false;
        }
        if(isReserved) {
            return false;
        }
        if(vehicle.getVehicleClass() != allowedClass) {
            return false;
        }
        occupiedVehicle = vehicle;
        isOccupied = true;
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

    public boolean addReservation() {
        if(isOccupied || isReserved) {
            return false;
        }
        isReserved = true;
        return true;
    }

    public boolean cancelReservation() {
        if(!isReserved) {
            return false;
        }
        isReserved = false;
        return true;
    }
}