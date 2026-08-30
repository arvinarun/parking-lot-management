package parking.gui;

import parking.core.Vehicle;
import parking.core.VehicleClass;
import parking.reservation.Reservation;
import parking.reservation.ReservationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.time.LocalDateTime;
public class ReservationPanel extends JPanel {

    // Shared object needed to make a reservation
    private ReservationManager reservationManager;

    // Input fields
    private JTextField nameField;
    private JTextField vehicleNumberField;
    private JComboBox<String> vehicleTypeDropdown;
    
    private JSpinner startDateSpinner;
    private JSpinner startHourSpinner;
    private JSpinner startMinuteSpinner;

    private JSpinner endDateSpinner;
    private JSpinner endHourSpinner;
    private JSpinner endMinuteSpinner;

    // Label to show the result message
    private JLabel resultLabel;

    // Constructor builds the reservation tab layout
    public ReservationPanel(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;

        // Use a simple vertical layout, one row per item
        setLayout(new GridLayout(9, 2, 5, 5));

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

        String[] vehicleTypes = {"Car", "SUV", "Jeep", "Pickup Truck", "Motorcycle", "Bike", "Truck", "Bus"};

        vehicleTypeDropdown = new JComboBox<>(vehicleTypes);
        add(vehicleTypeDropdown);

        // Start date
        add(new JLabel("Start Date:"));
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy"));
        add(startDateSpinner);

        // Start time
        add(new JLabel("Start Time:"));

        JPanel startTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));

        startHourSpinner = new JSpinner(new SpinnerNumberModel(12, 0, 23, 1));
        startMinuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

        startTimePanel.add(startHourSpinner);
        startTimePanel.add(new JLabel(":"));
        startTimePanel.add(startMinuteSpinner);

        add(startTimePanel);

        // End date
        add(new JLabel("End Date:"));
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy"));
        add(endDateSpinner);

        // End time
        add(new JLabel("End Time:"));

        JPanel endTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));

        endHourSpinner = new JSpinner(new SpinnerNumberModel(13, 0, 23, 1));
        endMinuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

        endTimePanel.add(endHourSpinner);
        endTimePanel.add(new JLabel(":"));
        endTimePanel.add(endMinuteSpinner);

        add(endTimePanel);

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
        String vehicleType = (String) vehicleTypeDropdown.getSelectedItem();

        // Check inputs are not empty
        if (name.isEmpty() || vehicleNumber.isEmpty() || vehicleType.isEmpty()) {
            resultLabel.setText("Please fill in all fields.");
            return;
        }

        // Classify the vehicle type entered
        VehicleClass vehicleClass = classifyVehicle(vehicleType);

        if (vehicleClass == null) {
            resultLabel.setText("Invalid vehicle type. Please try again.");
            return;
        }

        Date startDate = (Date) startDateSpinner.getValue();
        Date endDate = (Date) endDateSpinner.getValue();

        LocalDateTime startTime = LocalDateTime.ofInstant(startDate.toInstant(), java.time.ZoneId.systemDefault()).withHour((int) startHourSpinner.getValue())
        .withMinute((int) startMinuteSpinner.getValue()).withSecond(0).withNano(0);

        LocalDateTime endTime = LocalDateTime.ofInstant(endDate.toInstant(),java.time.ZoneId.systemDefault()).withHour((int) endHourSpinner.getValue())
        .withMinute((int) endMinuteSpinner.getValue()).withSecond(0).withNano(0);

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
        } 
        else {
            resultLabel.setText("Reserved! ID: " + reservation.getReservationId() + ", Price: " + reservation.getPrice());

            nameField.setText("");
            vehicleNumberField.setText("");
            vehicleTypeDropdown.setSelectedIndex(0);
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