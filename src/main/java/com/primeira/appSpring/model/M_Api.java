package com.primeira.appSpring.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface M_Api {
    String getProduto();
    Long getQuantidade();
    Integer getMin();
    Integer getMax();
    BigDecimal getCustoMedio();
    LocalDateTime getUltimaCompra();
}
