package com.kirillLapchenko.apps.consoleChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        Server serv = new Server();
        Thread t = new Thread(serv);
        t.start();

    }
}
