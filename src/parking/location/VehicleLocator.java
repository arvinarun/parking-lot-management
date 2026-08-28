package parking.location;
import parking.core.Vehicle;
import parking.core.ParikngLot;
import parking.core.VehicleClass;

public class VehicleLocator
{
    //Reference for the main parking lot
    private ParkingLot parkingLot;

    //Constructor 
    public VehicleLocator(ParkingLot parkingLot)
    {
        this.parkingLot = parkingLot;
    }
    /*Main method to Find my vehicle feature
    *Inputs : vehicle number
            : vehicle type
    *Output : Vehicle location
            : Error message
    */
   public String findVehicle(String vehicleNumber, String vehicleType)
   {
    //vehicle number validate
    if(vehicleNumber == null || vehicleNumber.trim().isEmpty())
    {
        return "Invalid information.\n"
                +"please enter a vehicle number.";
    }
    vehicleNumber = vehicleNumber.trim();
    //vehicle type validate
    if(vehicleType == null || vehicleType.trim().isEmpty())
    {
        return "Invalid information.\n"
                +"please enter a vehicle type.";
    }
    vehicleType = vehicleType.trim();
   }}