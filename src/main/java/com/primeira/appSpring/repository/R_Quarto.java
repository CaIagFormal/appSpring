package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface R_Quarto extends JpaRepository<M_Quarto, Long> {
    @Query(value = "SELECT * FROM QUARTO WHERE QUARTO.ID = :ID LIMIT 1",nativeQuery = true)
    M_Quarto getQuartoById(@Param("ID") Integer integer);

    @Query(value = "SELECT Q.* FROM QUARTO Q " +
            "LEFT JOIN LOCACAO L ON L.ID_QUARTO = Q.ID " +
            "WHERE L.ID_QUARTO IS NULL OR NOT (:DATE BETWEEN L.CHECKIN AND L.CHECKOUT) " +
            "ORDER BY Q.NUM",nativeQuery = true)
    List<M_Quarto> getAvailableQuarto(@Param("DATE") LocalDate date);
}
