package ir.maktab.homeservices.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Yeganeh Nobakht
 **/
public class PaymentDto {
    @NotBlank(message = "card.number.blank")
    @Pattern(regexp = "[1-9][0-9]{15}",message = "card.number")
    private String cardNumber;
    @NotBlank(message = "cvv2.blank")
    @Pattern(regexp = "^[0-9]{3,4}$",message = "cvv2")
    private String ccv2;
    @NotBlank(message = "month.blank")
    @Size(min = 1, max = 12, message = "month.size")
    private String  cardExpirationMonth;
    @NotBlank(message = "year.blank")
    @Size(min = 0, max = 99, message = "year.size")
    private String  cardExpirationYear;
    @NotBlank(message = "card.pass.blank")
    @Pattern(regexp = "[0-9]{4}",message = "card.pass")
    private String  dynamicPassword;
    private String email;
    private String captcha;
    private double price;

    public String getCaptcha() {
        return captcha;
    }

    public PaymentDto setCaptcha(String captcha) {
        this.captcha = captcha;
        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public PaymentDto setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public String getCcv2() {
        return ccv2;
    }

    public PaymentDto setCcv2(String ccv2) {
        this.ccv2 = ccv2;
        return this;
    }

    public String getCardExpirationMonth() {
        return cardExpirationMonth;
    }

    public PaymentDto setCardExpirationMonth(String cardExpirationMonth) {
        this.cardExpirationMonth = cardExpirationMonth;
        return this;
    }

    public String getCardExpirationYear() {
        return cardExpirationYear;
    }

    public PaymentDto setCardExpirationYear(String cardExpirationYear) {
        this.cardExpirationYear = cardExpirationYear;
        return this;
    }

    public String  getDynamicPassword() {
        return dynamicPassword;
    }

    public PaymentDto setDynamicPassword(String dynamicPassword) {
        this.dynamicPassword = dynamicPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PaymentDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public PaymentDto setPrice(double price) {
        this.price = price;
        return this;
    }
}
