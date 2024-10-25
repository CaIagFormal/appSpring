package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Usuario extends JpaRepository<M_Usuario, Long> {
    @Query(value = "SELECT * FROM USUARIO WHERE USUARIO = :USUARIO AND SENHA = :SENHA LIMIT 1", nativeQuery = true)
    M_Usuario getUsuarioByUsuarioSenha(@Param("USUARIO") String usuario, @Param("SENHA") String senha);
}
