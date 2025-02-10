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
    @Query(value = "SELECT * FROM LOCACAO WHERE SENHA = :SENHA AND (NOW() BETWEEN CHECKIN AND CHECKOUT) AND NO_SHOW=FALSE LIMIT 1",nativeQuery = true)
    M_Locacao getLocacaoBySenha(@Param("SENHA") String senha);
    @Query(value = "SELECT * FROM LOCACAO WHERE ID_USUARIO = :USUARIO",nativeQuery = true)
    List<M_Locacao> getLocacaoByUsuario(@Param("USUARIO") Long usuario);

    @Query(value = "WITH CALC AS (SELECT (L.CHECKOUT-L.CHECKIN) AS DIARIAS, " +
            "SUM(C.PRECO) AS CON FROM LOCACAO L " +
            "LEFT JOIN CONSUMO C ON C.ID_LOCACAO = L.ID AND C.ID != 8 " +
            "WHERE L.ID_USUARIO = :USUARIO AND NOW() >= L.CHECKOUT OR L.NO_SHOW = TRUE " +
            "GROUP BY L.ID) " +
            "SELECT L.ID,Q.NUM,L.PRECO,L.SENHA,L.CHECKIN,L.CHECKOUT, " +
            "CASE (CALC.DIARIAS) " +
            "WHEN 0 THEN 1 ELSE (CALC.DIARIAS) END AS DIARIAS, " +
            "COALESCE(CALC.CON,0) AS CONSUMOS, " +
            "L.CHECKED_IN " +
            "FROM CALC " +
            "JOIN LOCACAO L ON L.ID_USUARIO = :USUARIO " +
            "JOIN QUARTO Q ON Q.ID=L.ID_QUARTO OR L.NO_SHOW = TRUE " +
            "WHERE NOW() >= L.CHECKOUT " +
            "ORDER BY L.CHECKIN DESC,L.CHECKOUT DESC",nativeQuery = true)
    List<M_ViewLocacao> getLocacaoCompleta(@Param("USUARIO") Long usuario);

    @Query(value = "WITH CALC AS (SELECT (L.CHECKOUT-L.CHECKIN) AS DIARIAS " +
            "FROM LOCACAO L " +
            "WHERE L.ID_USUARIO = :USUARIO AND NOW() < L.CHECKIN) " +
            "SELECT L.ID,Q.NUM,L.PRECO,L.SENHA,L.CHECKIN,L.CHECKOUT, " +
            "CASE (CALC.DIARIAS) " +
            "WHEN 0 THEN 1 ELSE (CALC.DIARIAS) END AS DIARIAS, " +
            "0 AS CONSUMOS, " +
            "FALSE AS CHECKED_IN " +
            "FROM CALC " +
            "JOIN LOCACAO L ON L.ID_USUARIO = :USUARIO " +
            "JOIN QUARTO Q ON Q.ID=L.ID_QUARTO " +
            "WHERE NOW() < L.CHECKIN " +
            "ORDER BY L.CHECKIN DESC,L.CHECKOUT DESC",nativeQuery = true)
    List<M_ViewLocacao> getLocacaoEmReserva(@Param("USUARIO") Long usuario);


    @Query(value = "WITH CALC AS (SELECT (L.CHECKOUT-L.CHECKIN) AS DIARIAS, " +
            "SUM(C.PRECO) AS CON FROM LOCACAO L " +
            "LEFT JOIN CONSUMO C ON C.ID_LOCACAO = L.ID AND C.ID != 8 " +
            "WHERE L.ID_USUARIO = :USUARIO AND (NOW() BETWEEN L.CHECKIN AND L.CHECKOUT) " +
            "AND L.NO_SHOW = FALSE " +
            "GROUP BY L.ID) " +
            "SELECT L.ID,Q.NUM,L.PRECO,L.SENHA,L.CHECKIN,L.CHECKOUT, " +
            "CASE (CALC.DIARIAS) " +
            "WHEN 0 THEN 1 ELSE (CALC.DIARIAS) END AS DIARIAS, " +
            "COALESCE(CALC.CON,0) AS CONSUMOS, " +
            "L.CHECKED_IN " +
            "FROM CALC " +
            "JOIN LOCACAO L ON L.ID_USUARIO = :USUARIO " +
            "JOIN QUARTO Q ON Q.ID=L.ID_QUARTO " +
            "WHERE (NOW() BETWEEN L.CHECKIN AND L.CHECKOUT) AND L.NO_SHOW = FALSE " +
            "ORDER BY L.CHECKIN DESC,L.CHECKOUT DESC",nativeQuery = true)
    List<M_ViewLocacao> getLocacaoEmCurso(@Param("USUARIO") Long usuario);

    @Query(value = "SELECT *" +
            "FROM LOCACAO" +
            "WHERE NOW() BETWEEN CHECKIN AND CHECKOUT-1 AND NO_SHOW = FALSE" +
            "ORDER BY CHECKIN DESC,CHECKOUT DESC",nativeQuery = true)
    List<M_Locacao> getLocacaoParaConsumo();
    @Query(value = "SELECT * FROM LOCACAO WHERE ID = :ID AND ID_USUARIO = :USUARIO AND CHECKED_IN = TRUE LIMIT 1",nativeQuery = true)
    M_Locacao getLocacaoByIdAndUser(@Param("ID") Long id,@Param("USUARIO") Long usuario);
}
