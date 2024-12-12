package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
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

    @Query(value = "SELECT * FROM LOCACAO WHERE ID_USUARIO = :USUARIO AND NOW() >= CHECKOUT",nativeQuery = true)
    List<M_Locacao> getLocacaoCompleta(@Param("USUARIO") Long usuario);

    @Query(value = "SELECT * FROM LOCACAO WHERE ID_USUARIO = :USUARIO AND NOW() < CHECKIN",nativeQuery = true)
    List<M_Locacao> getLocacaoEmReserva(@Param("USUARIO") Long usuario);

    @Query(value = "SELECT * FROM LOCACAO WHERE ID_USUARIO = :USUARIO AND NOW() BETWEEN CHECKIN AND CHECKOUT",nativeQuery = true)
    List<M_Locacao> getLocacaoEmCurso(@Param("USUARIO") Long usuario);
}
