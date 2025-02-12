package com.primeira.appSpring.repository;


import com.primeira.appSpring.model.M_Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Programa extends JpaRepository<M_Programa, Long> {

}
