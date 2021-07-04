package ir.maktab.homeservices.service.paymentService;

import ir.maktab.homeservices.dto.PaymentDto;

public interface PaymentService {
    PaymentDto save(PaymentDto paymentDto);
    void update(PaymentDto paymentDto);
}
