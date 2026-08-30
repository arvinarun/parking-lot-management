package parking.gui;

import parking.core.ParkingLot;
import parking.payment.PaymentManager;
import parking.reservation.ReservationManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainWindow extends JFrame {

    private ParkingLot parkingLot;
    private ReservationManager reservationManager;
    private PaymentManager paymentManager;

    // Palette Colors
    private static final Color BG_DARK = new Color(27, 20, 18);
    private static final Color CARD_BG = new Color(43, 32, 28);
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Color TEXT_MUTED = new Color(208, 196, 188);

    // Subtle Easter Egg Palette
    private static final Color OVERLAY_CARD = new Color(34, 25, 22);
    private static final Color GOLD_ACCENT = new Color(212, 172, 13);
    private static final Color SOFT_TEXT = new Color(185, 175, 168);

    // Easter egg variables
    private int clickCount = 0;
    private long lastClickTime = 0;

    public MainWindow(ParkingLot parkingLot, ReservationManager reservationManager, PaymentManager paymentManager) {

        this.parkingLot = parkingLot;
        this.reservationManager = reservationManager;
        this.paymentManager = paymentManager;

        setTitle("Parking Management System");
        setSize(650, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Background Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BG_DARK);
        mainPanel.setBorder(new EmptyBorder(35, 45, 35, 45));

        // Header Panel Card
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(CARD_BG);
        headerPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(65, 50, 44), 1, true),
                new EmptyBorder(25, 20, 25, 20)
        ));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("PARKING MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ACCENT_GREEN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel versionLabel = new JLabel("v1.0 • System Active");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        versionLabel.setForeground(TEXT_MUTED);
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Easter egg click listener
        versionLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        versionLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                long currentTime = System.currentTimeMillis();

                // Reset counter if more than 2 seconds elapse between clicks
                if (currentTime - lastClickTime > 2000) {
                    clickCount = 0;
                }

                clickCount++;
                lastClickTime = currentTime;

                // 5 clicks triggers the Easter egg
                if (clickCount >= 5) {
                    clickCount = 0;
                    showEasterEgg();
                }
            }
        });

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(versionLabel);

        mainPanel.add(headerPanel);

        // Visual Separator Bar
        JPanel greenLine = new JPanel();
        greenLine.setBackground(ACCENT_GREEN);
        greenLine.setMaximumSize(new Dimension(520, 3));
        greenLine.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(greenLine);
        mainPanel.add(Box.createVerticalStrut(30));

        // Navigation Buttons
        JButton parkingButton = createStyledButton("PARKING OPERATIONS");
        JButton servicesButton = createStyledButton("SERVICES");
        JButton managementButton = createStyledButton("MANAGEMENT");

        mainPanel.add(parkingButton);
        mainPanel.add(Box.createVerticalStrut(18));
        mainPanel.add(servicesButton);
        mainPanel.add(Box.createVerticalStrut(18));
        mainPanel.add(managementButton);

        mainPanel.add(Box.createVerticalStrut(30));

        // Status Indicator
        JLabel statusLabel = new JLabel("● SYSTEM READY");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statusLabel.setForeground(ACCENT_GREEN);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(statusLabel);

        add(mainPanel);

        // Parking Operations Action
        parkingButton.addActionListener(e -> {
            ParkingWindow parkingWindow = new ParkingWindow(
                    this.parkingLot,
                    this.reservationManager,
                    this.paymentManager
            );
            parkingWindow.setVisible(true);
        });

        // Services Action
        servicesButton.addActionListener(e -> {
            ServiceWindow serviceWindow = new ServiceWindow(
                    this.parkingLot,
                    this.reservationManager
            );
            serviceWindow.setVisible(true);
        });

        // Management Action
        managementButton.addActionListener(e -> {
            ManagementWindow managementWindow = new ManagementWindow(
                    this.parkingLot,
                    this.reservationManager,
                    this.paymentManager
            );
            managementWindow.setVisible(true);
        });
    }

    // Custom-styled dark Easter egg modal
    private void showEasterEgg() {
        JDialog dialog = new JDialog(this, "SYSTEM OVERRIDE", true);
        dialog.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(OVERLAY_CARD);
        panel.setBorder(new CompoundBorder(
                new LineBorder(GOLD_ACCENT, 1, true),
                new EmptyBorder(30, 40, 25, 40)
        ));

        // Subtitle & Header
        JLabel sysLabel = new JLabel("SYSTEM OVERRIDE DETECTED");
        sysLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sysLabel.setForeground(GOLD_ACCENT);
        sysLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("You found the hidden layer.");
        subLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        subLabel.setForeground(TEXT_WHITE);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Divider
        JPanel sep = new JPanel();
        sep.setBackground(new Color(65, 50, 44));
        sep.setMaximumSize(new Dimension(420, 1));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Team credits container (Clean vertical list layout)
        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.Y_AXIS));
        teamPanel.setBackground(OVERLAY_CARD);
        teamPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        teamPanel.add(createMemberCard("Arvin", "Team Lead • Core Architecture & GUI"));
        teamPanel.add(Box.createVerticalStrut(10));
        teamPanel.add(createMemberCard("Sonal", "Reservation Engine"));
        teamPanel.add(Box.createVerticalStrut(10));
        teamPanel.add(createMemberCard("Methuli", "Payment Processing Module"));
        teamPanel.add(Box.createVerticalStrut(10));
        teamPanel.add(createMemberCard("Harshitha", "Vehicle Locator System"));

        // Quote
        JLabel quoteLabel = new JLabel("\"Happy parking.\"");
        quoteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        quoteLabel.setForeground(GOLD_ACCENT);
        quoteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Close button
        JButton closeBtn = new JButton("DISMISS");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        closeBtn.setForeground(TEXT_WHITE);
        closeBtn.setBackground(CARD_BG);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.setBorder(new CompoundBorder(
                new LineBorder(new Color(75, 60, 52), 1, true),
                new EmptyBorder(8, 26, 8, 26)
        ));
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.addActionListener(e -> dialog.dispose());

        panel.add(sysLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subLabel);
        panel.add(Box.createVerticalStrut(18));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(20));
        panel.add(teamPanel);
        panel.add(Box.createVerticalStrut(22));
        panel.add(quoteLabel);
        panel.add(Box.createVerticalStrut(22));
        panel.add(closeBtn);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JPanel createMemberCard(String name, String role) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(OVERLAY_CARD);
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLbl = new JLabel(name);
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLbl.setForeground(TEXT_WHITE);
        nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roleLbl = new JLabel(role);
        roleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        roleLbl.setForeground(SOFT_TEXT);
        roleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(nameLbl);
        card.add(Box.createVerticalStrut(2));
        card.add(roleLbl);

        return card;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(TEXT_WHITE);
        button.setBackground(CARD_BG);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Border lineBorder = new LineBorder(ACCENT_GREEN, 2, true);
        Border paddingBorder = new EmptyBorder(12, 20, 12, 20);

        button.setBorder(new CompoundBorder(lineBorder, paddingBorder));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(520, 55));
        button.setPreferredSize(new Dimension(520, 55));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_GREEN);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(CARD_BG);
            }
        });

        return button;
    }
}