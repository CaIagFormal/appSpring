package com.primeira.appSpring.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class S_Session {
    public boolean has_session(HttpSession session) {
        return session.getAttribute("usuario") == null;
    }
}