package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Consumo;
import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.repository.R_Consumo;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class S_Diarias {

    private final R_Produto r_produto;
    private final R_Consumo r_consumo;
    private final R_Locacao r_locacao;

    public S_Diarias(R_Produto r_produto, R_Consumo r_consumo, R_Locacao r_locacao) {
        this.r_produto = r_produto;
        this.r_consumo = r_consumo;
        this.r_locacao = r_locacao;
    }

    @Scheduled(cron = "0 0 10 * * ? ")
    public void gerarConsumoDiaria() {
        List<M_Locacao> m_locacaos = r_locacao.getLocacaoParaConsumo();

        M_Produto m_produto = r_produto.getReferenceById((long) 8);
        for (M_Locacao m_locacao:m_locacaos) {
            gerarConsumoLocacao(m_locacao,m_produto);
        }
    }

    public void gerarConsumoLocacao(M_Locacao m_locacao,M_Produto m_produto) {

        M_Consumo m_consumo = new M_Consumo();

        m_consumo.setData(LocalDateTime.now());
        m_consumo.setProduto(m_produto);
        m_consumo.setQtd(1);
        m_consumo.setPreco(m_locacao.getPreco());
        m_consumo.setLocacao(m_locacao);
        r_consumo.save(m_consumo);
    }

    public void gerarConsumoInicial(M_Locacao m_locacao) {
        if (!(LocalDate.now().isEqual(m_locacao.getCheckin().toLocalDate()))) {
            return;
        }

        if (LocalDateTime.now().getHour() < 10) {
            if (!(LocalDate.now().isEqual(m_locacao.getCheckout().toLocalDate()))) {
                return;
            }
        }
        M_Produto m_produto = r_produto.getReferenceById((long) 8);
        gerarConsumoLocacao(m_locacao,m_produto);

    }
}
