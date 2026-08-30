package parking.gui;

import parking.core.ParkingLot;
import parking.payment.PaymentManager;
import parking.reservation.ReservationManager;

import javax.swing.*;
import java.awt.*;

public class ParkingWindow extends JFrame {

    private static final Color BG_DARK = new Color(27, 20, 18);
    private static final Color CARD_BG = new Color(43, 32, 28);
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);

    public ParkingWindow(ParkingLot parkingLot, ReservationManager reservationManager, PaymentManager paymentManager) {

        setTitle("Parking Operations");
        setSize(700, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Enforce dark background color defaults for JTabbedPane
        UIManager.put("TabbedPane.background", CARD_BG);
        UIManager.put("TabbedPane.darkShadow", BG_DARK);
        UIManager.put("TabbedPane.shadow", BG_DARK);
        UIManager.put("TabbedPane.light", CARD_BG);
        UIManager.put("TabbedPane.highlight", CARD_BG);
        UIManager.put("TabbedPane.focus", ACCENT_GREEN);
        UIManager.put("TabbedPane.contentAreaColor", BG_DARK);
        UIManager.put("TabbedPane.unselectedBackground", CARD_BG);
        UIManager.put("TabbedPane.selected", ACCENT_GREEN);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tabbedPane.setBackground(CARD_BG);
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setOpaque(true);

        // Set container panel background
        getContentPane().setBackground(BG_DARK);

        tabbedPane.addTab(" Entry ", new EntryPanel(parkingLot, reservationManager));
        tabbedPane.addTab(" Payment ", new PaymentPanel(paymentManager));
        tabbedPane.addTab(" Exit ", new ExitPanel(parkingLot, paymentManager));

        add(tabbedPane, BorderLayout.CENTER);
    }
}