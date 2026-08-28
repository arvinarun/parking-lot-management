package parking.payment;
import parking.core.Vehicle;
import java.time.LocalDateTime;

public class Payment {
    private final String PaymentId;
    private final Vehicle vehicle;
    private final double Amount;
    private final PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus
    private final LocalDateTime paymentTime;

    public Payment (String PaymentId, Vehicle vehicle, double Amount,PaymentMethod paymentMethod)
{
    if (PaymentId == null) {
        throw new IllegalArgumentException ("Payment Id cannot be null.");
    }

    if (vehicle == null) {
        throw new IllegalArgumentException("Vehicle cannot be null.");

    }

    if (Amount <0) {
        throw new IllegalArgumentException("Payment amount cannot be negative.");

    }

    if (paymentMethod == null) {
        throw new IllegalArgumentException( "Payment Method cannot be null.");

    }

    this.PaymentId= PaymentId;
    this.vehicle= vehicle;
    this.Amount= Amount;
    this.paymentMethod= paymentMethod;
    this.paymentStatus= PaymentStatus.PENDING;
    this.paymentTime= LocalDateTime.now();
}
     
public String getPaymentId() {
    return PaymentID;

}
public Vehicle getVehicle() {
    return vehicle;

}
public double getAmount() {
    return Amount;

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
            "PaymentId = '" + PaymentId + '\ '' +
            ", vehicle=" + vehicle.getRegistrationNumber() +
            ",Amount =" + Amount +
            ", paymentMethod=" + paymentMethod +
            ", paymentStatus=" + paymentStatus +
            ", paymentTime=" + paymentTime +
            '}';

    }

}
    