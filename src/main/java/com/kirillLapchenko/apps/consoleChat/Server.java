package com.kirillLapchenko.apps.consoleChat;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private BufferedReader in;
    private PrintWriter out;
    private ServerSocket server;
    private JTextArea textArea = new JTextArea();


    public Server() {
        listenSocket();
    }

    public void listenSocket() {
        try {
            server = new ServerSocket(4444);
            System.out.println("Server created ");
        } catch (IOException e) {
            System.out.println("Could not listen on port 4444");
            System.exit(-1);
        }
        while (true) {
            ClientWorker w;
            try {
                w = new ClientWorker(server.accept(), textArea);
                Thread t = new Thread(w);
                t.start();
            } catch (IOException e) {
                System.out.println("Accept failed: 4444");
                System.exit(-1);
            }
        }
    }
}
