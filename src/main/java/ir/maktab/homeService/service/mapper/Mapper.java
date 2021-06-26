package ir.maktab.homeService.service.mapper;


import ir.maktab.homeService.data.entity.*;
import ir.maktab.homeService.dto.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {
    private final AddressMapper addressMapper;

    public Mapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Customer toCustomer(CustomerDto customerDto) {
        return new Customer()
                .setBalance(customerDto.getBalance())
                .setDate(customerDto.getDate())
                .setEmail(customerDto.getEmail())
                .setName(customerDto.getName())
                .setLastName(customerDto.getLastName())
                .setPassword(customerDto.getPassword())
                .setUsername(customerDto.getUsername())
                .setUserStatus(customerDto.getUserStatus())
                .setCustomerOrderList(customerDto.getCustomerOrderList().stream().map(this::toCustomerOrder).collect(Collectors.toList()))
              //  .setCustomerCommentList(customerDto.getCustomerCommentList().stream().map(this::toCustomerComment).collect(Collectors.toList()))
                .setId(customerDto.getId());
    }

    public CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto()
                .setBalance(customer.getBalance())
                .setDate(customer.getDate())
                .setEmail(customer.getEmail())
                .setName(customer.getName())
                .setLastName(customer.getLastName())
                .setPassword(customer.getPassword())
                .setUsername(customer.getUsername())
                .setUserStatus(customer.getUserStatus())
                //.setCustomerOrderList(customer.getCustomerOrderList().stream().map(this::toCustomerOrderDto).collect(Collectors.toList()))
                //.setCustomerCommentList(customer.getCustomerCommentList().stream().map(this::toCustomerCommentDto).collect(Collectors.toList()))
                .setId(customer.getId());

    }

    public CustomerOrder toCustomerOrder(CustomerOrderDto customerOrderDto) {
        CustomerOrder customerOrder = new CustomerOrder()
                .setAddress(addressMapper.toAddress(customerOrderDto.getAddressDto()))
                .setCustomer(toCustomer(customerOrderDto.getCustomerDto()))
                .setOrderDate(customerOrderDto.getOrderDate())
                .setOrderStatus(customerOrderDto.getOrderStatus())
                .setServiceCategory(toServiceCategory(customerOrderDto.getServiceCategory()))
                .setSubCategory(toSubCategory(customerOrderDto.getSubCategory()))
                .setId(customerOrderDto.getId())
                .setJobDescription(customerOrderDto.getJobDescription())
                .setWorkDate(customerOrderDto.getWorkDate())
                .setPrice(customerOrderDto.getPrice());
        if (customerOrderDto.getSpecialistDto()!=null)
            customerOrder.setSpecialist(toSpecialist(customerOrderDto.getSpecialistDto()));
        if (customerOrderDto.getCustomerCommentDto()!=null)
            customerOrder.setCustomerComment(toCustomerComment(customerOrderDto.getCustomerCommentDto()));
        return customerOrder;
    }

    public CustomerOrderDto toCustomerOrderDto(CustomerOrder customerOrder) {
        CustomerOrderDto customerOrderDto = new CustomerOrderDto()
                .setAddressDto(addressMapper.toAddressDto(customerOrder.getAddress()))
                .setCustomerDto(toCustomerDto(customerOrder.getCustomer()))
                .setOrderDate(customerOrder.getOrderDate())
                .setOrderStatus(customerOrder.getOrderStatus())
                .setServiceCategory(toServiceCategoryDto(customerOrder.getServiceCategory()))
                .setSubCategory(toSubCategoryDto((customerOrder.getSubCategory())))
                .setId(customerOrder.getId())
                .setJobDescription(customerOrder.getJobDescription())
                .setWorkDate(customerOrder.getWorkDate())
                .setPrice(customerOrder.getPrice());
        if (customerOrder.getSpecialist()!=null)
             customerOrderDto.setSpecialistDto(toSpecialistDto(customerOrder.getSpecialist()));
        if (customerOrder.getCustomerComment()!=null)
            customerOrderDto.setCustomerCommentDto(toCustomerCommentDto(customerOrder.getCustomerComment()));
        return customerOrderDto;
    }

    public SubCategoryDto toSubCategoryDto(SubCategory subCategory) {
        return new SubCategoryDto()
                .setId(subCategory.getId())
                .setName(subCategory.getName())
                .setPrice(subCategory.getPrice())
               // .setCustomerOrderList(subCategory.getCustomerOrderList().stream().map(this::toCustomerOrderDto).collect(Collectors.toList()))
                .setServiceCategory(toServiceCategoryDto(subCategory.getServiceCategory()));
    }

    public SubCategory toSubCategory(SubCategoryDto subCategoryDto) {
        return new SubCategory()
                .setId(subCategoryDto.getId())
                .setName(subCategoryDto.getName())
                .setPrice(subCategoryDto.getPrice())
                //.setCustomerOrderList(subCategoryDto.getCustomerOrderList().stream().map(this::toCustomerOrder).collect(Collectors.toList()))
                .setServiceCategory(toServiceCategory(subCategoryDto.getServiceCategory()));
    }

    public ServiceCategory toServiceCategory(ServiceCategoryDto serviceCategoryDto) {
        return new ServiceCategory()
                .setId(serviceCategoryDto.getId())
                .setName(serviceCategoryDto.getName())
                .setSubCategoryList(serviceCategoryDto.getSubCategoryList().stream().map(this::toSubCategory).collect(Collectors.toList()))
                .setCustomerOrderList(serviceCategoryDto.getCustomerOrderList().stream().map(this::toCustomerOrder).collect(Collectors.toList()));
//                .setSpecialistList(serviceCategoryDto.getSpecialistList().stream().map(this::toSpecialist).collect(Collectors.toList()));
    }

    public ServiceCategoryDto toServiceCategoryDto(ServiceCategory serviceCategory) {
        return new ServiceCategoryDto()
                .setId(serviceCategory.getId())
                .setName(serviceCategory.getName());
//                .setSubCategoryList(serviceCategory.getSubCategoryList().stream().map(this::toSubCategoryDto).collect(Collectors.toList()))
//                .setCustomerOrderList(serviceCategory.getCustomerOrderList().stream().map(this::toCustomerOrderDto).collect(Collectors.toList()))
//                .setSpecialistList(serviceCategory.getSpecialistList().stream().map(this::toSpecialistDto).collect(Collectors.toList()));
    }

    public CustomerComment toCustomerComment(CustomerCommentDto customerCommentDto) {
        return new CustomerComment()
                .setComment(customerCommentDto.getComment())
                .setCustomer(toCustomer(customerCommentDto.getCustomerDto()))
                .setId(customerCommentDto.getId())
                .setScore(Double.parseDouble(customerCommentDto.getScore()))
                .setSpecialist(toSpecialist(customerCommentDto.getSpecialistDto()));
    }

    public CustomerCommentDto toCustomerCommentDto(CustomerComment customerComment) {
        return new CustomerCommentDto()
                .setComment(customerComment.getComment())
                .setCustomerDto(toCustomerDto(customerComment.getCustomer()))
                .setId(customerComment.getId())
                .setScore(customerComment.getScore().toString())
                .setSpecialistDto(toSpecialistDto((customerComment.getSpecialist())));
    }

    public Specialist toSpecialist(SpecialistDto specialistDto) {
        return new Specialist().setProfilePicture(specialistDto.getProfilePicture())
                .setCommentCounter(specialistDto.getCommentCounter())
                .setRate(specialistDto.getRate())
                .setId(specialistDto.getId())
                .setBalance(specialistDto.getBalance())
                .setDate(specialistDto.getDate())
                .setEmail(specialistDto.getEmail())
                .setName(specialistDto.getName())
                .setLastName(specialistDto.getLastName())
                .setPassword(specialistDto.getPassword())
                .setUsername(specialistDto.getUsername())
                .setUserStatus(specialistDto.getUserStatus())
                .setServiceCategoryList(specialistDto.getServiceCategoryList().stream().map(this::toServiceCategory).collect(Collectors.toSet()));
//                .setCustomerCommentList(specialistDto.getCustomerCommentList().stream().map(this::toCustomerComment).collect(Collectors.toList()))
//                .setSuggestionList(specialistDto.getSuggestionList().stream().map(s -> toSuggestion(s)).collect(Collectors.toList()));

    }

    public SpecialistDto toSpecialistDto(Specialist specialist) {
        return new SpecialistDto().setProfilePicture(specialist.getProfilePicture())
                .setCommentCounter(specialist.getCommentCounter())
                .setId(specialist.getId())
                .setRate(specialist.getRate())
                .setBalance(specialist.getBalance())
                .setDate(specialist.getDate())
                .setEmail(specialist.getEmail())
                .setName(specialist.getName())
                .setLastName(specialist.getLastName())
                .setPassword(specialist.getPassword())
                .setUsername(specialist.getUsername())
                .setUserStatus(specialist.getUserStatus())
                .setServiceCategoryList(specialist.getServiceCategoryList().stream().map(this::toServiceCategoryDto).collect(Collectors.toList()));
//                .setCustomerCommentList(specialist.getCustomerCommentList().stream().map(this::toCustomerCommentDto).collect(Collectors.toList()))
//                .setSuggestionList(specialist.getSuggestionList().stream().map(s -> toSuggestionDto(s)).collect(Collectors.toList()));

    }


    public Suggestion toSuggestion(SuggestionDto suggestionDto) {
        return new Suggestion()
                .setSuggestionStatus(suggestionDto.getSuggestionStatus())
                .setCustomerOrder(toCustomerOrder(suggestionDto.getCustomerOrder()))
                .setDurationOfWork(suggestionDto.getDurationOfWork())
                .setId(suggestionDto.getId())
                .setPrice(suggestionDto.getPrice())
                .setSpecialist(toSpecialist(suggestionDto.getSpecialistDto()))
                .setStartTime(suggestionDto.getStartTime())
                .setWorkDescription(suggestionDto.getWorkDescription());
    }

    public SuggestionDto toSuggestionDto(Suggestion suggestion) {
        return new SuggestionDto()
                .setSuggestionStatus(suggestion.getSuggestionStatus())
                .setCustomerOrder(toCustomerOrderDto(suggestion.getCustomerOrder()))
                .setDurationOfWork(suggestion.getDurationOfWork())
                .setId(suggestion.getId())
                .setPrice(suggestion.getPrice())
                .setSpecialistDto(toSpecialistDto(suggestion.getSpecialist()))
                .setStartTime(suggestion.getStartTime())
                .setWorkDescription(suggestion.getWorkDescription());
    }
}
