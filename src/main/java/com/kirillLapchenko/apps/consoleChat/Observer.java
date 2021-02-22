package com.kirillLapchenko.apps.consoleChat;

public interface Observer {

    public void connectSubscribers(ClientWorker subscriber, int id);

    public void newChatRoomCreated(ClientWorker cw1, ClientWorker cw2);
}
