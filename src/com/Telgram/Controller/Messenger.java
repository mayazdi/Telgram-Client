package com.Telgram.Controller;


import com.Telgram.Model.*;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ContactItem {
    public ContactItem(String s) {
        Content=s;
    }

    String Content;

    @Override
    public String toString() {
        return Content;
    }
}

public class Messenger {
    public StackPane connection;
    Tools tools = Tools.getTools();

    public ImageView infoimage;
    public Label infouser;
    public Label infoname;
    public Label infobio;
    public StackPane infopane;
    public StackPane morepane;
    public ListView chat;
    public Label yourname;
    public JFXTextField search_fld;
    public ListView<ContactItem> Contacts;
    public ImageView pro_pic;
    public Label contact;
    public JFXTextField message;


    public void FetchContacts() throws Exception {
        HashMap<String, String> value = new HashMap<>();
        value.put("From", Tools.getThisUser());
        API api = new API(Action.FETCH_CONTACTS, value);
        Thread signInRequest = new Thread(api);
        signInRequest.start();
        signInRequest.join();
        System.out.println(api.inbox.value);
        String[] ctcs = api.inbox.value.values().toArray(new String[api.inbox.value.size()]);
        ObservableList observableList = FXCollections.observableArrayList();
        for (int i = 0; i < ctcs.length; i++) {
            if (ctcs[i].equals("Server") || ctcs[i].equals(Tools.getThisUser()))
                continue;
            observableList.add(ctcs[i]);
        }
        Contacts.setItems(observableList);
    }


    ObservableList<ContactItem> observableList = FXCollections.observableArrayList();
    ObservableList<String> pmList = FXCollections.observableArrayList();
    ScheduledExecutorService backgroundrefresh = Executors.newSingleThreadScheduledExecutor();

    public void Refresh(Object newValue){
        pmList.clear();
        contact.setText(newValue.toString());
        HashSet<PM> pms = Tools.userChatLog.get(newValue.toString());

        for (PM p : pms) {
            if (p.getIncoming()) {
                pmList.add(newValue.toString() + ": " + p.getContent() + "\t" + " @" + p.getDate());
            } else {
                pmList.add("You: " + p.getContent() + "\t" + " @" + p.getDate());
            }
        }
        chat.setItems(pmList);
    }


