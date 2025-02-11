package com.primeira.appSpring.controller;

import com.primeira.appSpring.service.S_Arduino;

import java.util.Scanner;

public class C_EmuArduino extends C_Arduino{

    private Scanner scanner;

    public C_EmuArduino() {
        super();
    }
    public void initialize() {
        super.init_success = true;
        scanner = new Scanner(System.in);
        System.out.println("AVISO: EMULADOR ATIVO\nUSE O TERMINAL PARA INSERIR SENHAS DE LOCAÇÕES");
    }

    public void run() {
        while (true) {
            System.out.print("Senha: ");
            if (S_Arduino.valida_senha(scanner.nextLine().trim())=='1') {
                System.out.println("OK!!");
                continue;
            }
            System.out.println("Errado");
        }
    }
}
