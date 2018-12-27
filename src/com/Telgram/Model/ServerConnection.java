package com.Telgram.Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection implements Runnable {

    Tools tools = Tools.getTools();

    @Override
    public void run() {

        try (Socket socket = new Socket("localhost", 8080);
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            String s = "hello";

            outputStream.writeObject(s);
            String input = (String) inputStream.readObject();
            if (!input.equals("hello")) {

                Tools.ServerIsOnline = false;
            }
            Tools.ServerIsOnline = true;
        } catch (Exception e) {
            Tools.ServerIsOnline = false;
        }
    }
}
