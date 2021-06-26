package ir.maktab.homeservices.data.repository.serviceCategory;

import ir.maktab.homeservices.data.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Integer>, JpaSpecificationExecutor<ServiceCategory> {
    //    void create(ServiceCategory serviceCategory);
//    void update(ServiceCategory serviceCategory);
//    Optional<ServiceCategory> get(Integer id);
//    Optional<ServiceCategory> getByName(String name);
//    List<ServiceCategory> getAll();
//    void delete(Integer integer);
    ServiceCategory findByName(String name);
}
