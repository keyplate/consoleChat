package com.kirillLapchenko.apps.consoleChat;

public class Client {
    private String password = "111";
    private String logName = "111";
    private int clientId;

    public Client(int id){

    }

    boolean verify(String name, String password){
        if(name.equals(logName) && password.equals(password)){
            return true;
        }
        return false;
    }
}
