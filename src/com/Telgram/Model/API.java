package com.Telgram.Model;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class API implements Runnable {
    Message message = new Message();
    public Message inbox;

    public API(Action action, HashMap value) {
        message.action = action;
        message.value = value;
    }


    private void contactServer() {
        try (Socket socket = new Socket("localhost", 8585);
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            outputStream.writeObject(message);
            inbox = (Message) inputStream.readObject();
        } catch (Exception e) {
            System.out.println("Client Threw an Exception !");
        }
    }

    @Override
    public void run() {
        contactServer();
    }


}