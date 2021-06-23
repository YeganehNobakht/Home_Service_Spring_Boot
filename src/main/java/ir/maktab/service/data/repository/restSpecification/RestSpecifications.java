package ir.maktab.service.data.repository.restSpecification;


import ir.maktab.service.data.entity.*;
import ir.maktab.service.dto.enums.UserRole;
import ir.maktab.service.dto.restDto.OrderFilterDto;
import ir.maktab.service.dto.restDto.UserOrderDtoFilter;
import ir.maktab.service.dto.restDto.UserReportDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public interface RestSpecifications {

//    static Specification<Suggestion> filterSuggestion(SuggestionFilterDto dto, UserRole userRole){
//        return (Specification<Suggestion>) (root, query, criteriaBuilder) -> {
//            CriteriaQuery<Suggestion> suggestionQuery = criteriaBuilder.createQuery(Suggestion.class);
//            List<Predicate> predicateList = new ArrayList<>();
//            Join<Suggestion, Specialist> specialistJoin = root.join("specialist");
//            Join<Suggestion, CustomerOrder> customerOrderJoin = root.join("customerOrder");
//            Join<CustomerOrder, Customer> customerJoin = customerOrderJoin.join("customer");
//
//            predicateList.add(criteriaBuilder.equal(root.get("suggestionStatus"), SuggestionStatus.ACCEPTED));
//
//            if (userRole.equals(UserRole.Specialist)){
//                predicateList.add(criteriaBuilder.equal(specialistJoin.get("id"),dto.getId()));
//            }
//            if (userRole.equals(UserRole.Customer)){
//                predicateList.add(criteriaBuilder.equal(customerJoin.get("id"),dto.getId()));
//            }
//           if (dto.getSuggestionStatus()!=null)
//                predicateList.add(criteriaBuilder.equal(root.get("suggestionStatus"),dto.getSuggestionStatus()));
//            if (dto.getMaxSuggestionPrice()!=null)
//                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"),dto.getMaxSuggestionPrice()));
//            if (dto.getMinSuggestionPrice()!=null)
//                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"),dto.getMinSuggestionPrice()));
//            if (dto.getStartDate()!=null)
//                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"),dto.getStartDate()));
//            if (dto.getEndDate()!=null)
//                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("startTime"),dto.getEndDate()));
//
//            return suggestionQuery.where(predicateList.toArray(new Predicate[0])).getRestriction();
//        };
//    }

    static Specification<CustomerOrder> filterAUserOrders(UserOrderDtoFilter dto, UserRole userRole) {
        return (Specification<CustomerOrder>) (root, query, criteriaBuilder) -> {
            CriteriaQuery<CustomerOrder> criteriaQuery = criteriaBuilder.createQuery(CustomerOrder.class);
            List<Predicate> predicateList = new ArrayList<>();
            Join<CustomerOrder, Specialist> specialistJoin = root.join("specialist");
            Join<CustomerOrder, Customer> customerJoin = root.join("customer");

            if (dto.getStartDate() != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("workDate"), dto.getStartDate()));
            }
            if (dto.getEndDate() != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("workDate"), dto.getEndDate()));
            }
            if (dto.getMinPrice() != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), dto.getMinPrice()));
            }
            if (dto.getMaxPrice() != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), dto.getMaxPrice()));
            }

            if (userRole.equals(UserRole.Specialist)) {
                predicateList.add(criteriaBuilder.equal(specialistJoin.get("id"), dto.getUserId()));
            }
            if (userRole.equals(UserRole.Customer)) {
                predicateList.add(criteriaBuilder.equal(customerJoin.get("id"), dto.getUserId()));
            }
            return criteriaQuery.where(predicateList.toArray(new Predicate[0])).getRestriction();

        };
    }

    @Deprecated
    static Specification<CustomerOrder> filterOrders(OrderFilterDto dto) {
        return (Specification<CustomerOrder>) (root, query, criteriaBuilder) -> {
            CriteriaQuery<CustomerOrder> criteriaQuery = criteriaBuilder.createQuery(CustomerOrder.class);
            List<Predicate> predicateList = new ArrayList<>();
            Join<CustomerOrder, SubCategory> subCategoryJoin = root.join("subCategory");
            Join<CustomerOrder, ServiceCategory> serviceCategoryJoin = root.join("serviceCategory");

            if (dto.getOrderStatus() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("orderStatus"), dto.getOrderStatus()));
            }
            if (!StringUtils.isEmpty(dto.getSubServiceName())) {
                predicateList.add(criteriaBuilder.equal(subCategoryJoin.get("name"), dto.getSubServiceName()));
            }
            if (!StringUtils.isEmpty(dto.getServiceName())) {
                predicateList.add(criteriaBuilder.equal(serviceCategoryJoin.get("name"), dto.getServiceName()));
            }
            if (dto.getStartDate() != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate"), dto.getStartDate()));
            }
            if (dto.getEndDate() != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("orderDate"), dto.getEndDate()));
            }
            return criteriaQuery.where(predicateList.toArray(new Predicate[0])).getRestriction();

        };
    }

    static Specification<User> userReportFilter(UserReportDto dto) {
        return (root, query, criteriaBuilder) -> {
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            List<Predicate> predicates = new ArrayList<>();

            Subquery<Customer> customerSubquery = criteriaQuery.subquery(Customer.class);
            Root<Customer> customerRoot = customerSubquery.from(Customer.class);
            Subquery<Customer> customerSelect = customerSubquery.select(customerRoot.get("id"));
            Predicate customerPredicate = criteriaBuilder.isTrue(criteriaBuilder.literal(true));


            Subquery<Specialist> specialistSubquery = criteriaQuery.subquery(Specialist.class);
            Root<Specialist> specialistRoot = specialistSubquery.from(Specialist.class);
            Subquery<Specialist> expertSelect = specialistSubquery.select(specialistRoot.get("id"));
            Predicate expertPredicate = criteriaBuilder.isTrue(criteriaBuilder.literal(true));


            if (dto.getMaxNumOrders() != null) {
                customerPredicate = criteriaBuilder.and(customerPredicate, criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.size(customerRoot.get("customerOrderList")), dto.getMaxNumOrders()));
            }
            if (dto.getMinNumOrders() != null) {
                customerPredicate = criteriaBuilder.and(customerPredicate, criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.size(customerRoot.get("customerOrderList")), dto.getMinNumOrders()));
            }

            if (dto.getMaxNumSuggestion() != null) {
                expertPredicate = criteriaBuilder.and(expertPredicate, criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.size(specialistRoot.get("suggestionList")), dto.getMaxNumSuggestion()));
            }
            if (dto.getMinNumSuggestion() != null) {
                expertPredicate = criteriaBuilder.and(expertPredicate, criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.size(specialistRoot.get("suggestionList")), dto.getMinNumSuggestion()));
            }
            if (dto.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), dto.getStartDate()));
            }
            if (dto.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), dto.getEndDate()));
            }
            if (customerPredicate.getExpressions().size() > 0) {
                customerSelect.where(customerPredicate);
                predicates.add(root.get("id").in(customerSubquery));
            }
            if (expertPredicate.getExpressions().size() > 0) {
                expertSelect.where(expertPredicate);
                predicates.add(root.get("id").in(specialistSubquery));
            }
            return criteriaQuery.select(root).where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}
