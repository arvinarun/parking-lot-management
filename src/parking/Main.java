package parking;

import parking.core.ParkingLot;
import parking.reservation.ReservationManager;
import parking.payment.PaymentManager;
import parking.gui.UserWindow;
import parking.gui.ManagementWindow;

public class Main {

    // Entry point of the program
    public static void main(String[] args) {

        // Create the shared objects used by both windows
        ParkingLot parkingLot = new ParkingLot();
        ReservationManager reservationManager = new ReservationManager(parkingLot);
        PaymentManager paymentManager = new PaymentManager(parkingLot, reservationManager);

        // Open the user window
        UserWindow userWindow = new UserWindow(parkingLot, reservationManager, paymentManager);
        userWindow.setVisible(true);

        // Open the management window
        ManagementWindow managementWindow = new ManagementWindow(parkingLot, reservationManager, paymentManager);
        managementWindow.setVisible(true);
    }
}