package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Api;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class S_Api {

    private final R_Produto r_produto;

    public S_Api(R_Produto r_produto) {
        this.r_produto = r_produto;
    }

    public List<M_Api> apiRequest(String iso) {
        return r_produto.requestApi(LocalDate.parse(iso, DateTimeFormatter.ISO_DATE));
    }
}
