package parking.gui;

import parking.payment.Payment;
import parking.payment.PaymentManager;
import parking.payment.PaymentMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentPanel extends JPanel {

    // Shared object needed to create and mark payments
    private PaymentManager paymentManager;

    // Input fields
    private JTextField vehicleNumberField;
    private JComboBox<PaymentMethod> methodDropdown;

    // Label to show the result message
    private JLabel resultLabel;

    // Constructor builds the payment tab layout
    public PaymentPanel(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;

        // Use a simple vertical layout, one row per item
        setLayout(new GridLayout(4, 2, 5, 5));

        // Vehicle number row
        add(new JLabel("Vehicle Number:"));
        vehicleNumberField = new JTextField();
        add(vehicleNumberField);

        // Payment method row, dropdown filled with the two enum values
        add(new JLabel("Payment Method:"));
        methodDropdown = new JComboBox<>(PaymentMethod.values());
        add(methodDropdown);

        // Submit button
        JButton submitButton = new JButton("Pay Now");
        add(submitButton);

        // Result message label
        resultLabel = new JLabel(" ");
        add(resultLabel);

        // When the button is clicked, run the payment logic
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handlePayment();
            }
        });
    }

    // Runs when the user submits the payment form
    private void handlePayment() {

        // Get the vehicle number and chosen payment method
        String vehicleNumber = vehicleNumberField.getText().trim();
        PaymentMethod method = (PaymentMethod) methodDropdown.getSelectedItem();

        // Check input is not empty
        if (vehicleNumber.isEmpty()) {
            resultLabel.setText("Please enter a vehicle number.");
            return;
        }

        // Try to create the payment, this works out the amount owed
        Payment payment = paymentManager.createPayment(vehicleNumber, method);

        if (payment == null) {
            resultLabel.setText("Vehicle not found. Please check your vehicle number.");
            return;
        }

        paymentManager.markAsPaid(vehicleNumber);

        resultLabel.setText("Payment successful. Amount: " + payment.getAmount());

        vehicleNumberField.setText("");
        methodDropdown.setSelectedIndex(0);
    }
}