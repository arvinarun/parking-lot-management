package parking.gui;

import parking.core.ParkingLot;
import parking.location.VehicleLocator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindVehiclePanel extends JPanel {

    // Object that does the actual vehicle searching
    private VehicleLocator vehicleLocator;

    // Input fields
    private JTextField nameField;
    private JTextField vehicleNumberField;
    private JTextField vehicleTypeField;

    // Label to show the result message
    private JLabel resultLabel;

    // Constructor builds the find vehicle tab layout
    public FindVehiclePanel(ParkingLot parkingLot) {

        // Create the locator using the shared parking lot
        vehicleLocator = new VehicleLocator(parkingLot);

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
        JButton submitButton = new JButton("Find Vehicle");
        add(submitButton);

        // Result message label
        resultLabel = new JLabel(" ");
        add(resultLabel);

        // When the button is clicked, run the find logic
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleFind();
            }
        });
    }

    // Runs when the user submits the find vehicle form
    private void handleFind() {

        // Get the text from the input fields
        String name = nameField.getText().trim();
        String vehicleNumber = vehicleNumberField.getText().trim();
        String vehicleType = vehicleTypeField.getText().trim();

        // Call the locator, it already handles empty checks and invalid type checks itself
        String result = vehicleLocator.findVehicle(name, vehicleNumber, vehicleType);

        // Show whatever message the locator returned
        resultLabel.setText(result);
    }
}