    @FXML
    public void initialize() {
        yourname.setText(Tools.getThisUser());
        connection.setVisible(false);
        morepane.setVisible(false);
        infopane.setVisible(false);

        Platform.runLater(()->{
            Contacts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    Refresh(newValue);
                }
            });
        });


        try {
            FetchContacts();
        } catch (Exception e) {
            System.out.println("Fetching Contacts faced Problem");
        }

        BackgroundRefresh brRunner = new BackgroundRefresh();
        backgroundrefresh.scheduleWithFixedDelay(brRunner, 1000, 2000, TimeUnit.MILLISECONDS);


        search_fld.textProperty().addListener(new ChangeListener<String>() {
            /**
             * Handling SearchTextField changes which tries to
             * find Usernames or Names of users existing in servers database
             * @param ov oldValue
             * @param t
             * @param t1 newValue
             */
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!search_fld.getText().equals("")) {
                    HashMap<String, String> value = new HashMap<>();
                    value.put("From", Tools.getThisUser());
                    value.put("To", "Server");
                    value.put("SearchKey", t1);

                    API api = new API(Action.SEARCH, value);
                    Thread signInRequest = new Thread(api);
                    signInRequest.start();
                    try {
                        signInRequest.join();
                    } catch (InterruptedException e) {
                        /*e.printStackTrace();*/
                    }
                    System.out.println(api.inbox.value);
                    if (api.inbox.value != null) {
                        ObservableList observableList = FXCollections.observableArrayList();

                        String[] ctcs = api.inbox.value.values().toArray(new String[api.inbox.value.size()]);
                        for (String s : ctcs) {
                            if ((s.equals("Server")) || (s.equals(Tools.getThisUser())))
                                continue;
                            observableList.add(s);

                        }
                        Contacts.setItems(observableList);
                    }
                    api.inbox = null;
                } else {
                    try {
                        FetchContacts();
                    } catch (Exception e) {
                        System.out.println("Fetching Contacts faced Problem");
                    }
                }
            }
        });


        /*Contacts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Refresh(newValue);
            }
        });*/


    }


    public void File(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your file:");
        File file = fileChooser.showOpenDialog((Stage)Contacts.getScene().getWindow());
        if (file != null){
            if (Tools.ServerIsOnline){

            }
        }

    }

    public void Send() {
        if (Tools.ServerIsOnline) {
            connection.setVisible(false);
            if ((!message.getText().isEmpty()) && (!contact.getText().contains(" "))) {

                HashMap<String, String> value = new HashMap<>();
                value.put("From", Tools.getThisUser());
                value.put("To", contact.getText());
                value.put("Date", myDate.getDate());
                value.put("Message", message.getText());

                //---------------------------------------------------------------------------------------------
                API api = new API(Action.MESSAGE, value);
                Thread signInRequest = new Thread(api);
                signInRequest.start();
                try {
                    signInRequest.join();
                } catch (InterruptedException e) {
                    /*e.printStackTrace();*/
                }

                System.out.println(api.inbox.value);

                if (api.inbox.value.containsKey("Status")) {
                    if (api.inbox.value.get("Status").equals("Sent")) {
                        System.out.println("Message Sent!");
                        /*list.scrollTo(c);*/
                        /*chat.*/
                        if (api.inbox.value.get("To").equals(Tools.getThisUser())) {
                            pmList.add("You:" + value.get("Message"));
                            chat.setItems(pmList);
                            //Az right
                        } else pmList.add("OON:" + value.get("Message"));//Az left
                        chat.scrollTo(value.get("Message"));
                    }
                    api.inbox = null;
                }
                //---------------------------------------------------------------------------------------------


                System.out.println(value);
                /*System.out.println("Sent: "+message.getText());*/
                message.setText("");
            }
        } else {
            connection.setVisible(true);
        }
    }

    public void Send(MouseEvent mouseEvent) {
        Send();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER"))
            Send();
    }

    public void SearchSubmit(KeyEvent keyEvent) {

    }

    public void More(MouseEvent mouseEvent) {
        morepane.setVisible(true);
    }

    public void serachButton(MouseEvent mouseEvent) {

    }

    public void GoAddFriend(MouseEvent mouseEvent) {
        System.out.println("GoAddFriend");
    }

    public void GoAddGroup(MouseEvent mouseEvent) {
        HashMap<String, String> values = new HashMap<>();
        values.put("From", contact.getText());
        values.put("To", "Server");
        values.put("Group", "group"+String.valueOf(Math.random()));
        values.put("Member", contact.getText());
        API api = new API(Action.ADD_CONTACT, values);
        Thread signInRequest = new Thread(api);
        signInRequest.start();
        try {
            signInRequest.join();
        } catch (InterruptedException e) {
            /*e.printStackTrace();*/
        }
        System.out.println("GoAddGroup");
    }

    public void GoSettings(MouseEvent mouseEvent) {
        System.out.println("GoSetting");
        morepane.setVisible(false);
        tools.changePage("Settings", "Settings", mouseEvent);
    }

    public void Exit(MouseEvent mouseEvent) {
        backgroundrefresh.shutdown();
        Stage currentStage = (Stage) yourname.getScene().getWindow();
        currentStage.close();
        System.out.println("Exit");
    }

    public void Back(MouseEvent mouseEvent) {
        morepane.setVisible(false);
    }

    public void userBack(MouseEvent mouseEvent) {
        infopane.setVisible(false);
    }

    public void infoAdd(ActionEvent actionEvent) {
        HashMap<String, String> values = new HashMap<>();
        values.put("From", Tools.getThisUser());
        values.put("To", "Server");
        values.put("Contact", contact.getText());
        API api = new API(Action.ADD_CONTACT, values);
        Thread addContact = new Thread(api);
        addContact.start();
        try {
            addContact.join();
        } catch (InterruptedException e) {
            /*e.printStackTrace();*/
        }

    }

    public void goUserInfo(MouseEvent mouseEvent) {
        if (!contact.getText().contains("Pick a")) {
            infopane.setVisible(true);
            infouser.setText("@" + contact.getText());

            HashMap<String, String> values = new HashMap<>();
            values.put("From", contact.getText());
            values.put("To", "Server");
            values.put("Fetch", "Values");
            API api = new API(Action.EDIT_USER, values);
            Thread signInRequest = new Thread(api);
            signInRequest.start();
            try {
                signInRequest.join();
            } catch (InterruptedException e) {
                /*e.printStackTrace();*/
            }
            infoname.setText(api.inbox.value.get("Name"));
            infobio.setText(api.inbox.value.get("Bio"));

        }
    }

}
