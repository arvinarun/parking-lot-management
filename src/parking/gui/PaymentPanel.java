package parking.gui;

import parking.payment.Payment;
import parking.payment.PaymentManager;
import parking.payment.PaymentMethod;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PaymentPanel extends JPanel {

    private PaymentManager paymentManager;

    private JTextField vehicleNumberField;
    private JComboBox<PaymentMethod> methodDropdown;
    private JLabel resultLabel;

    private static final Color CARD_BG = new Color(43, 32, 28);
    private static final Color INPUT_BG = new Color(59, 44, 39);
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);
    private static final Color TEXT_WHITE = Color.WHITE;

    public PaymentPanel(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;

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

        // Vehicle Number
        gbc.gridx = 0; gbc.gridy = 0;
        formCard.add(createFormLabel("Vehicle Number:"), gbc);

        vehicleNumberField = createStyledTextField();
        gbc.gridx = 1;
        formCard.add(vehicleNumberField, gbc);

        // Payment Method
        gbc.gridx = 0; gbc.gridy = 1;
        formCard.add(createFormLabel("Payment Method:"), gbc);

        methodDropdown = new JComboBox<>(PaymentMethod.values());
        styleComboBox(methodDropdown);
        gbc.gridx = 1;
        formCard.add(methodDropdown, gbc);

        // Submit Button
        JButton submitButton = createActionButton("Pay Now");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        formCard.add(submitButton, gbc);

        // Result Label
        resultLabel = new JLabel(" ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        resultLabel.setForeground(ACCENT_GREEN);
        gbc.gridy = 3;
        formCard.add(resultLabel, gbc);

        add(formCard);

        submitButton.addActionListener(e -> handlePayment());
    }

    private void handlePayment() {
        String vehicleNumber = vehicleNumberField.getText().trim();
        PaymentMethod method = (PaymentMethod) methodDropdown.getSelectedItem();

        if (vehicleNumber.isEmpty()) {
            resultLabel.setText("Please enter a vehicle number.");
            return;
        }

        Payment payment = paymentManager.createPayment(vehicleNumber, method);
        if (payment == null) {
            resultLabel.setText("Vehicle not found. Please check vehicle number.");
            return;
        }

        paymentManager.markAsPaid(vehicleNumber);
        resultLabel.setText("Payment successful. Amount: LKR" + payment.getAmount());

        vehicleNumberField.setText("");
        methodDropdown.setSelectedIndex(0);
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