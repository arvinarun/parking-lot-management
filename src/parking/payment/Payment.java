package parking.payment;

import parking.core.Vehicle;
import java.time.LocalDateTime;

public class Payment {

    private static int nextPaymentId = 1;

    private String paymentId;
    private Vehicle vehicle;
    private double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentTime;

    public Payment(Vehicle vehicle, double amount, PaymentMethod paymentMethod) {

        this.paymentId = "P" + nextPaymentId++;
        this.vehicle = vehicle;
        this.amount = amount;
        this.paymentMethod = paymentMethod;

        this.paymentStatus = PaymentStatus.PENDING;
        this.paymentTime = LocalDateTime.now();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void markAsPaid() {
        paymentStatus = PaymentStatus.PAID;
    }

    public boolean isPaid() {
        return paymentStatus == PaymentStatus.PAID;
    }

    @Override
    public String toString() {

        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", vehicle=" + vehicle.getVehicleNumber() +
                ", amount=" + amount +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", paymentTime=" + paymentTime +
                '}';
    }
}