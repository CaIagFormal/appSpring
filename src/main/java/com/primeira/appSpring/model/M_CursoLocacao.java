package com.primeira.appSpring.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.sql.Date;

public interface M_CursoLocacao {

    long getId();

    boolean getEmcurso();
}
