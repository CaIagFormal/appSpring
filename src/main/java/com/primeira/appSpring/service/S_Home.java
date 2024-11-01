package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class S_Home {
    private static R_Usuario r_usuario;

    public S_Home(R_Usuario r_usuario) {
        this.r_usuario = r_usuario;
    }

    public static List<M_Usuario> getUsuarios() {
        return r_usuario.findAll();
    }
}
