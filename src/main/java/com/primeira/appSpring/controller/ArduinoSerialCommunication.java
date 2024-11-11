package com.primeira.appSpring.controller;
import com.fazecast.jSerialComm.*;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.service.S_Arduino;

import java.io.*;
import java.sql.Date;

public class ArduinoSerialCommunication {


    public ArduinoSerialCommunication() {
    }
    private SerialPort serialPort;

    public void initialize() {
        SerialPort[] ports = SerialPort.getCommPorts();

        if (ports.length == 0) {
            System.out.println("Nenhuma porta serial encontrada.");
            return;
        }

        serialPort = ports[0]; // Use a primeira porta serial encontrada, você pode ajustar isso conforme necessário

        if (!serialPort.openPort()) {
            System.out.println("Falha ao abrir a porta serial.");
            return;
        }

        serialPort.setComPortParameters(
                9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 10000, 0);


        BufferedReader input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        OutputStream output = serialPort.getOutputStream();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (input.ready()) {
                    String inputLine = input.readLine();
                    System.out.println("Dados recebidos: " + inputLine.trim());

                    output.write(S_Arduino.valida_senha(inputLine.trim()));

                    /*
                        InputStream pcm = new FileInputStream("C:\Users\Aluno\IdeaProjects\santissimamariameajuda\src\main\java\com\santissimamariameajuda\pcm.raw");
                        for (int i=0; i<418; i++) {
                            // Process the read data
                            output.write(pcm.readNBytes(10));
                        }
                        pcm.close();

                         */
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}