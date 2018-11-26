package com.Telgram.Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;


public class BackgroundRefresh implements Runnable {
    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", 8580);
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            Message message = new Message();
            HashMap <String ,String >value= new HashMap<>();

            value.put("From",Tools.getThisUser());
            value.put("To","Server");
            message.value = value;
            outputStream.writeObject(message);
            HashMap <String ,HashSet<PM>> pmLog= (HashMap<String, HashSet<PM>>) inputStream.readObject();
            Tools.setUserChatLog(pmLog);
            //System.out.println("Refreshed");

            /*if (Tools.userChatLog == pmLog)
                Tools.userChatLog = pmLog;
                *//*return;*//*
            else {
                Tools.userChatLog = pmLog;
                //Radif konam PM Log ro
            }*/
        } catch (Exception e) {
            /*e.printStackTrace();*/
            System.out.println("Client Threw an Exception !");
        }

    }


}
