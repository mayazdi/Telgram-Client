package com.Telgram.Model;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FileHandler implements Runnable {
    String name;
    File file;
    public FileHandler(String s,File f){
        this.name=s;
        this.file=f;
    }
    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", Tools.filePort);
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            //Send File and har chi ke hast
            /*outputStream.writeObject(this.file);
            System.out.println("Met here");*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
