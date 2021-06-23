package ir.maktab.service.data.repository.subCategory;

import ir.maktab.service.data.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
//    void create(SubCategory subCategory);
//    void update(SubCategory subCategory);
//    Optional<SubCategory> get(Integer id);
//    Optional<SubCategory> getByName(String name);
//    List<SubCategory> getAll();
//    void delete(Integer integer);

    SubCategory findByName(String number);

    @Query("select s from SubCategory as s where s.serviceCategory.name=:name")
    List<SubCategory> findByServiceCategoryName(@Param("name") String name);
}
