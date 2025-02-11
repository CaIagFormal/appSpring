package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.repository.R_Locacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

//RETORNA CHAR '1' ou '0'
@Service
public class S_Arduino {
    private static R_Locacao r_locacao;

    public S_Arduino(R_Locacao r_locacao){
        this.r_locacao = r_locacao;
    }

    public static char valida_senha(String entrada) {
        System.out.println("Entrada: "+entrada);
        M_Locacao m_locacao = r_locacao.getLocacaoBySenha(entrada);
        if (m_locacao == null) {
            return '0';
        }
        m_locacao.setChecked_in(true);
        r_locacao.save(m_locacao);
        return '1';
    }
}
