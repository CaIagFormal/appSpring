package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class S_Home {
    private static R_Locacao r_locacao;

    public S_Home(R_Locacao r_locacao) {
        this.r_locacao = r_locacao;
    }

    //public static List<M_Usuario> getUsuarios() {
    //    return r_usuario.findAll();
    //}

    public static List<M_Locacao> getLocacaosByUser(M_Usuario usuario) { return r_locacao.getLocacaoByUsuario(usuario.getId());}

    public static List<M_Locacao> getLocacaoEmCurso(M_Usuario usuario) {
        return r_locacao.getLocacaoEmCurso(usuario.getId());
    }

    public static List<M_Locacao> getLocacaoCompleta(M_Usuario usuario) {
        return r_locacao.getLocacaoCompleta(usuario.getId());
    }

    public static List<M_Locacao> getLocacaoReserva(M_Usuario usuario) {
        return r_locacao.getLocacaoEmReserva(usuario.getId());
    }
}
