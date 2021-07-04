package ir.maktab.homeservices.data.repository.suggestion;

import ir.maktab.homeservices.data.entity.Specialist;
import ir.maktab.homeservices.data.entity.Suggestion;
import ir.maktab.homeservices.data.entity.enums.SuggestionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Integer>, JpaSpecificationExecutor<Suggestion> {


    List<Suggestion> findBySpecialist(Specialist specialist);

    Suggestion findBySpecialist_IdAndAndCustomerOrder_Id(Integer specialistId, Integer orderId);

    List<Suggestion> findByCustomerOrder_Id(Integer order);

    @Query("SELECT s FROM Suggestion s WHERE s.suggestionStatus = :status and s.specialist.id = :specialistId")
    List<Suggestion> findUserBySuggestionStatusAndSpecialist(@Param("status") SuggestionStatus status, @Param("specialistId") Integer id);

    @Modifying
    @Query("update Suggestion  s set s.suggestionStatus=:status where s.id=:suggestionId")
    void updateStatus(@Param("suggestionId") Integer suggestionId, @Param("status") SuggestionStatus status);

}
