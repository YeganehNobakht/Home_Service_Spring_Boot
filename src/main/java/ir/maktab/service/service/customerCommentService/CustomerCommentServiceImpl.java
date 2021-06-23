package ir.maktab.service.service.customerCommentService;

import ir.maktab.service.data.repository.customerComment.CustomerCommentRepository;
import ir.maktab.service.dto.CustomerCommentDto;
import ir.maktab.service.service.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommentServiceImpl implements CustomerCommentService {
    private final CustomerCommentRepository customerCommentRepository;
    private final Mapper mapper;

    public CustomerCommentServiceImpl(CustomerCommentRepository customerCommentRepository, Mapper mapper) {
        this.customerCommentRepository = customerCommentRepository;
        this.mapper = mapper;
    }

    @Override
    public CustomerCommentDto save(CustomerCommentDto customerCommentDto) {
        return mapper.toCustomerCommentDto(customerCommentRepository.save(mapper.toCustomerComment(customerCommentDto)));
    }
}
