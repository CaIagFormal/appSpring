package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Consumo;
import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.repository.R_Consumo;
import com.primeira.appSpring.repository.R_Locacao;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class S_Email {
    private final JavaMailSender mailSender;
    private final R_Locacao r_locacao;
    private final R_Consumo r_consumo;
    private final DateTimeFormatter dtf;
    private final DateTimeFormatter dtf_time;

    private final String[] meses;

    public S_Email(JavaMailSender mailSender, R_Locacao r_locacao, R_Consumo r_consumo) {
        this.mailSender = mailSender;
        this.r_locacao = r_locacao;
        this.r_consumo = r_consumo;
        this.dtf = DateTimeFormatter.ofPattern("dd' de 'MM', 'YYYY");
        this.dtf_time = DateTimeFormatter.ofPattern("dd' de 'MM', 'YYYY' às 'HH:mm:ss");
        this.meses = new String[]{"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    }

    @Scheduled(cron = "00 05 16 * * ? ")
    public void gerarLembreteAntecedente() {
        List<M_Locacao> m_locacao_list = r_locacao.getLocacoesWhereCheckinIsTomorrow();

        for (M_Locacao m_locacao: m_locacao_list) {
            long diarias = (m_locacao.getCheckout().toLocalDate().toEpochDay()-m_locacao.getCheckin().toLocalDate().toEpochDay());
            diarias = (diarias==0)?1:diarias;

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Venha logo se não...");
            mailMessage.setText("Olá "+m_locacao.getUsuario().getApelido()+",\n\n"+
                    "\tPor favor compareça amanhã fazer Check-in no quarto "+m_locacao.getQuarto().getNum()+". "+
                    "Aqui está as informações sobre sua locação:\n\n"+
                    "\tCheck-in: "+formatarDataEmPtBR(m_locacao.getCheckin().toLocalDate())+"\n"+
                    "\tCheck-out: "+formatarDataEmPtBR(m_locacao.getCheckout().toLocalDate())+"\n"+
                    "\tTotal Estimado: m_locacao.getPreco().toString()"+diarias*Double.parseDouble(m_locacao.getPreco().toString())+"\n"+
                    "\tSenha: "+m_locacao.getSenha()+"\n"+
                    "\tQuarto: "+ m_locacao.getQuarto().getNum()+"\n\n"+
                    "Obrigado por escolher RAM GRÁTIS, venha logo se não...");
            mailMessage.setTo(m_locacao.getUsuario().getUsuario());

            mailSender.send(mailMessage);
        }
    }

    @Scheduled(cron = "0 31 16 * * ? ")
    public void gerarLembreteNoShow() {
        List<M_Locacao> m_locacao_list = r_locacao.getLocacoesWhereNoShowWasYesterday();
        for (M_Locacao m_locacao: m_locacao_list) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("No show...");

            mailMessage.setText("Olá "+m_locacao.getUsuario().getApelido()+",\n\n"+
                    "\tPor que você não apareceu no quarto "+m_locacao.getQuarto().getNum()+" na sua locação em "+formatarDataEmPtBR(m_locacao.getCheckin().toLocalDate())+"? "+
                    "Agora terá de pagar as consequências: R$"+m_locacao.getPreco().toString()+"!\n\n"+
                    "Obrigado por escolher RAM GRÁTIS, nunca mais faça isso se não...");

            mailMessage.setTo(m_locacao.getUsuario().getUsuario());

            mailSender.send(mailMessage);
        }
    }

    @Scheduled(cron = "0 20 17 * * ? ")
    public void gerar() {
        List<M_Locacao> m_locacao_list = r_locacao.getLocacoesWhereCheckOutWasYesterday();
        for (M_Locacao m_locacao: m_locacao_list) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Locação Concluída");

            List<M_Consumo> m_consumo_list = r_consumo.getConsumosByLocacao(m_locacao.getId());
            double diarias = (m_locacao.getCheckout().toLocalDate().toEpochDay()-m_locacao.getCheckin().toLocalDate().toEpochDay());
            diarias = (diarias==0)?1:diarias;
            diarias = diarias*m_locacao.getPreco().doubleValue();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Olá "+m_locacao.getUsuario().getApelido()+",\n\n");
            stringBuilder.append("Aqui estão alguns dados sobre a sua locação terminada: \n");
            stringBuilder.append("\tCheck-in: "+formatarDataEmPtBR(m_locacao.getCheckin().toLocalDate())+"\n");
            stringBuilder.append("\tCheck-out: "+formatarDataEmPtBR(m_locacao.getCheckout().toLocalDate())+"\n");
            stringBuilder.append("\tDiárias: R$"+diarias+"\n");
            stringBuilder.append("\tQuarto: "+ m_locacao.getQuarto().getNum()+"\n");
            stringBuilder.append("\tConsumos:\n");

            double total = 0;
            for (M_Consumo m_consumo:m_consumo_list) {
                if (m_consumo.getProduto().getId()==8) {
                    continue;
                }
                stringBuilder.append("\t\t"+formatarDataEmPtBRTempo(m_consumo.getData())+": "+m_consumo.getQtd()+"x "+m_consumo.getProduto().getDescricao()+" = R$"+m_consumo.getPreco()+"\n");
                total += m_consumo.getPreco().doubleValue();
            }

            stringBuilder.append("\n\tTotal: R$"+(diarias+total)+"\n\n");

            stringBuilder.append("Obrigado por escolher RAM GRÁTIS, obrigado por fazer tudo certo");
            mailMessage.setText(stringBuilder.toString());

            mailMessage.setTo(m_locacao.getUsuario().getUsuario());

            mailSender.send(mailMessage);
        }
    }

    public String formatarDataEmPtBR(LocalDate temporal) {


        String base = dtf.format(temporal);
        String mes = meses[Integer.parseInt(base.substring(6, 8)) - 1];
        return base.substring(0, 6) + mes + base.substring(8, 14);
    }

    public String formatarDataEmPtBRTempo(LocalDateTime temporal) {


        String base = dtf_time.format(temporal);
        String mes = meses[Integer.parseInt(base.substring(6, 8)) - 1];
        return base.substring(0, 6) + mes + base.substring(8, 14);
    }
}
