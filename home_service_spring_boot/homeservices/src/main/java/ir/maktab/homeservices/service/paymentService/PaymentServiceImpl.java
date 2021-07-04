package ir.maktab.homeservices.service.paymentService;

import ir.maktab.homeservices.data.entity.Payment;
import ir.maktab.homeservices.data.entity.enums.PaymentStatus;
import ir.maktab.homeservices.data.repository.PaymentRepository;
import ir.maktab.homeservices.dto.PaymentDto;
import ir.maktab.homeservices.service.mapper.PaymentMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentDto save(PaymentDto paymentDto) {
        Payment payment = paymentMapper.toPayment(paymentDto);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        Payment save = paymentRepository.save(payment);
        return paymentMapper.toPaymentDto(save);
    }

    @Override
    public void update(PaymentDto paymentDto) {
        paymentRepository.save(paymentMapper.toPayment(paymentDto));
    }
}
