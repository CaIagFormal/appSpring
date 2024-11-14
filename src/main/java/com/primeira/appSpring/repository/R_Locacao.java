package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Locacao extends JpaRepository<M_Locacao, Long> {
    @Query(value = "SELECT * FROM LOCACAO WHERE SENHA = :SENHA AND NOW() BETWEEN CHECKIN AND CHECKOUT LIMIT 1",nativeQuery = true)
    M_Locacao getLocacaoBySenha(@Param("SENHA") String senha);
}
