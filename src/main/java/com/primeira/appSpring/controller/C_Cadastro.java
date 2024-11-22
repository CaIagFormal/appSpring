package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Cadastro;
import com.primeira.appSpring.service.S_Home;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class C_Cadastro {

    //Usuario
    @GetMapping("/cadastro")
    public String getCadastro() {
        return "cadastro/cadastro";
    }
    @PostMapping("/cadastro")
    public String postCadastro(@RequestParam("Usuario") String usuario,
                               @RequestParam("UsuarioConf") String conf_usuario,
                               @RequestParam("Apelido") String apelido,
                               @RequestParam("ApelidoConf") String conf_apelido,
                               @RequestParam("Senha") String senha,
                               @RequestParam("SenhaConf") String conf_senha) {
        M_Usuario m_usuario = S_Cadastro.cadastrarUsuario(usuario, conf_usuario, apelido, conf_apelido, senha, conf_senha);
        if (m_usuario==null) {
            return "cadastro/cadastro";
        }
        return "index";
    }

    //Locação
    @GetMapping("/cadLocacao")
    public String getCadLocacao(HttpSession session, Model model) {
        model.addAttribute("mensagem",null);
        if (!(session.getAttribute("usuario") ==null)) {


            return "locacao/cadastro";
        }
        return "redirect:/";
    }

    @PostMapping("/cadLocacao")
    public String postCadLocacao(@RequestParam("quarto") String quarto,
                                 @RequestParam("checkout") LocalDate checkout,
                                 HttpSession session,
                                 Model model) {
        M_Locacao m_locacao = S_Cadastro.cadastrarLocacao((M_Usuario) session.getAttribute("usuario"),quarto,LocalDate.now(),checkout);
        if (!(m_locacao == null)) {
            model.addAttribute("mensagem", "A locação foi criada");
            model.addAttribute("locacoes", S_Home.getLocacaosByUser((M_Usuario) session.getAttribute("usuario")));
            return "redirect:/";
        }
        model.addAttribute("mensagem", "Deu erro ao criar");
        return "redirect:/cadLocacao";
    }
    @PostMapping("/getQuartosDisp")
    public String getQuartosDisp(@RequestParam("checkout") LocalDate checkout,
                                 Model model) {
        model.addAttribute("quartos", S_Cadastro.getQuartos(checkout));
        return "pv/quartosdisp";
    }
}
