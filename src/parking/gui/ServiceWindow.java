package parking.gui;

import parking.core.ParkingLot;
import parking.reservation.ReservationManager;

import javax.swing.*;

public class ServiceWindow extends JFrame {

    // Constructor builds the service window
    public ServiceWindow(ParkingLot parkingLot, ReservationManager reservationManager) {

        setTitle("Parking Lot - Services");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add service tabs
        tabbedPane.addTab("Reservation", new ReservationPanel(reservationManager));
        tabbedPane.addTab("Find Vehicle", new FindVehiclePanel(parkingLot));

        add(tabbedPane);
    }
}