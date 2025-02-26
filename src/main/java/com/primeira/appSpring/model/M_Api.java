package com.primeira.appSpring.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface M_Api {
    String getProduto();
    Long getQuantidade();
    Integer getMin();
    Integer getMax();
    BigDecimal getCusto_medio();
    LocalDate getUltima_compra();
}
