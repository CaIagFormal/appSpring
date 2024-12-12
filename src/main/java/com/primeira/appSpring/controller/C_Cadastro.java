package com.primeira.appSpring.controller;

import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Resposta;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Cadastro;
import com.primeira.appSpring.service.S_Home;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
        if (!(session.getAttribute("usuario") ==null)) {


            return "locacao/cadastro";
        }
        return "redirect:/";
    }

    @PostMapping("/cadLocacao")
    @ResponseBody
    public String postCadLocacao(@RequestParam("quarto") String quarto,
                                 @RequestParam("checkout") LocalDate checkout,
                                 @RequestParam("checkin") LocalDate checkin,
                                 HttpSession session,
                                 Model model,
                                 HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        M_Resposta verif_confere = S_Cadastro.conferirReserva(quarto,checkin,checkout);
        if (!verif_confere.isSucesso()) {
            return verif_confere.getMensagem();
        }
        M_Resposta cadast_confere = S_Cadastro.cadastrarLocacao((M_Usuario) session.getAttribute("usuario"),(M_Quarto) verif_confere.getObjeto(),checkin,checkout);
        if (cadast_confere.isSucesso()) {
            M_Locacao m_locacao = (M_Locacao) cadast_confere.getObjeto();
            return "<div class=\"container\">\n" +
                    "    <h1 class=\"text-center mt-5\">Você reservou sua locação com sucesso!!</h1>\n" +
                    "    <h2 class=\"text-center\">Não compartilhe sua senha: "+m_locacao.getSenha()+"</h2>\n" +
                    "    <a href=\"/\" class=\"container text-center btn btn-success mt-5\">Retornar para página incial</a>\n" +
                    "</div>";
        }
        return cadast_confere.getMensagem();
    }
    @PostMapping("/getQuartosDisp")
    public String getQuartosDisp(@RequestParam("checkout") LocalDate checkout,
                                 @RequestParam("checkin") LocalDate checkin,
                                 Model model) {
        model.addAttribute("quartos", S_Cadastro.getQuartos(checkin,checkout));
        return "pv/quartosdisp";
    }
}
