package parking.gui;

import parking.core.Vehicle;
import parking.core.VehicleClass;
import parking.reservation.Reservation;
import parking.reservation.ReservationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationPanel extends JPanel {

    // Shared object needed to make a reservation
    private ReservationManager reservationManager;

    // Input fields
    private JTextField nameField;
    private JTextField vehicleNumberField;
    private JTextField vehicleTypeField;
    private JTextField startTimeField;
    private JTextField endTimeField;

    // Label to show the result message
    private JLabel resultLabel;

    // Format we expect the user to type date and time in
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Constructor builds the reservation tab layout
    public ReservationPanel(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;

        // Use a simple vertical layout, one row per item
        setLayout(new GridLayout(7, 2, 5, 5));

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

        // Start time row
        add(new JLabel("Start Time (yyyy-MM-dd HH:mm):"));
        startTimeField = new JTextField();
        add(startTimeField);

        // End time row
        add(new JLabel("End Time (yyyy-MM-dd HH:mm):"));
        endTimeField = new JTextField();
        add(endTimeField);

        // Submit button
        JButton submitButton = new JButton("Reserve Space");
        add(submitButton);

        // Result message label
        resultLabel = new JLabel(" ");
        add(resultLabel);

        // When the button is clicked, run the reservation logic
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleReservation();
            }
        });
    }

    // Runs when the user submits the reservation form
    private void handleReservation() {

        // Get the text from the input fields
        String name = nameField.getText().trim();
        String vehicleNumber = vehicleNumberField.getText().trim();
        String vehicleType = vehicleTypeField.getText().trim();
        String startText = startTimeField.getText().trim();
        String endText = endTimeField.getText().trim();

        // Check inputs are not empty
        if (name.isEmpty() || vehicleNumber.isEmpty() || vehicleType.isEmpty() || startText.isEmpty() || endText.isEmpty()) {
            resultLabel.setText("Please fill in all fields.");
            return;
        }

        // Classify the vehicle type entered
        VehicleClass vehicleClass = classifyVehicle(vehicleType);

        if (vehicleClass == null) {
            resultLabel.setText("Invalid vehicle type. Please try again.");
            return;
        }

        // Try to read the start and end time text as actual date and time values
        LocalDateTime startTime;
        LocalDateTime endTime;

        try {
            startTime = LocalDateTime.parse(startText, FORMAT);
            endTime = LocalDateTime.parse(endText, FORMAT);
        } catch (DateTimeParseException e) {
            resultLabel.setText("Invalid date format. Use yyyy-MM-dd HH:mm");
            return;
        }

        // End time must be after start time
        if (!endTime.isAfter(startTime)) {
            resultLabel.setText("End time must be after start time.");
            return;
        }

        // Create the vehicle for this reservation
        Vehicle vehicle = new Vehicle(name, vehicleNumber, vehicleType, vehicleClass);

        // Try to create the reservation
        Reservation reservation = reservationManager.createReservation(vehicle, startTime, endTime);

        // Show the result to the user
        if (reservation == null) {
            resultLabel.setText("No available space for your vehicle type.");
        } else {
            resultLabel.setText("Reserved! ID: " + reservation.getReservationId() + ", Price: " + reservation.getPrice());
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