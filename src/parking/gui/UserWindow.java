package parking.gui;

import parking.core.ParkingLot;
import parking.reservation.ReservationManager;
import parking.payment.PaymentManager;

import javax.swing.*;

public class UserWindow extends JFrame {

    // Constructor builds the window and adds all the tabs
    public UserWindow(ParkingLot parkingLot, ReservationManager reservationManager, PaymentManager paymentManager) {

        // Set window title and basic settings
        setTitle("Parking Lot - User");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the tabbed pane to hold all 5 tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add each tab, passing the shared objects each tab panel needs
        tabbedPane.addTab("Entry", new EntryPanel(parkingLot, reservationManager));
        tabbedPane.addTab("Reservation", new ReservationPanel(reservationManager));
        tabbedPane.addTab("Find Vehicle", new FindVehiclePanel(parkingLot));
        tabbedPane.addTab("Payment", new PaymentPanel(paymentManager));
        tabbedPane.addTab("Exit", new ExitPanel(parkingLot, paymentManager));

        // Add the tabbed pane to the window
        add(tabbedPane);
    }
}