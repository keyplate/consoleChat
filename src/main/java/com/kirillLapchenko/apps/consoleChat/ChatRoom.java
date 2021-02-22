package com.kirillLapchenko.apps.consoleChat;

public class ChatRoom{

    ClientWorker subscriber1, subscriber2;

    public ChatRoom(ClientWorker sub1, ClientWorker sub2){
        subscriber1 = sub1;
        subscriber2 = sub2;
        setUpChatRoom();
    }

    private void setUpChatRoom(){
        subscriber2.joinChat(this);
        subscriber1.joinChat(this);
    }

    public void sendMessage(ClientWorker subscriber, String message){
        if(subscriber.equals(subscriber1))
            subscriber2.receiveMessage(message);

        if(subscriber.equals(subscriber2))
            subscriber1.receiveMessage(message);
    }
}
