package ir.maktab.homeservices.data.entity;


import ir.maktab.homeservices.data.entity.enums.PaymentStatus;

import javax.persistence.*;

/**
 * @author Yeganeh Nobakht
 **/
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cardNumber;
    private double price;
    @Column(length = 32, columnDefinition = "varchar(32) default 'CANCEL'")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.CANCEL;


    public Integer getId() {
        return id;
    }

    public Payment setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Payment setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Payment setPrice(double price) {
        this.price = price;
        return this;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Payment setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }
}
