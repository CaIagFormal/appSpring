package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Home;
import com.primeira.appSpring.service.S_Login;
import com.primeira.appSpring.service.S_Session;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Login {

    private final S_Session s_session;
    public C_Login(S_Session s_session) {
        this.s_session = s_session;
    }
    @GetMapping("/")
    public String getLogin(
            HttpSession session,
            Model model
    ) {
        if (!(s_session.has_session(session))) {
            model.addAttribute("emcurso", S_Home.getLocacaoEmCurso((M_Usuario) session.getAttribute("usuario")));
            model.addAttribute("completa", S_Home.getLocacaoCompleta((M_Usuario) session.getAttribute("usuario")));
            model.addAttribute("reserva", S_Home.getLocacaoReserva((M_Usuario) session.getAttribute("usuario")));

            return "home/home";
        }
        return "index";
    }
    @PostMapping("/")
    public String postLogin(@RequestParam("Usuario") String usuario,
                            @RequestParam("Senha") String senha,
                            HttpSession session) {
        M_Usuario m_usuario = S_Login.validarLogin(usuario, senha);

        session.setAttribute("usuario",m_usuario);
        return "redirect:/";
    }

    @GetMapping("/logout")
    @ResponseBody
    public boolean getLogout(HttpSession session) {
        session.setAttribute("usuario",null);
        return true;
    }
}