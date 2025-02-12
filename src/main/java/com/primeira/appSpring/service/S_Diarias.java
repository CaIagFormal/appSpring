package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Consumo;
import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.model.M_Programa;
import com.primeira.appSpring.repository.R_Consumo;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Produto;
import com.primeira.appSpring.repository.R_Programa;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class S_Diarias {

    private final R_Produto r_produto;
    private final R_Consumo r_consumo;
    private final R_Locacao r_locacao;
    private final R_Programa r_programa;

    @Qualifier("transactionManager")
    protected final PlatformTransactionManager txManager;

    public S_Diarias(R_Produto r_produto, R_Consumo r_consumo,
                     R_Locacao r_locacao, R_Programa r_programa, PlatformTransactionManager txManager) {
        this.r_produto = r_produto;
        this.r_consumo = r_consumo;
        this.r_locacao = r_locacao;
        this.r_programa = r_programa;
        this.txManager = txManager;
    }

    @Scheduled(cron = "0 0 10 * * ? ")
    public void gerarConsumoDiaria() {
        M_Programa m_programa = r_programa.getReferenceById(1L);
        m_programa.setLast_run(LocalDateTime.now());
        List<M_Locacao> m_locacaos = r_locacao.getLocacaoParaConsumo();

        M_Produto m_produto = r_produto.getReferenceById((long) 8);
        for (M_Locacao m_locacao:m_locacaos) {
            gerarConsumoLocacao(m_locacao,m_produto);

        }
    }

    @PostConstruct
    public void init() {

        TransactionTemplate tmpl = new TransactionTemplate(txManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
             @Override
             protected void doInTransactionWithoutResult(TransactionStatus status) {
                 consertarContabilizacao();
             }
         });
    }

    private void consertarContabilizacao() {

        r_locacao.atualizarNoShow();

        M_Programa m_programa = r_programa.getReferenceById(1L);
        // Confere se o programa já rodou na data atual
        if (m_programa.getLast_run().toLocalDate().isEqual(LocalDate.now())) {
            return;
        }

        // Confere se o programa rodou antes das 10
        if (m_programa.getLast_run().isBefore(LocalDateTime.now().withHour(10).withMinute(0).withSecond(0).minusNanos(0))) {
            return;
        }
        gerarConsumoDiaria();
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
