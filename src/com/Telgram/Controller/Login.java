package com.Telgram.Controller;

import com.Telgram.Model.*;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Login {
    public StackPane connection;
    Tools tools = Tools.getTools();
    @FXML
    public JFXPasswordField psd_fld;
    @FXML
    public JFXTextField usr_fld;

    public void initialize(){
        connection.setVisible(false);
        ServerConnection serverConnection = new ServerConnection();
        ScheduledExecutorService serverService = Executors.newSingleThreadScheduledExecutor();
        serverService.scheduleWithFixedDelay(serverConnection, 2000, 3000, TimeUnit.MILLISECONDS);
        /*BackgroundRefresh brRunner = new BackgroundRefresh();
        */
    }

    public void goSignUp(ActionEvent actionEvent) {
        //Open Sign Up Page
        System.out.println("Open Sign Up Page");
        /*Thread api = new Thread(new API());*/
        //t.callPage("Sign Up","SignUp");
        tools.changePage("Sign Up", "SignUp", actionEvent);

        /*api.start();*/

    }

    //                  ^^^^^^^^^^^^^^^^Throws Exception
    public void SignIn(Event event) throws Exception {
        if (Tools.ServerIsOnline){
            connection.setVisible(false);
            HashMap<String, String> value = new HashMap<>();

            value.put("From", usr_fld.getText());
            value.put("to", "Server");
            value.put("Password", psd_fld.getText());
            value.put("Username", usr_fld.getText());
            value.put("Time", myDate.getDate());

            API api = new API(Action.SIGN_IN, value);
            Thread signInRequest = new Thread(api);
            signInRequest.start();
            signInRequest.join();
            System.out.println(api.inbox.value);
            if (api.inbox.value.containsKey("SignIn")) {
                if (api.inbox.value.get("SignIn").equals("Done")) {
                    //Do Sign In
                    System.out.println("Visited");
                    tools.setThisUser(api.inbox.value.get("To"));
                    api.inbox = null;
                    /*tools.setSingedIn(true);*/
                    tools.changePage("Telgram", "Messenger", event);
                }
            }
        }
        else {
            connection.setVisible(true);
        }

    }

    public void signIn(ActionEvent actionEvent) {
        try {
            SignIn(actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                SignIn(keyEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*public Hyperlink*/
}