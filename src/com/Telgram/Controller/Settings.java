package com.Telgram.Controller;

import com.Telgram.Model.API;
import com.Telgram.Model.Action;
import com.Telgram.Model.Tools;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;


public class Settings {
    public JFXTextField name_fld;
    public JFXTextField username_fld;
    public JFXTextField bio_fld;
    public JFXPasswordField password_fld;

    public void fetchInformation() {
        HashMap<String, String> values = new HashMap<>();
        values.put("From", Tools.getThisUser());
        values.put("To", "Server");
        values.put("Fetch","Values");
        API api = new API(Action.EDIT_USER, values);
        Thread signInRequest = new Thread(api);
        signInRequest.start();
        try {
            signInRequest.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        name_fld.setText(api.inbox.value.get("Name"));
        username_fld.setText(api.inbox.value.get("Username"));
        bio_fld.setText(api.inbox.value.get("Bio"));
        password_fld.setText(api.inbox.value.get("Password"));

    }

    public void changeRequest(String field, String value) {
        HashMap<String, String> values = new HashMap<>();
        values.put("From", Tools.getThisUser());
        values.put("To", "Server");
        values.put(field, value);
        API api = new API(Action.EDIT_USER, values);
        Thread signInRequest = new Thread(api);
        signInRequest.start();
        try {
            signInRequest.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(api.inbox.value);
        if (api.inbox.value.get("Change").equals("Done"))
            System.out.println(field + " changed successfully");
    }

    public void initialize() {
        fetchInformation();
    }

    public JFXButton signout;
    Tools tools = Tools.getTools();

    public void signOut(ActionEvent actionEvent) {
        Tools.setThisUser(null);
        Tools.userChatLog = null;
        tools.setSingedIn(false);
        tools.changePage("Sign Up", "Login", actionEvent);
    }

    public void Back(MouseEvent mouseEvent) {
        tools.changePage("Telgram", "Messenger", mouseEvent);
    }

    public void nameEntered(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            if (name_fld.getText().length() >= 3)
                changeRequest("Name", name_fld.getText());
            else System.out.println("Not Long enough");
        }
    }

    public void usernameEntered(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            if (username_fld.getText().length() >= 3)
                changeRequest("Username", username_fld.getText());
            else System.out.println("Not Long enough");
        }
    }

    public void bioEntered(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            if (password_fld.getText().length() <= 15)
                changeRequest("Bio", bio_fld.getText());
            else System.out.println("Too Long");
        }
    }

    public void passwordEntered(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            if (password_fld.getText().length() >= 3)
                changeRequest("Password", password_fld.getText());
            else System.out.println("Not Long enough");
        }
    }

    public void picture(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your profile picture:");
        File file = fileChooser.showOpenDialog((Stage)name_fld.getScene().getWindow());
        if (file != null){

        }
    }
}
