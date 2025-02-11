package com.primeira.appSpring.controller;

import com.fazecast.jSerialComm.SerialPort;
import com.primeira.appSpring.service.S_Arduino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;


public class C_SerialArduino extends C_Arduino {

    private SerialPort serialPort;
    private BufferedReader input;
    private OutputStream output;

    public C_SerialArduino() {
        super();
    }


    public void initialize() {
        SerialPort[] ports = SerialPort.getCommPorts();

        if (ports.length==0) {
            System.out.println("Nenhum porte foi encontrado.");
            return;
        }

        ports = this.removeClosed(ports);

        if (ports.length==0) {
            System.out.println("Todos os portes encontrados estão fechados.");
            return;
        }
        if (ports.length>1) {
            this.serialPort = choosePort(ports);
        } else {
            this.serialPort = ports[0];
        }

        this.serialPort.setComPortParameters(
                9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

        this.serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 10000, 0);

        this.input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        this.output = serialPort.getOutputStream();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.init_success = true;
    }


    public void run() {
        while (true) {
            try {
                if (input.ready()) {
                    String inputLine = input.readLine();
                    output.write(S_Arduino.valida_senha(inputLine.trim()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private SerialPort choosePort(SerialPort[] ports) {
        Scanner scanner = new Scanner(System.in);
        int val;
        System.out.println("Múltiplas portas detectados, escolha um porta de 1 a "+ports.length+".");
        while (true) {
            System.out.print("Porte: ");
            val = scanner.nextInt();
            if (ports.length>val&&val>0) {
                break;
            }
            System.out.println("Porta inválido");
        }
        return ports[val];
    }


    private SerialPort[] removeClosed(SerialPort[] ports) {
        boolean[] open = new boolean[ports.length];
        int ammount = 0;
        for (int i = 0; i < ports.length; i++) {
            open[i] = ports[i].openPort();
            if (open[i]) {ammount++;}
        }
        SerialPort[] new_ports = new SerialPort[ammount];
        int n = 0;
        for (int i = 0; i < ports.length; i++) {
            if (!open[i]) {continue;}
            new_ports[n] = ports[i];
            n++;
        }
        return new_ports;
    }
}
