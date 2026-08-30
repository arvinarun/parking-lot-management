package parking.gui;

import parking.core.Vehicle;
import parking.core.VehicleClass;
import parking.reservation.Reservation;
import parking.reservation.ReservationManager;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ReservationPanel extends JPanel {

    private ReservationManager reservationManager;

    private JTextField nameField;
    private JTextField vehicleNumberField;
    private JComboBox<String> vehicleTypeDropdown;

    private JSpinner startDateSpinner;
    private JSpinner startHourSpinner;
    private JSpinner startMinuteSpinner;

    private JSpinner endDateSpinner;
    private JSpinner endHourSpinner;
    private JSpinner endMinuteSpinner;

    private JLabel resultLabel;

    private static final Color CARD_BG = new Color(43, 32, 28);
    private static final Color INPUT_BG = new Color(59, 44, 39);
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);
    private static final Color TEXT_WHITE = Color.WHITE;

    public ReservationPanel(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;

        setBackground(new Color(27, 20, 18));
        setLayout(new GridBagLayout());

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(new Color(65, 50, 44), 1, true),
                new EmptyBorder(20, 25, 20, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
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

        // Start Date & Time
        gbc.gridx = 0; gbc.gridy = 3;
        formCard.add(createFormLabel("Start Date:"), gbc);

        startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy"));
        styleSpinner(startDateSpinner);
        gbc.gridx = 1;
        formCard.add(startDateSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formCard.add(createFormLabel("Start Time:"), gbc);

        JPanel startTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        startTimePanel.setBackground(CARD_BG);
        startHourSpinner = new JSpinner(new SpinnerNumberModel(12, 0, 23, 1));
        startMinuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        styleSpinner(startHourSpinner);
        styleSpinner(startMinuteSpinner);

        JLabel colon1 = new JLabel(":");
        colon1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        colon1.setForeground(TEXT_WHITE);

        startTimePanel.add(startHourSpinner);
        startTimePanel.add(colon1);
        startTimePanel.add(startMinuteSpinner);
        gbc.gridx = 1;
        formCard.add(startTimePanel, gbc);

        // End Date & Time
        gbc.gridx = 0; gbc.gridy = 5;
        formCard.add(createFormLabel("End Date:"), gbc);

        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy"));
        styleSpinner(endDateSpinner);
        gbc.gridx = 1;
        formCard.add(endDateSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formCard.add(createFormLabel("End Time:"), gbc);

        JPanel endTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        endTimePanel.setBackground(CARD_BG);
        endHourSpinner = new JSpinner(new SpinnerNumberModel(13, 0, 23, 1));
        endMinuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        styleSpinner(endHourSpinner);
        styleSpinner(endMinuteSpinner);

        JLabel colon2 = new JLabel(":");
        colon2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        colon2.setForeground(TEXT_WHITE);

        endTimePanel.add(endHourSpinner);
        endTimePanel.add(colon2);
        endTimePanel.add(endMinuteSpinner);
        gbc.gridx = 1;
        formCard.add(endTimePanel, gbc);

        // Submit Button
        JButton submitButton = createActionButton("Reserve Space");
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 8, 8, 8);
        formCard.add(submitButton, gbc);

        // Result Label
        resultLabel = new JLabel(" ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        resultLabel.setForeground(ACCENT_GREEN);
        gbc.gridy = 8;
        formCard.add(resultLabel, gbc);

        add(formCard);

        submitButton.addActionListener(e -> handleReservation());
    }

    private void handleReservation() {
        String name = nameField.getText().trim();
        String vehicleNumber = vehicleNumberField.getText().trim();
        String vehicleType = (String) vehicleTypeDropdown.getSelectedItem();

        if (name.isEmpty() || vehicleNumber.isEmpty() || vehicleType == null) {
            resultLabel.setText("Please fill in all fields.");
            return;
        }

        VehicleClass vehicleClass = classifyVehicle(vehicleType);
        if (vehicleClass == null) {
            resultLabel.setText("Invalid vehicle type.");
            return;
        }

        Date startDate = (Date) startDateSpinner.getValue();
        Date endDate = (Date) endDateSpinner.getValue();

        LocalDateTime startTime = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault())
                .withHour((int) startHourSpinner.getValue())
                .withMinute((int) startMinuteSpinner.getValue())
                .withSecond(0).withNano(0);

        LocalDateTime endTime = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault())
                .withHour((int) endHourSpinner.getValue())
                .withMinute((int) endMinuteSpinner.getValue())
                .withSecond(0).withNano(0);

        if (!endTime.isAfter(startTime)) {
            resultLabel.setText("End time must be after start time.");
            return;
        }

        Vehicle vehicle = new Vehicle(name, vehicleNumber, vehicleType, vehicleClass);
        Reservation reservation = reservationManager.createReservation(vehicle, startTime, endTime);

        if (reservation == null) {
            resultLabel.setText("No available space for your vehicle type.");
        } else {
            resultLabel.setText("Reserved! ID: " + reservation.getReservationId() + ", Price: LKR" + reservation.getPrice());
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
        JTextField tf = new JTextField(15);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tf.setBackground(INPUT_BG);
        tf.setForeground(TEXT_WHITE);
        tf.setCaretColor(TEXT_WHITE);
        tf.setBorder(new CompoundBorder(
                new LineBorder(ACCENT_GREEN, 1),
                new EmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }

    private void styleComboBox(JComboBox<?> box) {
        box.setFont(new Font("Segoe UI", Font.BOLD, 15));
        box.setBackground(INPUT_BG);
        box.setForeground(TEXT_WHITE);
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setFont(new Font("Segoe UI", Font.BOLD, 14));
        spinner.setBackground(INPUT_BG);
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().setBackground(INPUT_BG);
            ((JSpinner.DefaultEditor) editor).getTextField().setForeground(TEXT_WHITE);
        }
    }

    private JButton createActionButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(TEXT_WHITE);
        btn.setBackground(ACCENT_GREEN);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(0, 42));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}