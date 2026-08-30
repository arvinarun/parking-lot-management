package parking.gui;

import parking.core.ParkingLot;
import parking.core.ParkingSpace;
import parking.core.Vehicle;
import parking.core.VehicleClass;
import parking.reservation.Reservation;
import parking.reservation.ReservationManager;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EntryPanel extends JPanel {

    private ParkingLot parkingLot;
    private ReservationManager reservationManager;

    private JTextField nameField;
    private JTextField vehicleNumberField;
    private JComboBox<String> vehicleTypeDropdown;
    private JLabel resultLabel;

    private static final Color CARD_BG = new Color(43, 32, 28);
    private static final Color INPUT_BG = new Color(59, 44, 39);
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);
    private static final Color TEXT_WHITE = Color.WHITE;

    public EntryPanel(ParkingLot parkingLot, ReservationManager reservationManager) {
        this.parkingLot = parkingLot;
        this.reservationManager = reservationManager;

        setBackground(new Color(27, 20, 18));
        setLayout(new GridBagLayout());

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(new Color(65, 50, 44), 1, true),
                new EmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        formCard.add(createFormLabel("Name:"), gbc);

        nameField = createStyledTextField();
        gbc.gridx = 1;
        formCard.add(nameField, gbc);

        // Vehicle Number
        gbc.gridx = 0; gbc.gridy = 1;
        formCard.add(createFormLabel("Vehicle Number:"), gbc);

        vehicleNumberField = createStyledTextField();
        gbc.gridx = 1;
        formCard.add(vehicleNumberField, gbc);

        // Vehicle Type
        gbc.gridx = 0; gbc.gridy = 2;
        formCard.add(createFormLabel("Vehicle Type:"), gbc);

        String[] vehicleTypes = {"Car", "SUV", "Jeep", "Pickup Truck", "Motorcycle", "Bike", "Truck", "Bus"};
        vehicleTypeDropdown = new JComboBox<>(vehicleTypes);
        styleComboBox(vehicleTypeDropdown);
        gbc.gridx = 1;
        formCard.add(vehicleTypeDropdown, gbc);

        // Submit Button
        JButton submitButton = createActionButton("Enter Parking Lot");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        formCard.add(submitButton, gbc);

        // Result Label
        resultLabel = new JLabel(" ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        resultLabel.setForeground(ACCENT_GREEN);
        gbc.gridy = 4;
        formCard.add(resultLabel, gbc);

        add(formCard);

        submitButton.addActionListener(e -> handleEntry());
    }

    private void handleEntry() {
        String name = nameField.getText().trim();
        String vehicleNumber = vehicleNumberField.getText().trim();
        String vehicleType = (String) vehicleTypeDropdown.getSelectedItem();

        if (name.isEmpty() || vehicleNumber.isEmpty() || vehicleType == null) {
            resultLabel.setText("Please fill in all fields.");
            return;
        }

        Reservation reservation = reservationManager.findReservationByVehicle(vehicleNumber);
        ParkingSpace space;

        if (reservation != null) {
            Vehicle reservedVehicle = reservation.getVehicle();
            space = parkingLot.parkVehicle(reservedVehicle);
            if (space != null) {
                reservationManager.markReservationUsed(vehicleNumber);
            }
        } else {
            VehicleClass vehicleClass = classifyVehicle(vehicleType);
            if (vehicleClass == null) {
                resultLabel.setText("Invalid vehicle type. Please try again.");
                return;
            }
            Vehicle vehicle = new Vehicle(name, vehicleNumber, vehicleType, vehicleClass);
            space = parkingLot.parkVehicle(vehicle);
        }

        if (space == null) {
            resultLabel.setText("No available space for your vehicle type.");
        } else {
            int floor = space.getSpaceNumber() <= 50 ? 1 : 2;
            resultLabel.setText("Park at Space " + space.getSpaceNumber() + ", Floor " + floor);
            nameField.setText("");
            vehicleNumberField.setText("");
            vehicleTypeDropdown.setSelectedIndex(0);
        }
    }

    private VehicleClass classifyVehicle(String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "suv": case "car": case "jeep": case "pickup truck": case "pickup":
                return VehicleClass.LIGHT;
            case "motorcycle": case "bike": case "motorbike":
                return VehicleClass.MOTORCYCLE;
            case "truck": case "bus":
                return VehicleClass.HEAVY;
            default:
                return null;
        }
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(TEXT_WHITE);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField(16);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tf.setBackground(INPUT_BG);
        tf.setForeground(TEXT_WHITE);
        tf.setCaretColor(TEXT_WHITE);
        tf.setBorder(new CompoundBorder(
                new LineBorder(ACCENT_GREEN, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        return tf;
    }

    private void styleComboBox(JComboBox<?> box) {
        box.setFont(new Font("Segoe UI", Font.BOLD, 15));
        box.setBackground(INPUT_BG);
        box.setForeground(TEXT_WHITE);
    }

    private JButton createActionButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(TEXT_WHITE);
        btn.setBackground(ACCENT_GREEN);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(0, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}