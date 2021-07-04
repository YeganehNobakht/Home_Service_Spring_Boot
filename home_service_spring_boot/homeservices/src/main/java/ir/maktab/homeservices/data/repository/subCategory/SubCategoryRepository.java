package ir.maktab.homeservices.data.repository.subCategory;

import ir.maktab.homeservices.data.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    SubCategory findByName(String number);

    @Query("select s from SubCategory as s where s.serviceCategory.name=:name")
    List<SubCategory> findByServiceCategoryName(@Param("name") String name);
}
