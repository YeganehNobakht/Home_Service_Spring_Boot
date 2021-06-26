package ir.maktab.homeservices.data.repository.User;

import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.data.entity.Specialist;
import ir.maktab.homeservices.data.entity.User;
import ir.maktab.homeservices.dto.UserFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
//    List<UserDto> filterUser(UserDto userDto);

    static Specification<User> filterUsers(UserFilter user, ServiceCategory serviceCategory) {
        return (Specification<User>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
            Subquery<Specialist> specialistSubquery = userCriteriaQuery.subquery(Specialist.class);
            Root<Specialist> specialistRoot = specialistSubquery.from(Specialist.class);

//            if (user.getUserRole().equals(UserRole.Specialist)){
//                userCriteriaQuery = criteriaBuilder.createQuery(Specialist.class);
//            }

            if (user.getUserRole()!=null) {
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
                specialistSubquery.select(specialistRoot.get("id")).where(criteriaBuilder.isMember(serviceCategory,specialistRoot.get("serviceCategoryList")));
                predicates.add(root.get("id").in(specialistSubquery));
            }

            return userCriteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}
