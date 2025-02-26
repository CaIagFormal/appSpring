package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Api;
import com.primeira.appSpring.service.S_Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class C_Api {

    private final S_Api s_api;

    public C_Api(S_Api s_api) {
        this.s_api = s_api;
    }

    @GetMapping("/API/{iso}")
    @ResponseBody
    public List<M_Api> getApiRequestByISO(@PathVariable("iso") String iso) {
        return s_api.apiRequest(iso);
    }

    @GetMapping("/API/{dd}/{MM}/{yyyy}")
    @ResponseBody
    public List<M_Api> getApiRequestByDate(@PathVariable("dd") String day,
                                           @PathVariable("MM") String month,
                                           @PathVariable("yyyy") String year) {
        return getApiRequestByISO(year+'-'+month+'-'+day);
    }

}
