package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Quarto;
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

    public static List<M_Quarto> getQuartos(LocalDate checkout) {
        return r_quarto.getAvailableQuarto(LocalDate.now(),checkout);
    }

    public static M_Locacao cadastrarLocacao(M_Usuario usuario, String quarto, LocalDate checkin, LocalDate checkout) {
        boolean pode_salvar = true;
        if (checkout == null) {
            pode_salvar = false;
        } else {
            if (checkin.isAfter(checkout)) {
                pode_salvar = false;
            }
        }
        if (quarto.equals("Quarto")) {
            pode_salvar = false;
        }

        System.out.println(quarto + " " + checkin + " " + checkout);

        if (pode_salvar) {
            M_Locacao m_locacao = new M_Locacao();
            m_locacao.setSenha(S_Cadastro.gerarSenha());
            m_locacao.setCheckin(Date.valueOf(checkin));
            m_locacao.setCheckout(Date.valueOf(checkout));
            m_locacao.setQuarto(r_quarto.getQuartoById(Integer.valueOf(quarto)));
            m_locacao.setUsuario(usuario);

            return r_locacao.save(m_locacao);
        }
        return null;
    }

    public static String gerarSenha() {

        return String.valueOf(new Random().nextLong(999999999));
    }


}
