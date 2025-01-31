package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Consumo extends JpaRepository<M_Consumo, Long> {
}
