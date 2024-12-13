package com.primeira.appSpring.model;

import java.math.BigDecimal;
import java.sql.Date;

public interface M_ViewLocacao {
    long getNum();
    BigDecimal getPreco();
    String getSenha();
    Date getCheckin();
    Date getCheckout();
    int getDiarias();

}
