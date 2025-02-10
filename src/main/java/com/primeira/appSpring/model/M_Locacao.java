package com.primeira.appSpring.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name="locacao")
public class M_Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private M_Usuario usuario;

    private Date checkin;

    private Date checkout;

    @ManyToOne
    @JoinColumn(name="id_quarto")
    private M_Quarto quarto;

    private String senha;

    private BigDecimal preco;

    @Value("${props.boolean.isFPL:#{false}}")
    private boolean checked_in;

    @Value("${props.boolean.isFPL:#{false}}")
    private boolean no_show;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public M_Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(M_Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public M_Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(M_Quarto quarto) {
        this.quarto = quarto;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public boolean isChecked_in() {
        return checked_in;
    }

    public void setChecked_in(boolean checked_in) {
        this.checked_in = checked_in;
    }

    public boolean isNo_show() {
        return no_show;
    }

    public void setNo_show(boolean no_show) {
        this.no_show = no_show;
    }
}
