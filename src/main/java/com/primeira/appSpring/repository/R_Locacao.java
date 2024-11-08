package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Locacao extends JpaRepository<M_Locacao, Long> {
}
