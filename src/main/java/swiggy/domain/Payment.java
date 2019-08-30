package swiggy.domain;


import javax.persistence.*;

@Entity
@Table(name="payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="payment_identifier")
    private Integer payementIdentifier;

    @Column(name="payment_mode")
    private String paymentMode;

    @Column(name="payment_status")
    private String paymentStatus;

    @Column(name="payment_amount")
    private String paymentAmount;

    @Column(name="order_identifier")
    private Integer orderIdentifier;

    @Transient
    private String paymentId;

    @Transient
    private String orderId;

    public Integer getPayementIdentifier() {
        return payementIdentifier;
    }

    public void setPayementIdentifier(Integer payementIdentifier) {
        this.payementIdentifier = payementIdentifier;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Integer getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(Integer orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payementIdentifier=" + payementIdentifier +
                ", paymentMode='" + paymentMode + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentAmount='" + paymentAmount + '\'' +
                ", orderIdentifier=" + orderIdentifier +
                '}';
    }
}
