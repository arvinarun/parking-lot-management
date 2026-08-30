package parking.gui;

import parking.core.ParkingLot;
import parking.core.ParkingSpace;
import parking.core.Vehicle;
import parking.core.VehicleClass;
import parking.reservation.Reservation;
import parking.reservation.ReservationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryPanel extends JPanel {

    // Shared objects needed for entry logic
    private ParkingLot parkingLot;
    private ReservationManager reservationManager;

    // Input fields
    private JTextField nameField;
    private JTextField vehicleNumberField;
    private JTextField vehicleTypeField;

    // Label to show the result message
    private JLabel resultLabel;

    // Constructor builds the entry tab layout
    public EntryPanel(ParkingLot parkingLot, ReservationManager reservationManager) {
        this.parkingLot = parkingLot;
        this.reservationManager = reservationManager;

        // Use a simple vertical layout, one row per item
        setLayout(new GridLayout(5, 2, 5, 5));

        // Name row
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        // Vehicle number row
        add(new JLabel("Vehicle Number:"));
        vehicleNumberField = new JTextField();
        add(vehicleNumberField);

        // Vehicle type row
        add(new JLabel("Vehicle Type:"));
        vehicleTypeField = new JTextField();
        add(vehicleTypeField);

        // Submit button
        JButton submitButton = new JButton("Enter Parking Lot");
        add(submitButton);

        // Result message label
        resultLabel = new JLabel(" ");
        add(resultLabel);

        // When the button is clicked, run the entry logic
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleEntry();
            }
        });
    }

    // Runs when the user submits the entry form
    private void handleEntry() {

        // Get the text from the input fields
        String name = nameField.getText().trim();
        String vehicleNumber = vehicleNumberField.getText().trim();
        String vehicleType = vehicleTypeField.getText().trim();

        // Check inputs are not empty
        if (name.isEmpty() || vehicleNumber.isEmpty() || vehicleType.isEmpty()) {
            resultLabel.setText("Please fill in all fields.");
            return;
        }

        // Check if this vehicle number already has a reservation
        Reservation reservation = reservationManager.findReservationByVehicle(vehicleNumber);

        ParkingSpace space;

        if (reservation != null) {
            // Reuse the vehicle already stored on the reservation, do not make a new one
            Vehicle reservedVehicle = reservation.getVehicle();
            space = parkingLot.parkVehicle(reservedVehicle);

            if (space != null) {
                // Mark the reservation as used now that the vehicle has entered
                reservationManager.markReservationUsed(vehicleNumber);
            }
        } else {
            // No reservation, classify the vehicle type entered
            VehicleClass vehicleClass = classifyVehicle(vehicleType);

            if (vehicleClass == null) {
                resultLabel.setText("Invalid vehicle type. Please try again.");
                return;
            }

            // Create a new vehicle and try to park it as a walk-in
            Vehicle vehicle = new Vehicle(name, vehicleNumber, vehicleType, vehicleClass);
            space = parkingLot.parkVehicle(vehicle);
        }

        // Show the result to the user
        if (space == null) {
            resultLabel.setText("No available space for your vehicle type.");
        } else {
            int floor = space.getSpaceNumber() <= 50 ? 1 : 2;
            resultLabel.setText("Park at Space " + space.getSpaceNumber() + ", Floor " + floor);
        }
    }

    // Same classification rules used in VehicleLocator, matches vehicle type text to a class
    private VehicleClass classifyVehicle(String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "suv":
            case "car":
            case "jeep":
            case "pickup truck":
            case "pickup":
                return VehicleClass.LIGHT;

            case "motorcycle":
            case "bike":
            case "motorbike":
                return VehicleClass.MOTORCYCLE;

            case "truck":
            case "bus":
                return VehicleClass.HEAVY;

            default:
                return null;
        }
    }
}