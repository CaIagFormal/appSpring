package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Resposta;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Cadastro;
import com.primeira.appSpring.service.S_Home;
import com.primeira.appSpring.service.S_Refeitorio;
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

    private final S_Refeitorio s_refeitorio;

    public C_Cadastro(S_Refeitorio s_refeitorio) {
        this.s_refeitorio = s_refeitorio;
    }

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

    @GetMapping("/refeitorio")
    public String getRefeitorio() {
        return "redirect:/";
    }

    @PostMapping("/refeitorio")
    public String postRefeitorio(HttpSession session,Model model, @RequestParam("id") String id) {
        if (session.getAttribute("usuario") ==null) {
            return "cadastro/cadastro";
        }
        long l_id = Long.parseLong(id);
        M_Locacao m_locacao = S_Home.getLocacaoById(l_id);
        if (m_locacao==null) {
            return "redirect:/";
        }
        session.setAttribute("locacao",m_locacao);

        model.addAttribute("produtos", s_refeitorio.findAll());
        return "refeitorio/cadastraritens";
    }

    @PostMapping("/incluir_itens")
    public String incluirItens(HttpSession session, @RequestParam("itens") String itens) {
        M_Locacao m_locacao = (M_Locacao) session.getAttribute("locacao");
        if (m_locacao == null) {
            return "redirect:/";
        }
        long[][] l_itens = s_refeitorio.gerarItens(itens);
        s_refeitorio.incluirItens(l_itens,m_locacao);
        return "redirect:/";
    }
}