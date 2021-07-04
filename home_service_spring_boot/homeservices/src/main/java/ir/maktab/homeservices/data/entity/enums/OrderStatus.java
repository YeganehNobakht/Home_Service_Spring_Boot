package ir.maktab.homeservices.data.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.criterion.Order;

public enum OrderStatus {
    WAITING_FOR_SPECIALIST_OFFER,
    WAITING_FOR_SPECIALIST_CHOSE,
    WAITING_FOR_SPECIALIST_COME,
    STARTED,
    FINISHED_WORK,
    WAIT_FOR_PAY,
    PAID;

    @JsonCreator
    public static OrderStatus forName(String name) {
        for(OrderStatus c: values()) {
            if(c.name().equals(name)) {
                return c;
            }
        }

        return null;
    }
}
