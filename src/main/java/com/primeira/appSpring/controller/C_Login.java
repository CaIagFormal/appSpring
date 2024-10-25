package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class C_Login {
    @GetMapping("/")
    public String getLogin() {
        return "index";
    }
    @PostMapping("/")
    public String postLogin(@RequestParam("Usuario") String usuario,
                            @RequestParam("Senha") String senha) {
        M_Usuario m_usuario = S_Login.validarLogin(usuario, senha);
        if (m_usuario==null) {
            return "index";
        }
        return "home/home";
    }
}