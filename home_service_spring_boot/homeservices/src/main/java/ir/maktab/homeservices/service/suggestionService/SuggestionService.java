package ir.maktab.homeservices.service.suggestionService;


import ir.maktab.homeservices.data.entity.enums.SuggestionStatus;
import ir.maktab.homeservices.dto.CustomerOrderDto;
import ir.maktab.homeservices.dto.OrderDto;
import ir.maktab.homeservices.dto.SpecialistDto;
import ir.maktab.homeservices.dto.SuggestionDto;
import ir.maktab.homeservices.exceptions.checkes.*;

import java.util.List;
import java.util.Set;

public interface SuggestionService {

    void addSuggestionForOrder(SuggestionDto suggestionDto, CustomerOrderDto customerOrderDto) throws TooLowSubServicePriceException, OrderNotFoundException;

    public List<SuggestionDto> filterSuggestion(SuggestionDto suggestionDto);

    List<SuggestionDto> findByCustomerOrder(CustomerOrderDto customerOrderDto) throws OrderNoSuggestionException, OrderException;

    List<SuggestionDto> findBySpecialist(SpecialistDto specialist) ;

    SuggestionDto findBySpecialist_IdAndOrderId(Integer specialistId, Integer orderId) ;

    SuggestionDto findById(Integer id) throws SuggestionNotFoundException;

    SuggestionDto update(SuggestionDto suggestionDto) ;

    void updateStatus(Integer suggestionId,SuggestionStatus status);

    List<SuggestionDto> findUserBySuggestionStatusAndSpecialist(SuggestionStatus status, Integer id);

    void selectASuggestion(Integer id,CustomerOrderDto orderDto) throws OrderException, OrderNoSuggestionException, OrderNotFoundException, SuggestionNotFoundException;

    Set<CustomerOrderDto> showOrderWaitForOffer(SpecialistDto specialistDto);
}
