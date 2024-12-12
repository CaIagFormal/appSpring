package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Resposta;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Quarto;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class S_Cadastro {
    private static R_Usuario r_usuario;
    private static R_Locacao r_locacao;
    private static R_Quarto r_quarto;

    public S_Cadastro(R_Usuario r_usuario, R_Locacao r_locacao, R_Quarto r_quarto) {
        this.r_usuario = r_usuario;
        this.r_locacao = r_locacao;
        this.r_quarto = r_quarto;
    }

    public static M_Usuario cadastrarUsuario(String usuario, String usuario_conf,
                                             String apelido, String apelido_conf,
                                             String senha, String conf_senha) {
        boolean pode_salvar = true;

        if (senha.trim().equals("") || senha == null || !senha.trim().equals(conf_senha)) {
            pode_salvar = false;
        }

        if (usuario.trim().equals("") || usuario == null || !usuario.trim().equals(usuario_conf)) {
            pode_salvar = false;
        }

        if (apelido.trim().equals("") || apelido == null || !apelido.trim().equals(apelido_conf)) {
            pode_salvar = false;
        }

        if (pode_salvar) {
            M_Usuario m_usuario = new M_Usuario();
            m_usuario.setUsuario(usuario);
            m_usuario.setApelido(apelido);
            m_usuario.setSenha(senha);
            return r_usuario.save(m_usuario);
        }
        return null;
    }

    public static List<M_Quarto> getQuartos(LocalDate checkin, LocalDate checkout) {
        return r_quarto.getAvailableQuarto(checkin,checkout);
    }

    public static M_Resposta cadastrarLocacao(M_Usuario usuario, M_Quarto quarto, LocalDate checkin, LocalDate checkout) {
        M_Resposta m_resposta = new M_Resposta();
        try {


            M_Locacao m_locacao = new M_Locacao();
            m_locacao.setSenha(S_Cadastro.gerarSenha());
            m_locacao.setCheckin(Date.valueOf(checkin));
            m_locacao.setCheckout(Date.valueOf(checkout));
            m_locacao.setQuarto(quarto);
            m_locacao.setUsuario(usuario);
            m_locacao.setPreco(m_locacao.getQuarto().getPreco());

            m_resposta.setObjeto(r_locacao.save(m_locacao));
            m_resposta.setSucesso(m_resposta.getObjeto() != null);
            m_resposta.setMensagem(m_resposta.isSucesso() ? null : "Houve um problema ao registrar a locação\n");
        } catch (Exception e) {
            m_resposta.setSucesso(false);
            m_resposta.setMensagem("Houve um problema ao acessar o servidor\n");
        }
        return m_resposta;
    }

    public static String gerarSenha() {

        return String.valueOf(new Random().nextLong(999999999));
    }

    public static M_Resposta conferirReserva(String quarto, LocalDate checkin, LocalDate checkout) {
        M_Resposta m_resposta = new M_Resposta();
        m_resposta.setSucesso(false);
        String resposta="";
        if (checkin == null) {
            resposta += "Check-in não foi preenchido\n";
        }
        if (checkout == null) {
            resposta += "Check-out não foi preenchido\n";
        }
        if (quarto.equals("Quarto")) {
            resposta += "Quarto não foi selecionado\n";
        }
        if (checkin.isAfter(checkout)) {
            resposta += "Check-in está após Check-out\n";
        }
        if (resposta.isBlank()) {
            try {
                Long quarto_id = Long.valueOf(quarto);
                m_resposta.setObjeto(r_quarto.isQuartoAvailable(checkin, checkout, quarto_id));
                m_resposta.setSucesso(m_resposta.getObjeto() != null && resposta.isBlank());
                resposta += m_resposta.isSucesso() ? null : "Quarto já foi alocado ou não existe\n";
            } catch (Exception e) {
                m_resposta.setMensagem("Erro ao verificar quarto");
                e.printStackTrace();
            }
        }
        m_resposta.setMensagem(resposta);
        return m_resposta;
    }

}
