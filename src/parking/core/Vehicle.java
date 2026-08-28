package parking.core;
public class Vehicle {

    String vehicleNumber;

    public Vehicle(String vehicleNumber){
        this.vehicleNumber = vehicleNumber;
    }

    public static void main(String[] args) {
        Vehicle v1 = new Vehicle("ABC-123");
        System.out.println(v1.vehicleNumber);
    }
}