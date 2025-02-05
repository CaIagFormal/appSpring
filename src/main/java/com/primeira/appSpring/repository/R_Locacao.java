package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_ViewLocacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface R_Locacao extends JpaRepository<M_Locacao, Long> {
    @Query(value = "SELECT * FROM LOCACAO WHERE SENHA = :SENHA AND NOW() BETWEEN CHECKIN AND CHECKOUT LIMIT 1",nativeQuery = true)
    M_Locacao getLocacaoBySenha(@Param("SENHA") String senha);
    @Query(value = "SELECT * FROM LOCACAO WHERE ID_USUARIO = :USUARIO",nativeQuery = true)
    List<M_Locacao> getLocacaoByUsuario(@Param("USUARIO") Long usuario);

    @Query(value = "WITH CALC AS (SELECT (L.CHECKOUT-L.CHECKIN) AS DIARIAS, " +
            "SUM(C.PRECO) AS CON FROM LOCACAO L " +
            "LEFT JOIN CONSUMO C ON C.ID_LOCACAO = L.ID " +
            "WHERE L.ID_USUARIO = :USUARIO AND NOW() >= L.CHECKOUT " +
            "GROUP BY L.ID) " +
            "SELECT L.ID,Q.NUM,L.PRECO,L.SENHA,L.CHECKIN,L.CHECKOUT, " +
            "CASE (CALC.DIARIAS) " +
            "WHEN 0 THEN 1 ELSE (CALC.DIARIAS) END AS DIARIAS, " +
            "CASE WHEN (CALC.CON) IS NULL THEN 0 ELSE (CALC.CON) END AS CONSUMOS " +
            "FROM CALC " +
            "JOIN LOCACAO L ON L.ID_USUARIO = :USUARIO " +
            "JOIN QUARTO Q ON Q.ID=L.ID_QUARTO " +
            "WHERE NOW() >= L.CHECKOUT " +
            "ORDER BY L.CHECKIN DESC,L.CHECKOUT DESC",nativeQuery = true)
    List<M_ViewLocacao> getLocacaoCompleta(@Param("USUARIO") Long usuario);

    @Query(value = "WITH CALC AS (SELECT (L.CHECKOUT-L.CHECKIN) AS DIARIAS " +
            "FROM LOCACAO L " +
            "LEFT JOIN CONSUMO C ON C.ID_LOCACAO = L.ID " +
            "WHERE L.ID_USUARIO = :USUARIO AND NOW() < L.CHECKIN) " +
            "SELECT L.ID,Q.NUM,L.PRECO,L.SENHA,L.CHECKIN,L.CHECKOUT, " +
            "CASE (CALC.DIARIAS) " +
            "WHEN 0 THEN 1 ELSE (CALC.DIARIAS) END AS DIARIAS, " +
            "0 AS CONSUMOS " +
            "FROM CALC " +
            "JOIN LOCACAO L ON L.ID_USUARIO = :USUARIO " +
            "JOIN QUARTO Q ON Q.ID=L.ID_QUARTO " +
            "WHERE NOW() < L.CHECKIN " +
            "ORDER BY L.CHECKIN DESC,L.CHECKOUT DESC",nativeQuery = true)
    List<M_ViewLocacao> getLocacaoEmReserva(@Param("USUARIO") Long usuario);


    @Query(value = "WITH CALC AS (SELECT (L.CHECKOUT-L.CHECKIN) AS DIARIAS, " +
            "SUM(C.PRECO) AS CON FROM LOCACAO L " +
            "LEFT JOIN CONSUMO C ON C.ID_LOCACAO = L.ID " +
            "WHERE L.ID_USUARIO = :USUARIO AND NOW() BETWEEN L.CHECKIN AND L.CHECKOUT " +
            "GROUP BY L.ID) " +
            "SELECT L.ID,Q.NUM,L.PRECO,L.SENHA,L.CHECKIN,L.CHECKOUT, " +
            "CASE (CALC.DIARIAS) " +
            "WHEN 0 THEN 1 ELSE (CALC.DIARIAS) END AS DIARIAS, " +
            "CASE WHEN (CALC.CON) IS NULL THEN 0 ELSE (CALC.CON) END AS CONSUMOS " +
            "FROM CALC " +
            "JOIN LOCACAO L ON L.ID_USUARIO = :USUARIO " +
            "JOIN QUARTO Q ON Q.ID=L.ID_QUARTO " +
            "WHERE NOW() BETWEEN L.CHECKIN AND L.CHECKOUT " +
            "ORDER BY L.CHECKIN DESC,L.CHECKOUT DESC",nativeQuery = true)
    List<M_ViewLocacao> getLocacaoEmCurso(@Param("USUARIO") Long usuario);
    @Query(value = "SELECT * FROM LOCACAO WHERE ID = :ID AND ID_USUARIO = :USUARIO LIMIT 1",nativeQuery = true)
    M_Locacao getLocacaoByIdAndUser(@Param("ID") Long id,@Param("USUARIO") Long usuario);
}
