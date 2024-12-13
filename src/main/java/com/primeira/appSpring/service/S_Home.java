package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.model.M_ViewLocacao;
import com.primeira.appSpring.repository.R_Locacao;
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

    public static List<M_ViewLocacao> getLocacaoEmCurso(M_Usuario usuario) {
        try {
            return r_locacao.getLocacaoEmCurso(usuario.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<M_ViewLocacao> getLocacaoCompleta(M_Usuario usuario) {
        try {
            return r_locacao.getLocacaoCompleta(usuario.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<M_ViewLocacao> getLocacaoReserva(M_Usuario usuario) {
        try {
            return r_locacao.getLocacaoEmReserva(usuario.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
