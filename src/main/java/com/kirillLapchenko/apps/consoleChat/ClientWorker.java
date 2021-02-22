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
    ChatRoom chatRoom;

    //Constructor
    ClientWorker(Socket client, int clientId, Observer server) throws IOException {
        this.clientSocket = client;
        CLIENT_ID = clientId;
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.server = server;
        writer.println("Welcome to the anonymous chatroom, your id is " + clientId);
        writer.flush();
    }

    @Override
    public void run() {
        while (true) {
            try {
                lastInput = reader.readLine();
                if(!isConnectionRequired)
                    isConnectionCalled();
                if(isConnectionRequired && !isConnected)
                    getConnection();
                if(isConnected)
                    chatRoom.sendMessage(this, lastInput);
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

    public void getConnection(){
        try {
            int clientNum = clientIdToConnect();
            server.connectSubscribers(this, clientNum);
        } catch (Exception e) {
            throw new RuntimeException("Client doesn't require connection yet");
        }
    }

    public int clientIdToConnect() throws Exception {
        if (isConnectionRequired && !isConnected) {
            String[] s = lastInput.split(" ");
            return Integer.parseInt(s[1]);
        }
        throw new Exception("Client doesn't require connection yet");
    }

    public void receiveMessage(String message){
        writer.println(message);
        writer.flush();
    }

    public void joinChat(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
        changeConnectionState();
    }

    public void changeConnectionState(){
        isConnected = !isConnected;
    }

}