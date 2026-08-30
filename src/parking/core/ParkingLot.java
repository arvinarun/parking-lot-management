package parking.core;

import parking.payment.PaymentManager;
import java.util.ArrayList;
public class ParkingLot {
    // List of all parking spaces in the lot
    private ArrayList <ParkingSpace> parkingSpaces;
    private ArrayList <Vehicle> vehicleHistory;

    // Constructor to et attributes of all 100 parking spaces
    public ParkingLot() {
        parkingSpaces = new ArrayList <>();
        vehicleHistory = new ArrayList <>();

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
    
    //Finds reserved space
    public ParkingSpace findReservedSpace (String vehicleNumber) {
        if(vehicleNumber == null) {
            return null;
        }
        for (ParkingSpace space : parkingSpaces) {
            if(space.isReserved() && space.getReservedVehicle() != null && space.getReservedVehicle().getVehicleNumber().equalsIgnoreCase(vehicleNumber.trim())) {
                return space;
            }
        }

        return null;
    }
    // Park vehicle in valid parking space
    public ParkingSpace parkVehicle (Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        ParkingSpace space = findReservedSpace(vehicle.getVehicleNumber());
        if (space == null) {
            space = findAvailableSpace (vehicle.getVehicleClass());
        }
        
        if (space == null) {
            return null;
        }


        if (space.parkVehicle (vehicle)) {
            if (!vehicleHistory.contains(vehicle)) {
                vehicleHistory.add(vehicle);
            }

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

    // Allowing vehicle to exit
    public String exitVehicle(String vehicleNumber, PaymentManager paymentManager) {
        ParkingSpace space = findVehicle(vehicleNumber);

        if (space == null) {
            return "Vehicle not found in the parking lot.";
        }

        // Check payment status
        if (!paymentManager.isPaid(vehicleNumber)) {
            return "Payment not completed !\nPlease complete payment before exiting.";
        }

        // Record Exit
        Vehicle vehicle = space.getOccupiedVehicle();

        if (removeVehicle(vehicleNumber)) {
            vehicle.recordExit();
            return "Payment confirmed. Vehicle may exit.";
        }

        return "Unable to remove vehicle. Please try again.";
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

    // Return full list of spaces
    public ArrayList<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public ArrayList<Vehicle> getVehicleHistory() {
        return vehicleHistory;
    }
}