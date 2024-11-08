package com.primeira.appSpring.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="quarto")
public class M_Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long num;

    private BigDecimal preco;

    private String descricao;

    private String classe;
    private int qtd_camas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public int getQtd_camas() {
        return qtd_camas;
    }

    public void setQtd_camas(int qtd_camas) {
        this.qtd_camas = qtd_camas;
    }
}
