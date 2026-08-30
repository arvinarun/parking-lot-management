package parking.gui;

import parking.core.ParkingLot;
import parking.core.ParkingSpace;
import parking.core.Vehicle;
import parking.payment.Payment;
import parking.payment.PaymentManager;
import parking.reservation.Reservation;
import parking.reservation.ReservationManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManagementWindow extends JFrame {

    private ParkingLot parkingLot;
    private ReservationManager reservationManager;
    private PaymentManager paymentManager;

    private DefaultTableModel spacesModel;
    private DefaultTableModel reservationsModel;
    private DefaultTableModel paymentsModel;
    private DefaultTableModel vehicleHistoryModel;

    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    private static final Color BG_DARK = new Color(27, 20, 18);
    private static final Color CARD_BG = new Color(43, 32, 28);
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);

    public ManagementWindow(ParkingLot parkingLot, ReservationManager reservationManager, PaymentManager paymentManager) {

        this.parkingLot = parkingLot;
        this.reservationManager = reservationManager;
        this.paymentManager = paymentManager;

        setTitle("Management Console");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Enforce dark tab styling defaults
        UIManager.put("TabbedPane.background", CARD_BG);
        UIManager.put("TabbedPane.darkShadow", BG_DARK);
        UIManager.put("TabbedPane.shadow", BG_DARK);
        UIManager.put("TabbedPane.light", CARD_BG);
        UIManager.put("TabbedPane.highlight", CARD_BG);
        UIManager.put("TabbedPane.focus", ACCENT_GREEN);
        UIManager.put("TabbedPane.contentAreaColor", BG_DARK);
        UIManager.put("TabbedPane.unselectedBackground", CARD_BG);
        UIManager.put("TabbedPane.selected", ACCENT_GREEN);

        getContentPane().setBackground(BG_DARK);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tabbedPane.setBackground(CARD_BG);
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setOpaque(true);

        spacesModel = new DefaultTableModel(new String[]{"Space", "Floor", "Class", "Status", "Vehicle"}, 0);
        reservationsModel = new DefaultTableModel(new String[]{"ID", "Vehicle", "Status", "Price"}, 0);
        paymentsModel = new DefaultTableModel(new String[]{"ID", "Vehicle", "Amount", "Status"}, 0);
        vehicleHistoryModel = new DefaultTableModel(new String[]{"Name", "Vehicle", "Type", "Entry Time", "Exit Time"}, 0);

        JTable spacesTable = createStyledTable(spacesModel);
        JTable reservationsTable = createStyledTable(reservationsModel);
        JTable paymentsTable = createStyledTable(paymentsModel);
        JTable vehicleHistoryTable = createStyledTable(vehicleHistoryModel);

        tabbedPane.addTab(" Spaces ", createDarkScrollPane(spacesTable));
        tabbedPane.addTab(" Reservations ", createDarkScrollPane(reservationsTable));
        tabbedPane.addTab(" Payments ", createDarkScrollPane(paymentsTable));
        tabbedPane.addTab(" Vehicle History ", createDarkScrollPane(vehicleHistoryTable));

        JButton refreshButton = new JButton("REFRESH ALL DATA");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(ACCENT_GREEN);
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(0, 48));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(e -> refreshTables());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BG_DARK);
        bottomPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        bottomPanel.add(refreshButton, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshTables();

        Timer autoTimer = new Timer(30000, e -> {
            reservationManager.checkReservations(LocalDateTime.now());
            refreshTables();
        });
        autoTimer.start();
    }

    private JScrollPane createDarkScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(36, 27, 23));
        scrollPane.setBackground(BG_DARK);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(34);
        table.setBackground(new Color(36, 27, 23));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(55, 42, 36));
        table.setSelectionBackground(ACCENT_GREEN);
        table.setSelectionForeground(Color.WHITE);
        table.setAutoCreateRowSorter(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(CARD_BG);
        header.setForeground(ACCENT_GREEN);
        header.setPreferredSize(new Dimension(0, 38));

        ((DefaultTableCellRenderer) header.getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        return table;
    }

    private void refreshTables() {
        refreshSpacesTable();
        refreshReservationsTable();
        refreshPaymentsTable();
        refreshVehicleHistoryTable();
    }

    private void refreshSpacesTable() {
        spacesModel.setRowCount(0);
        for (ParkingSpace space : parkingLot.getParkingSpaces()) {
            int floor = space.getSpaceNumber() <= 50 ? 1 : 2;
            String status = space.isOccupied() ? "Occupied" : space.isReserved() ? "Reserved" : "Free";
            String vehicleNumber = space.isOccupied() ? space.getOccupiedVehicle().getVehicleNumber()
                    : space.isReserved() ? space.getReservedVehicle().getVehicleNumber() : "-";

            spacesModel.addRow(new Object[]{
                    space.getSpaceNumber(), floor, space.getAllowedClass(), status, vehicleNumber
            });
        }
    }

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

    private void refreshVehicleHistoryTable() {
        vehicleHistoryModel.setRowCount(0);
        for (Vehicle vehicle : parkingLot.getVehicleHistory()) {
            String entryTime = vehicle.getEntryTime().format(DISPLAY_FORMAT);
            String exitTime = vehicle.getExitTime() != null ? vehicle.getExitTime().format(DISPLAY_FORMAT) : "-";

            vehicleHistoryModel.addRow(new Object[]{
                    vehicle.getName(),
                    vehicle.getVehicleNumber(),
                    vehicle.getVehicleType(),
                    entryTime,
                    exitTime
            });
        }
    }
}