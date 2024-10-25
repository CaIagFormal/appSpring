package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S_Login {

    @Autowired
    private static R_Usuario r_usuario;

    public S_Login(R_Usuario r_usuario){
        S_Login.r_usuario = r_usuario;
    }

    public static M_Usuario validarLogin(String usuario, String senha) {
        return r_usuario.getUsuarioByUsuarioSenha(usuario, senha);
    }
}
