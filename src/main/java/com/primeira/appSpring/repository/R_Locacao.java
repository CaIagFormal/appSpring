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

    @Query(value = "SELECT L.ID,Q.NUM,L.PRECO,L.SENHA,L.CHECKIN,L.CHECKOUT, " +
            "CASE (L.CHECKOUT-L.CHECKIN) " +
            "WHEN 0 THEN 1 ELSE (L.CHECKOUT-L.CHECKIN) END AS DIARIAS, " +
            "CASE WHEN (SUM(C.PRECO)) IS NULL THEN 0 ELSE (SUM(C.PRECO)) END AS CONSUMOS " +
            "FROM LOCACAO L " +
            "JOIN QUARTO Q ON Q.ID=L.ID_QUARTO " +
            "LEFT JOIN CONSUMO C ON C.ID_LOCACAO = L.ID " +
            "WHERE L.ID_USUARIO = :USUARIO AND NOW() >= L.CHECKOUT " +
            "GROUP BY L.ID,Q.NUM ORDER BY L.CHECKIN DESC,L.CHECKOUT DESC",nativeQuery = true)
    List<M_ViewLocacao> getLocacaoCompleta(@Param("USUARIO") Long usuario);

    @Query(value = "SELECT L.ID,Q.NUM,L.PRECO,L.SENHA,L.CHECKIN,L.CHECKOUT, " +
            "CASE (L.CHECKOUT-L.CHECKIN) " +
            "WHEN 0 THEN 1 ELSE (L.CHECKOUT-L.CHECKIN) END AS DIARIAS,0 AS CONSUMOS " +
            "FROM LOCACAO L " +
            "JOIN QUARTO Q ON Q.ID=L.ID_QUARTO " +
            "WHERE L.ID_USUARIO = :USUARIO AND NOW() < L.CHECKIN " +
            "ORDER BY L.CHECKIN DESC,L.CHECKOUT DESC",nativeQuery = true)
    List<M_ViewLocacao> getLocacaoEmReserva(@Param("USUARIO") Long usuario);

    @Query(value = "SELECT L.ID,Q.NUM,L.PRECO,L.SENHA,L.CHECKIN,L.CHECKOUT, " +
            "CASE (L.CHECKOUT-L.CHECKIN) " +
            "WHEN 0 THEN 1 ELSE (L.CHECKOUT-L.CHECKIN) END AS DIARIAS, " +
            "CASE WHEN (SUM(C.PRECO)) IS NULL THEN 0 ELSE (SUM(C.PRECO)) END AS CONSUMOS " +
            "FROM LOCACAO L " +
            "JOIN QUARTO Q ON Q.ID=L.ID_QUARTO " +
            "LEFT JOIN CONSUMO C ON C.ID_LOCACAO = L.ID " +
            "WHERE L.ID_USUARIO = :USUARIO AND NOW() BETWEEN L.CHECKIN AND L.CHECKOUT " +
            "GROUP BY L.ID,Q.NUM ORDER BY L.CHECKIN DESC,L.CHECKOUT DESC",nativeQuery = true)
    List<M_ViewLocacao> getLocacaoEmCurso(@Param("USUARIO") Long usuario);

    @Query(value = "SELECT * FROM LOCACAO WHERE ID = :ID AND ID_USUARIO = :USUARIO LIMIT 1",nativeQuery = true)
    M_Locacao getLocacaoByIdAndUser(@Param("ID") Long id,@Param("USUARIO") Long usuario);
}
