package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Api;
import com.primeira.appSpring.model.M_Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface R_Produto extends JpaRepository<M_Produto, Long> {
    @Query(value = "SELECT * FROM PRODUTO WHERE COD_BARRAS IS NOT NULL", nativeQuery = true)
    List<M_Produto> availableProducts();

    @Query(value = "with g_consumo as\n" +
            "\t(select\n" +
            "\t\tp.id as id_produto,\n" +
            "\t\tsum(c.qtd) as quantidade,\n" +
            "\t\tsum(c.preco)/sum(c.qtd) as custo_medio\n" +
            "\tfrom produto p \n" +
            "\tjoin consumo c on c.id_produto = p.id\n" +
            "\twhere c.id_produto <> 8 and c.data <= :data\n" +
            "\tgroup by p.id\n" +
            "\t),\n" +
            "\n" +
            "g_compra as \n" +
            "\t(select\n" +
            "\t\tc.id_produto,\n" +
            "\t\tsum(c.quantidade) as quantidade,\n" +
            "\t\tcast(max(c.data) as date) as ultima_compra\n" +
            "\tfrom compra c\n" +
            "\twhere c.id_produto <> 8 and c.data <= :data\n" +
            "\tgroup by c.id\n" +
            "\t)\n" +
            "\t\n" +
            "select\n" +
            "\tp.cod_barras as produto,\n" +
            "\tg_compra.quantidade-g_consumo.quantidade as quantidade,\n" +
            "\tp.min as min,\n" +
            "\tp.max as max,\n" +
            "\tg_consumo.custo_medio,\n" +
            "\tg_compra.ultima_compra\n" +
            "\tfrom g_consumo\n" +
            "\tjoin g_compra on g_consumo.id_produto = g_compra.id_produto\n" +
            "\tjoin produto p on g_consumo.id_produto = p.id\n" +
            "\torder by p.id", nativeQuery = true)
    List<M_Api> requestApi(@Param("data") LocalDate data);
}
