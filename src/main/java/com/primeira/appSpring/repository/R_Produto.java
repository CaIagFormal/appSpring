package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Produto extends JpaRepository<M_Produto, Long> {
}
