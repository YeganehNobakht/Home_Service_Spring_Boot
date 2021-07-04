package ir.maktab.homeservices.data.repository.specification;

import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.data.entity.Specialist;
import ir.maktab.homeservices.data.entity.Suggestion;
import ir.maktab.homeservices.data.entity.User;
import ir.maktab.homeservices.dto.UserFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class Specifications {
    public static Specification<User> filterUsers(UserFilter user, ServiceCategory serviceCategory) {
        return (Specification<User>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
            Subquery<Specialist> specialistSubquery = userCriteriaQuery.subquery(Specialist.class);
            Root<Specialist> specialistRoot = specialistSubquery.from(Specialist.class);

            if (user.getUserRole() != null) {
                predicates.add(criteriaBuilder.equal(root.get("userRole"), user.getUserRole()));
            }

            if (!StringUtils.isEmpty(user.getName())) {
                predicates.add(criteriaBuilder.equal(root.get("name"), user.getName()));
            }

            if (!StringUtils.isEmpty(user.getLastName())) {
                predicates.add(criteriaBuilder.equal(root.get("lastName"), user.getLastName()));
            }

            if (!StringUtils.isEmpty(user.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.get("email"), user.getEmail()));
            }

            if (serviceCategory != null) {
                //if you have several conditions then you should create a new predicate and add the conditions to it like above then add this new predicate to where clause.
                specialistSubquery.select(specialistRoot.get("id")).where(criteriaBuilder.isMember(serviceCategory, specialistRoot.get("serviceCategoryList")));
                predicates.add(root.get("id").in(specialistSubquery));
            }

            return userCriteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }

    public static Specification<Suggestion> filterSuggestion(Suggestion suggestion) {
        return (Specification<Suggestion>) (root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            if (suggestion.getCustomerOrder() != null) {
                predicates.add(cb.equal(root.get("customerOrder"), suggestion.getCustomerOrder()));
            }
            if (suggestion.getPrice() != null) {
                predicates.add(cb.le(root.get("price"), suggestion.getPrice()));
            }
            Join<Suggestion, Specialist> specialist = root.join("specialist");

            if (suggestion.getSpecialist().getRate() != 0) {
                predicates.add(cb.ge(specialist.get("rate"), suggestion.getSpecialist().getRate()));
            }

            cq.orderBy(cb.desc(root.get("price")), cb.desc(specialist.get("rate")));

            return cq.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}
