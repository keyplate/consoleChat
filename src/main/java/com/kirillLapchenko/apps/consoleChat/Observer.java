package com.kirillLapchenko.apps.consoleChat;

public interface Observer {

    public ClientWorker getClientWorkerToConnect(int id);

    public void newChatRoomCreated(ChatRoom room);
}
