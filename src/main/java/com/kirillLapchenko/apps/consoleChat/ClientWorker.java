package com.kirillLapchenko.apps.consoleChat;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientWorker extends Thread implements Runnable {
    private Socket clientSocket;
    private JTextArea textArea;
    private Client client;


    //Constructor
    ClientWorker(Socket client, JTextArea textArea) {
        this.clientSocket = client;
        this.textArea = textArea;
    }

    public void run() {
        String line;
        BufferedReader in;
        in = null;
        PrintWriter out = null;
        client = new Client((int) getId());
        try {
            in = new BufferedReader(new
                    InputStreamReader(clientSocket.getInputStream()));
            out = new
                    PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("in or out failed");
            System.exit(-1);
        }
        try {
            if (logIn(in, out))
                out.println("Welcome back!");


            while (true) {
                line = in.readLine();
                out.println(line);
                textArea.append(line);

            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private boolean logIn(BufferedReader reader, PrintWriter printer) throws IOException {
        for (int i = 0; i < 3; i++) {
            String password, login;
            printer.println("Please enter your login");
            login = reader.readLine();
            printer.println("Please enter your password");
            password = reader.readLine();
            if (client.verify(login, password))
                return true;
            printer.println("Incorrect Login or Password");
        }
        return false;
    }
}
