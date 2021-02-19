package com.kirillLapchenko.apps.consoleChat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientWorker implements Runnable {
    private Socket clientSocket;
    private final int CLIENT_ID;
    private BufferedReader reader;
    private PrintWriter writer;
    private String lastInput = "";
    private boolean isConnectionRequired = false;
    private boolean isConnected = false;
    private Observer server;
    private ClientWorker subscriber;

    //Constructor
    ClientWorker(Socket client, int clientId, Observer server) throws IOException {
        this.clientSocket = client;
        CLIENT_ID = clientId;
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                lastInput = reader.readLine();
                isConnectionCalled();
                if(isConnectionRequired && !isConnected){
                    getConnection();
                }
                if(isConnected){
                    chat();
                    lastInput = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void isConnectionCalled() throws Exception {
        if (lastInput.startsWith("#CONNECT ")) {
            isConnectionRequired = true;
        }
    }

    private void getConnection() throws Exception {
        subscriber = server.getClientWorkerToConnect(clientIdToConnect());
        isConnected = true;
    }

    public int clientIdToConnect() throws Exception {
        if (isConnectionRequired && !isConnected) {
            String[] s = lastInput.split(" ");
            return Integer.parseInt(s[1]);
        }
        throw new Exception("Client doesn't require connection yet");
    }

    public void chat(){
        if(lastInput != null) {
            subscriber.writer.println(lastInput);
            subscriber.writer.flush();
        }
    }
}