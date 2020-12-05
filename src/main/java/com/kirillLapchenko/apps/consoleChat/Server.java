package com.kirillLapchenko.apps.consoleChat;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public void listenSocket(){
        try {
            ServerSocket server = new ServerSocket(4141);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error listening port 4141");
        }
    }
}
