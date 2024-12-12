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

    @Query(value = "SELECT * FROM QUARTO Q " +
            "WHERE Q.ID NOT IN (" +
            "SELECT Q.ID FROM QUARTO Q JOIN LOCACAO L ON L.ID_QUARTO=Q.ID " +
            "WHERE (L.CHECKIN < :DATACHECKOUT AND L.CHECKOUT > :DATACHECKIN))",nativeQuery = true)
    List<M_Quarto> getAvailableQuarto(@Param("DATACHECKIN") LocalDate datacheckin,@Param("DATACHECKOUT") LocalDate datacheckout);

    @Query(value = "SELECT * FROM QUARTO Q " +
            "WHERE Q.ID NOT IN (" +
            "SELECT Q.ID FROM QUARTO Q JOIN LOCACAO L ON L.ID_QUARTO=Q.ID " +
            "WHERE (L.CHECKIN < :DATACHECKOUT AND L.CHECKOUT > :DATACHECKIN))" +
            "AND Q.ID = :QUARTO LIMIT 1",nativeQuery = true)
    M_Quarto isQuartoAvailable(@Param("DATACHECKIN") LocalDate datacheckin,@Param("DATACHECKOUT") LocalDate datacheckout,@Param("QUARTO") Long id);
}
