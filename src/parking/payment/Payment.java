package parking.payment;
import parking.core.Vehicle;
import java.time.LocalDateTime;

public class Payment {
    private final String paymentId;
    private final Vehicle vehicle;
    private final double amount;
    private final PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private final LocalDateTime paymentTime;

    public Payment (String paymentId, Vehicle vehicle, double amount,PaymentMethod paymentMethod)
{
    if (paymentId == null) {
        throw new IllegalArgumentException ("Payment Id cannot be null.");
    }

    if (vehicle == null) {
        throw new IllegalArgumentException("Vehicle cannot be null.");

    }

    if (amount <0) {
        throw new IllegalArgumentException("Payment amount cannot be negative.");

    }

    if (paymentMethod == null) {
        throw new IllegalArgumentException( "Payment Method cannot be null.");

    }

    this.paymentId= paymentId;
    this.vehicle= vehicle;
    this.amount= amount;
    this.paymentMethod= paymentMethod;
    this.paymentStatus= PaymentStatus.PENDING;
    this.paymentTime= LocalDateTime.now();
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
    this.paymentStatus =PaymentStatus.PAID;

}
public boolean isPaid() {
    return paymentStatus == PaymentStatus.PAID;

}
// override
public String toString() {
    return "Payment{" +
            "paymentId = '" + paymentId + '\ '' + Number() +
            ",amount =" + amount +
            ", paymentMethod=" + paymentMethod +
            ", paymentStatus=" + paymentStatus +
            ", paymentTime=" + paymentTime +
            '}';

    }

}
