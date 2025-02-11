package com.primeira.appSpring.controller;

import com.primeira.appSpring.service.S_Arduino;

public abstract class C_Arduino {
    protected boolean init_success;

    public C_Arduino() {
        this.init_success = false;
    }

    abstract public void initialize();

    abstract public void run();

    public boolean isInit_success() {
        return init_success;
    }
}