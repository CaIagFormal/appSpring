package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface R_Produto extends JpaRepository<M_Produto, Long> {
    @Query(value = "SELECT * FROM PRODUTO WHERE COD_BARRAS IS NOT NULL",nativeQuery = true)
    List<M_Produto> availableProducts();
}
