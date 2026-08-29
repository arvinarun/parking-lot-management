package parking.core;
import java.util.ArrayList;
public class ParkingLot {
    private ArrayList <ParkingSpace> parkingSpaces;

    public ParkingLot() {
        parkingSpaces = new ArrayList <>();

        for(int i = 1; i <= 30; i++) {
            parkingSpaces.add(new ParkingSpace(i, VehicleClass.LIGHT));
        }
        for(int i = 31; i <= 45; i++) {
            parkingSpaces.add(new ParkingSpace(i, VehicleClass.MOTORCYCLE));
        }
        for(int i = 46; i <= 50; i++) {
            parkingSpaces.add(new ParkingSpace(i, VehicleClass.HEAVY));
        }
        for(int i = 51; i <= 80; i++) {
            parkingSpaces.add(new ParkingSpace(i, VehicleClass.LIGHT));
        }
        for(int i = 81; i <= 95; i++) {
            parkingSpaces.add(new ParkingSpace(i, VehicleClass.MOTORCYCLE));
        }
        for(int i = 96; i <= 100; i++) {
            parkingSpaces.add(new ParkingSpace(i, VehicleClass.HEAVY));
        }
    }

    public ParkingSpace findAvailableSpace(VehicleClass vehicleClass) {

        for(ParkingSpace space : parkingSpaces) {

            if(!space.isOccupied() && !space.isReserved() && space.getAllowedClass() == vehicleClass) {
                return space;
            }

        }
        return null;
    }

    public ParkingSpace parkVehicle(Vehicle vehicle) {
        
        if(vehicle == null) {
            return null;
        }
        ParkingSpace space = findAvailableSpace(vehicle.getVehicleClass());

        if(space == null) {
            return null;
        }


        if (space.parkVehicle(vehicle)) {
            return space;
        }


        return null;
    }

    public boolean removeVehicle(String vehicleNumber) {
        
        for(ParkingSpace space : parkingSpaces) {

            if(space.isOccupied() && space.getOccupiedVehicle() != null && space.getOccupiedVehicle().getVehicleNumber().equals(vehicleNumber)) {
                return space.removeVehicle();
            }
        }

        return false;
    }

    public ParkingSpace findVehicle(String vehicleNumber) {

        for(ParkingSpace space : parkingSpaces) {

            if(space.isOccupied() && space.getOccupiedVehicle() != null && space.getOccupiedVehicle().getVehicleNumber().equals(vehicleNumber)) {
                return space;
            } 
        }

        return null;
    }

    public int getTotalSpaces() {
        return parkingSpaces.size();
    }
}