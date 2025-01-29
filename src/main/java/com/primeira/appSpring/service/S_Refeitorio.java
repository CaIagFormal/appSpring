package com.primeira.appSpring.service;


import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Refeitorio {

    @Autowired
    private static R_Produto r_produto;

    public S_Refeitorio(R_Produto r_produto) { S_Refeitorio.r_produto = r_produto; }

    public static List<M_Produto> findAll() {
        return r_produto.findAll();
    }

}
