package ir.maktab.homeservices.configuration.discountProperty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:discount.properties")
public final class RetunedMoney {
    @Value("${discount}")
    private double discount;

    public double getDiscount() {
        return discount;
    }
}
