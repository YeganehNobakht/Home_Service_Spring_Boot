package ir.maktab.homeservices.service.mapper;

import ir.maktab.homeservices.data.entity.Payment;
import ir.maktab.homeservices.dto.PaymentDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperImpl implements PaymentMapper {
    public Payment toPayment(PaymentDto paymentDto){
        return new Payment()
                .setCardNumber(paymentDto.getCardNumber())
                .setPrice(paymentDto.getPrice());
    }
    public PaymentDto toPaymentDto(Payment payment){
        return new PaymentDto()
                .setCardNumber(payment.getCardNumber())
                .setPrice(payment.getPrice());
    }
}
