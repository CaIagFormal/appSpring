package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.repository.R_Locacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

//RETORNA CHAR '1' ou '0'
@Service
public class S_Arduino {

    private static R_Locacao r_locacao;

    public S_Arduino(R_Locacao r_locacao) {
        this.r_locacao = r_locacao;
    }
    public static char valida_senha(String entrada) {
        M_Locacao m_locacao = r_locacao.getReferenceById(1L);
        if (m_locacao.getCheckout().toLocalDate().isAfter(LocalDate.now())) {
            return '0';
        }
        String senha = m_locacao.getSenha().toString();
        return senha.equals(entrada) ? '1' : '0';
    }
}
