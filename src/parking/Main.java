package parking;

import parking.core.ParkingLot;
import parking.reservation.ReservationManager;
import parking.payment.PaymentManager;
import parking.gui.ServiceWindow;
import parking.gui.ManagementWindow;
import parking.gui.ParkingWindow;

public class Main {

    // Entry point of the program
    public static void main(String[] args) {

        // Create the shared objects used by both windows
        ParkingLot parkingLot = new ParkingLot();
        ReservationManager reservationManager = new ReservationManager(parkingLot);
        PaymentManager paymentManager = new PaymentManager(parkingLot, reservationManager);

        // Open the user windows
        ParkingWindow parkingWindow = new ParkingWindow(parkingLot, reservationManager, paymentManager);
        parkingWindow.setVisible(true);
        ServiceWindow serviceWindow = new ServiceWindow(parkingLot, reservationManager);
        serviceWindow.setVisible(true);

        // Open the management window
        ManagementWindow managementWindow = new ManagementWindow(parkingLot, reservationManager, paymentManager);
        managementWindow.setVisible(true);
    }
}