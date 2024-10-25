package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.stereotype.Service;

@Service
public class S_Cadastro {
    private static R_Usuario r_usuario;

    public S_Cadastro(R_Usuario r_usuario) {
        this.r_usuario = r_usuario;
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
}
