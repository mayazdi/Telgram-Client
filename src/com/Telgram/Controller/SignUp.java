package com.Telgram.Controller;

import com.Telgram.Model.*;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SignUp {

    Tools tools = Tools.getTools();

    public JFXPasswordField password_fld;
    public JFXTextField username_fld;
    public JFXTextField name_fld;
    public CheckBox gender;


    public void signUp(ActionEvent actionEvent) throws Exception {
        HashMap<String, String> information = new HashMap<>();
        information.put("From", username_fld.getText());
        information.put("To", "Server");
        information.put("Username", username_fld.getText());
        information.put("Password", password_fld.getText());
        information.put("Gender", Boolean.toString(gender.isSelected()));
        information.put("Name", name_fld.getText());
        information.put("Date", myDate.getDate());
        API api = new API(Action.SIGN_UP, information);
        Thread signUp = new Thread(api);
        signUp.start();
        signUp.join();
        System.out.println(api.inbox.value);

        if (api.inbox.value.containsKey("SignUp")) {
            if (api.inbox.value.get("SignUp").equals("Done")) {
                Tools.setThisUser(api.inbox.value.get("To"));
                api.inbox = null;
                tools.changePage("Telgram", "Messenger", actionEvent);
            }

        }

    }

    public void back(MouseEvent mouseEvent) {
        tools.changePage("Telgram Login", "Login", mouseEvent);
    }

    public void PickPicture(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpeg", "*.jpeg"),
                new FileChooser.ExtensionFilter("jpg", "*.jpg"),
                new FileChooser.ExtensionFilter("png", "*.png")
        );
        /*File image = fileChooser.showOpenDialog(null);
        FileHandler fileHandler = new FileHandler(username_fld.getText(),image);
        ScheduledExecutorService backgroundrefresh = Executors.newSingleThreadScheduledExecutor();*/

    }
}