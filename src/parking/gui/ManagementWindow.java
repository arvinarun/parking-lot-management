package parking.gui;

import parking.core.ParkingLot;
import parking.core.ParkingSpace;
import parking.payment.Payment;
import parking.payment.PaymentManager;
import parking.reservation.Reservation;
import parking.reservation.ReservationManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ManagementWindow extends JFrame {

    // Shared objects needed to read all the data
    private ParkingLot parkingLot;
    private ReservationManager reservationManager;
    private PaymentManager paymentManager;

    // Table models, these hold the data shown in each table
    private DefaultTableModel spacesModel;
    private DefaultTableModel reservationsModel;
    private DefaultTableModel paymentsModel;

    // Constructor builds the management window
    public ManagementWindow(ParkingLot parkingLot, ReservationManager reservationManager, PaymentManager paymentManager) {
        this.parkingLot = parkingLot;
        this.reservationManager = reservationManager;
        this.paymentManager = paymentManager;

        // Set window title and basic settings
        setTitle("Parking Lot - Management");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the tabbed pane to hold the 3 tables
        JTabbedPane tabbedPane = new JTabbedPane();

        // Set up column names for each table
        spacesModel = new DefaultTableModel(new String[]{"Space", "Floor", "Class", "Status", "Vehicle"}, 0);
        reservationsModel = new DefaultTableModel(new String[]{"ID", "Vehicle", "Status", "Price"}, 0);
        paymentsModel = new DefaultTableModel(new String[]{"ID", "Vehicle", "Amount", "Status"}, 0);

        // Add each table inside a scroll pane, wrapped in its own tab
        tabbedPane.addTab("Spaces", new JScrollPane(new JTable(spacesModel)));
        tabbedPane.addTab("Reservations", new JScrollPane(new JTable(reservationsModel)));
        tabbedPane.addTab("Payments", new JScrollPane(new JTable(paymentsModel)));

        // Refresh button, lets staff manually update the tables
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTables();
            }
        });

        // Put the tabbed pane in the center and the refresh button at the bottom
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);

        // Fill the tables with the current data right away
        refreshTables();

        // Timer that runs every 30 seconds, refreshes tables and checks reservations for expiry or no-show
        Timer autoTimer = new Timer(30000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reservationManager.checkReservations(LocalDateTime.now());
                refreshTables();
            }
        });
        autoTimer.start();
    }

    // Clears and refills all 3 tables with the latest data
    private void refreshTables() {
        refreshSpacesTable();
        refreshReservationsTable();
        refreshPaymentsTable();
    }

    // Refills the spaces table
    private void refreshSpacesTable() {

        // Remove all existing rows first
        spacesModel.setRowCount(0);

        for (ParkingSpace space : parkingLot.getParkingSpaces()) {

            int floor = space.getSpaceNumber() <= 50 ? 1 : 2;

            String status;
            String vehicleNumber = "-";

            if (space.isOccupied()) {
                status = "Occupied";
                vehicleNumber = space.getOccupiedVehicle().getVehicleNumber();
            } else if (space.isReserved()) {
                status = "Reserved";
                vehicleNumber = space.getReservedVehicle().getVehicleNumber();
            } else {
                status = "Free";
            }

            // Add one row per space
            spacesModel.addRow(new Object[]{
                    space.getSpaceNumber(),
                    floor,
                    space.getAllowedClass(),
                    status,
                    vehicleNumber
            });
        }
    }

    // Refills the reservations table
    private void refreshReservationsTable() {

        reservationsModel.setRowCount(0);

        for (Reservation reservation : reservationManager.getReservations()) {
            reservationsModel.addRow(new Object[]{
                    reservation.getReservationId(),
                    reservation.getVehicle().getVehicleNumber(),
                    reservation.getStatus(),
                    reservation.getPrice()
            });
        }
    }

    // Refills the payments table
    private void refreshPaymentsTable() {

        paymentsModel.setRowCount(0);

        for (Payment payment : paymentManager.getPayments()) {
            paymentsModel.addRow(new Object[]{
                    payment.getPaymentId(),
                    payment.getVehicle().getVehicleNumber(),
                    payment.getAmount(),
                    payment.getPaymentStatus()
            });
        }
    }
}