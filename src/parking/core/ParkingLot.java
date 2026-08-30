package parking.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
public class ParkingLot {
    // List of all parking spaces in the lot
    private ArrayList <ParkingSpace> parkingSpaces;

    // Constructor to et attributes of all 100 parking spaces
    public ParkingLot() {
        parkingSpaces = new ArrayList <>();

        for(int i = 1; i <= 30; i++) {
            parkingSpaces.add (new ParkingSpace(i, VehicleClass.LIGHT));
        }
        for(int i = 31; i <= 45; i++) {
            parkingSpaces.add (new ParkingSpace(i, VehicleClass.MOTORCYCLE));
        }
        for(int i = 46; i <= 50; i++) {
            parkingSpaces.add (new ParkingSpace(i, VehicleClass.HEAVY));
        }
        for(int i = 51; i <= 80; i++) {
            parkingSpaces.add (new ParkingSpace(i, VehicleClass.LIGHT));
        }
        for(int i = 81; i <= 95; i++) {
            parkingSpaces.add (new ParkingSpace(i, VehicleClass.MOTORCYCLE));
        }
        for(int i = 96; i <= 100; i++) {
            parkingSpaces.add (new ParkingSpace(i, VehicleClass.HEAVY));
        }
    }

    // Find the first unoccupied and unreserved space that matches vehicle class
    public ParkingSpace findAvailableSpace (VehicleClass vehicleClass) {
        for (ParkingSpace space : parkingSpaces) {
            if (!space.isOccupied() && !space.isReserved() && space.getAllowedClass() == vehicleClass) {
                return space;
            }
        }

        return null;
    }
    
    // Park vehicle in parking space
    public ParkingSpace parkVehicle (Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        ParkingSpace space = findAvailableSpace (vehicle.getVehicleClass());

        if (space == null) {
            return null;
        }


        if (space.parkVehicle (vehicle)) {
            return space;
        }

        return null;
    }

    // Remove vehicle from parking space
    public boolean removeVehicle (String vehicleNumber) {
        for (ParkingSpace space : parkingSpaces) {
            if (space.isOccupied() && space.getOccupiedVehicle() != null && space.getOccupiedVehicle().getVehicleNumber().equals(vehicleNumber)) {
                return space.removeVehicle();
            }
        }

        return false;
    }

    // Finds the space current occupied
    public ParkingSpace findVehicle (String vehicleNumber) {
        for (ParkingSpace space : parkingSpaces) {
            if (space.isOccupied() && space.getOccupiedVehicle() != null && space.getOccupiedVehicle().getVehicleNumber().equals(vehicleNumber)) {
                return space;
            } 
        }

        return null;
    }

    // Return total number of spaces
    public int getTotalSpaces() {
        return parkingSpaces.size();
    }

    // return full list of spaces
    public ArrayList<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }
}