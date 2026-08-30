package parking.gui;

import parking.core.ParkingLot;
import parking.payment.Payment;
import parking.payment.PaymentManager;
import parking.payment.PaymentMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitPanel extends JPanel {

    // Shared objects needed to check payment and exit the vehicle
    private ParkingLot parkingLot;
    private PaymentManager paymentManager;

    // Input fields
    private JTextField vehicleNumberField;
    private JComboBox<PaymentMethod> methodDropdown;

    // Label to show the result message
    private JLabel resultLabel;

    // Constructor builds the exit tab layout
    public ExitPanel(ParkingLot parkingLot, PaymentManager paymentManager) {
        this.parkingLot = parkingLot;
        this.paymentManager = paymentManager;

        // Use a simple vertical layout, one row per item
        setLayout(new GridLayout(5, 2, 5, 5));

        // Vehicle number row
        add(new JLabel("Vehicle Number:"));
        vehicleNumberField = new JTextField();
        add(vehicleNumberField);

        // Payment method row, used only if payment is still needed
        add(new JLabel("Payment Method (if unpaid):"));
        methodDropdown = new JComboBox<>(PaymentMethod.values());
        add(methodDropdown);

        // Pay button, in case payment was not done on the payment tab
        JButton payButton = new JButton("Pay Now");
        add(payButton);

        // Exit button, checks payment and removes the vehicle if paid
        JButton exitButton = new JButton("Exit");
        add(exitButton);

        // Result message label
        resultLabel = new JLabel(" ");
        add(resultLabel);

        // When Pay Now is clicked, run the pay logic
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handlePay();
            }
        });

        // When Exit is clicked, run the exit logic
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleExit();
            }
        });
    }

    // Runs when the user clicks Pay Now
    private void handlePay() {

        String vehicleNumber = vehicleNumberField.getText().trim();
        PaymentMethod method = (PaymentMethod) methodDropdown.getSelectedItem();

        if (vehicleNumber.isEmpty()) {
            resultLabel.setText("Please enter a vehicle number.");
            return;
        }

        // If already paid, no need to pay again
        if (paymentManager.isPaid(vehicleNumber)) {
            resultLabel.setText("This vehicle is already paid.");
            return;
        }

        // Create the payment, this works out the amount owed
        Payment payment = paymentManager.createPayment(vehicleNumber, method);

        if (payment == null) {
            resultLabel.setText("Vehicle not found. Please check your vehicle number.");
            return;
        }

        // Mark it as paid
        paymentManager.markAsPaid(vehicleNumber);

        resultLabel.setText("Payment successful. Amount: " + payment.getAmount() + ". You may now exit.");
    }

    // Runs when the user clicks Exit
    private void handleExit() {

        String vehicleNumber = vehicleNumberField.getText().trim();

        if (vehicleNumber.isEmpty()) {
            resultLabel.setText("Please enter a vehicle number.");
            return;
        }

        // exitVehicle already checks payment status and gives the right message
        String result = parkingLot.exitVehicle(vehicleNumber, paymentManager);
        resultLabel.setText(result);
    }
}