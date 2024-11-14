package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.repository.R_Locacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

//RETORNA CHAR '1' ou '0'
@Service
public class S_Arduino {
    @Autowired
    private static R_Locacao r_locacao;

    public S_Arduino(R_Locacao r_locacao){
        this.r_locacao = r_locacao;
    }

    public static char valida_senha(String entrada) {
        System.out.println("Entrada: "+entrada);
        return (r_locacao.getLocacaoBySenha(entrada) != null) ? '1' : '0';
    }
}
