package com.primeira.appSpring.model;

import jakarta.persistence.*;

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

    private BigDecimal senha;
}
