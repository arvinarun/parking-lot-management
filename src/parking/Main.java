package parking;

import parking.core.ParkingLot;
import parking.gui.MainWindow;
import parking.payment.PaymentManager;
import parking.reservation.ReservationManager;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // 1. Instantiate core parking lot
        ParkingLot parkingLot = new ParkingLot();

        // 2. Instantiate ReservationManager (requires parkingLot)
        ReservationManager reservationManager = new ReservationManager(parkingLot);

        // 3. Instantiate PaymentManager (requires parkingLot AND reservationManager)
        PaymentManager paymentManager = new PaymentManager(parkingLot, reservationManager);

        // 4. Launch GUI on the Swing Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow(parkingLot, reservationManager, paymentManager);
            mainWindow.setVisible(true);
        });
    }
}