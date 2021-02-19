package com.kirillLapchenko.apps.consoleChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class Server implements Runnable, Observer {
    private BufferedReader in;
    private PrintWriter out;
    private ServerSocketChannel server;
    private ArrayList<ClientWorker> clientList;
    private int clientCount = 0;

    public Server() {
        try {
            server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(4444));
            server.configureBlocking(false);
            clientList = new ArrayList<>();
            System.out.println("Server started!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                acceptClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void acceptClient() throws IOException {
        SocketChannel localSocket = server.accept();
        if (localSocket != null) {
            Socket clientSocket = localSocket.socket();
            ClientWorker w = new ClientWorker(clientSocket, clientCount, this);
            Thread t = new Thread(w);
            t.start();
            clientList.add(w);
            clientCount++;
        }
    }

    @Override
    public ClientWorker getClientWorkerToConnect(int id) {
        ClientWorker temp = clientList.get(id);
        return  temp;
    }

    @Override
    public void newChatRoomCreated(ChatRoom room) {

    }
}