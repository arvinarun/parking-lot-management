package parking.location;
public class FindMyVehicle
{
    //Reference for the main parking lot
    private ParkingLot parkingLot;

    //Constructor 
    public FindMyVehicle(ParkingLot parkingLot)
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
    //vehicle type validate
    if(vehicleType == null || vehicleType.trim().isEmpty())
    {
        return "Invalid information.\n"
                +"please enter a vehicle type.";
    }
    vehicleNumber =vehicleNumber.trim();
    vehicleType = vehicleType.trim();
   }}