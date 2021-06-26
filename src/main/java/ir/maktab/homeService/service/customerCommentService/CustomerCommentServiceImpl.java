package ir.maktab.homeService.service.customerCommentService;

import ir.maktab.homeService.service.mapper.Mapper;
import ir.maktab.homeService.data.repository.customerComment.CustomerCommentRepository;
import ir.maktab.homeService.dto.CustomerCommentDto;
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
