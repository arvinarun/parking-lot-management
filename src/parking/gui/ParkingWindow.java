package parking.gui;

import parking.core.ParkingLot;
import parking.reservation.ReservationManager;
import parking.payment.PaymentManager;

import javax.swing.*;

public class ParkingWindow extends JFrame {

    // Constructor builds the parking operations window
    public ParkingWindow(ParkingLot parkingLot, ReservationManager reservationManager, PaymentManager paymentManager) {

        setTitle("Parking Lot - Parking");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add parking operation tabs
        tabbedPane.addTab("Entry", new EntryPanel(parkingLot, reservationManager));
        tabbedPane.addTab("Payment", new PaymentPanel(paymentManager));
        tabbedPane.addTab("Exit", new ExitPanel(parkingLot, paymentManager));

        add(tabbedPane);
    }
}