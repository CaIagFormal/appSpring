package com.primeira.appSpring.service;


import com.primeira.appSpring.model.M_Consumo;
import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.repository.R_Consumo;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class S_Refeitorio {

    private static R_Produto r_produto;
    private static R_Consumo r_consumo;

    public S_Refeitorio(R_Produto r_produto, R_Consumo r_consumo) { S_Refeitorio.r_produto = r_produto;
        S_Refeitorio.r_consumo = r_consumo;}

    public List<M_Produto> findAll() {
        return r_produto.findAll();
    }

    public List<M_Consumo> incluirItens(long[][] itens, M_Locacao locacao) {
        LocalDateTime now = LocalDateTime.now();
        List<M_Consumo> m_consumoList = new ArrayList<>();
        for (int i = 0; i < itens.length; i++) {
            M_Consumo m_consumo = new M_Consumo();
            m_consumo.setLocacao(locacao);
            m_consumo.setData(now);
            m_consumo.setQtd((int) itens[i][1]);
            m_consumo.setProduto(r_produto.getReferenceById(itens[i][0]));

            m_consumo.setPreco(m_consumo.getProduto().getPreco().multiply(BigDecimal.valueOf(m_consumo.getQtd())));
            m_consumo = r_consumo.save(m_consumo);
            m_consumoList.add(m_consumo);
        }
        return m_consumoList;
    }

    public long[][] gerarItens(String itens) {
        String[] split1 = itens.split("\\|");
        String[] split2;
        long[][] l_split = new long[split1.length][2];
        for (int i = 0; i < split1.length ; i++) {
            split2 = split1[i].split(",");
            l_split[i][0] = Long.decode(split2[0]);
            l_split[i][1] = Long.decode(split2[1]);
        }
        return l_split;
    }
}
