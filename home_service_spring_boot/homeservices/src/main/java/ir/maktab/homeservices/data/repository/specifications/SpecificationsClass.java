package ir.maktab.homeservices.data.repository.specifications;

import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.dto.SpecialistDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SpecificationsClass {

    public static Specification<ServiceCategory> filterProducts(SpecialistDto dto) {
        return (root, query, criteriaBuilder) -> {
            CriteriaQuery<ServiceCategory> criteriaQuery = criteriaBuilder.createQuery(ServiceCategory.class);
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getServiceCategoryList().size()!=0) {
                predicates.add(criteriaBuilder.not(root.get("name").in(dto.getServiceCategoryList())));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}
