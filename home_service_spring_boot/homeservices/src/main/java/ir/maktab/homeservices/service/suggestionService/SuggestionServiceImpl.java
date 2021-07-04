package ir.maktab.homeservices.service.suggestionService;


import ir.maktab.homeservices.data.entity.Suggestion;
import ir.maktab.homeservices.data.entity.enums.OrderStatus;
import ir.maktab.homeservices.data.entity.enums.SuggestionStatus;
import ir.maktab.homeservices.data.repository.specification.Specifications;
import ir.maktab.homeservices.data.repository.suggestion.SuggestionRepository;
import ir.maktab.homeservices.dto.*;
import ir.maktab.homeservices.exceptions.checkes.*;
import ir.maktab.homeservices.service.customerOrderService.CustomerOrderService;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.mapper.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final Mapper mapper;
    private final CustomerOrderService customerOrderService;
    private final MaktabMessageSource maktabMessageSource;

    public SuggestionServiceImpl(SuggestionRepository suggestionRepository, Mapper mapper, CustomerOrderService customerOrderService, MaktabMessageSource maktabMessageSource) {
        this.suggestionRepository = suggestionRepository;
        this.mapper = mapper;
        this.customerOrderService = customerOrderService;
        this.maktabMessageSource = maktabMessageSource;
    }

    @Override
    public void addSuggestionForOrder(SuggestionDto suggestionDto, CustomerOrderDto orderDto) throws TooLowSubServicePriceException, OrderNotFoundException {
        SubCategoryDto subCategory = orderDto.getSubCategory();
        if (subCategory.getPrice() >= suggestionDto.getPrice())
            throw new TooLowSubServicePriceException(maktabMessageSource.getEnglish("subService.price.low", new Object[]{subCategory.getPrice()}));
        orderDto.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_CHOSE);
        customerOrderService.updateOrderStatus(orderDto);
        suggestionRepository.save(mapper.toSuggestion(suggestionDto));
    }

    @Override
    public List<SuggestionDto> filterSuggestion(SuggestionDto suggestionDto) {
        List<Suggestion> all = suggestionRepository.findAll(Specifications.filterSuggestion(mapper.toSuggestion(suggestionDto)));
        return all.stream().map(mapper::toSuggestionDto).collect(Collectors.toList());
    }

    @Override
    public List<SuggestionDto> findByCustomerOrder(CustomerOrderDto order) throws OrderNoSuggestionException, OrderException {
        if (order.getOrderStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_CHOSE)) {
            List<Suggestion> suggestions = suggestionRepository.findByCustomerOrder_Id(order.getId());
            if (suggestions.size() == 0)
                throw new OrderNoSuggestionException(maktabMessageSource.getEnglish("no.suggestion", new Object[]{order.getId()}));
            return suggestions.stream().map(mapper::toSuggestionDto).collect(Collectors.toList());

        }
        throw new OrderException(maktabMessageSource.getEnglish("has.order", new Object[]{order.getId()}));
    }

    @Override
    public List<SuggestionDto> findBySpecialist(SpecialistDto specialist) {

        List<Suggestion> bySpecialist = suggestionRepository.findBySpecialist(mapper.toSpecialist(specialist));
        return bySpecialist.stream().map(mapper::toSuggestionDto).collect(Collectors.toList());
    }

    @Override
    public SuggestionDto findBySpecialist_IdAndOrderId(Integer specialistId, Integer orderId) {
        Suggestion suggestion = suggestionRepository.findBySpecialist_IdAndAndCustomerOrder_Id(specialistId, orderId);
        if (suggestion == null)
            return null;
        return mapper.toSuggestionDto(suggestion);
    }

    @Override
    public SuggestionDto findById(Integer id) throws SuggestionNotFoundException {
        Optional<Suggestion> suggestion = suggestionRepository.findById(id);
        if (suggestion.isPresent())
            return mapper.toSuggestionDto(suggestion.get());
        throw new SuggestionNotFoundException(maktabMessageSource.getEnglish("suggestion.not.found", new Object[]{id}));
    }

    @Override
    public SuggestionDto update(SuggestionDto suggestionDto) {
//        if (suggestionRepository.findById(suggestionDto.getId()).isPresent()) {
        //using save method for update
        return mapper.toSuggestionDto(suggestionRepository.save(mapper.toSuggestion(suggestionDto)));
//        } else
//            throw new Exception("No such specialist found");
    }

    @Transactional
    @Override
    public void updateStatus(Integer suggestionId, SuggestionStatus status) {
        suggestionRepository.updateStatus(suggestionId, status);
    }

    @Override
    public List<SuggestionDto> findUserBySuggestionStatusAndSpecialist(SuggestionStatus status, Integer id) {
        List<Suggestion> suggestionList = suggestionRepository.findUserBySuggestionStatusAndSpecialist(status, id);
        return suggestionList.stream().map(mapper::toSuggestionDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void selectASuggestion(Integer id, CustomerOrderDto orderDto) throws OrderException, OrderNoSuggestionException, OrderNotFoundException, SuggestionNotFoundException {
        Optional<Suggestion> suggestion = suggestionRepository.findById(id);
        if (suggestion.isPresent()) {
            suggestion.get().setSuggestionStatus(SuggestionStatus.ACCEPTED);
            updateStatus(id, SuggestionStatus.ACCEPTED);
            orderDto.setAcceptedSuggestionDto(mapper.toSuggestionDto(suggestion.get())).setSpecialistDto(mapper.toSpecialistDto(suggestion.get().getSpecialist()));
            List<SuggestionDto> allSuggestion = findByCustomerOrder(orderDto);
            for (SuggestionDto s : allSuggestion) {
                if (!s.getId().equals(suggestion.get().getId())) {
                    updateStatus(s.getId(), SuggestionStatus.REJECTED);
                }
            }
            orderDto.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_COME).setPrice(suggestion.get().getPrice());
            customerOrderService.updateOrderStatusAndPriceAndSpecialist(orderDto);
        } else
            throw new SuggestionNotFoundException(maktabMessageSource.getEnglish("suggestion.not.found", new Object[]{id}));
    }

    @Override
    public Set<CustomerOrderDto> showOrderWaitForOffer(SpecialistDto specialistDto) {
        Set<CustomerOrderDto> orderDtoList = new HashSet<>();
        List<ServiceCategoryDto> serviceCategoryDtoList = specialistDto.getServiceCategoryList();
        for (ServiceCategoryDto s : serviceCategoryDtoList) {
            List<CustomerOrderDto> order = customerOrderService.findByServiceAndStatus(s, OrderStatus.WAITING_FOR_SPECIALIST_OFFER);
            List<CustomerOrderDto> order1 = customerOrderService.findByServiceAndStatus(s, OrderStatus.WAITING_FOR_SPECIALIST_CHOSE);
            order.addAll(order1);
            for (CustomerOrderDto o : order) {
                if (findBySpecialist_IdAndOrderId(specialistDto.getId(), o.getId()) == null)
                    orderDtoList.add(o);
            }
        }
        return orderDtoList;
    }
}
