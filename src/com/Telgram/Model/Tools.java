package com.Telgram.Model;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Tools \n
 * This singleton class is handling reading from and writing  to ...
 */

//Window Size must be set to 1.8K*1.2K
public class Tools {
    public static String serverAddress = "localhost";
    public static int requestPort = 8585;
    public static int messagePort = 8585;
    public static int onlinePort = 8080;
    public static boolean ServerIsOnline = true;
    public static HashMap<String ,HashSet<PM>> userChatLog ;
    private static Tools tools = new Tools();
    private Tools (){ }
    public static Tools getTools () {return tools;}

    public synchronized static void setUserChatLog(HashMap<String, HashSet<PM>> userChatLog) {
        Tools.userChatLog = userChatLog;
    }

    private static String thisUser;
    private static boolean SignedIn=false;
    public static boolean getSingedIn(){return SignedIn;}
    public void setSingedIn(boolean b){ SignedIn=b;}
    public static void setThisUser(String thisUser) {
        Tools.thisUser = thisUser;
    }

    public static String getThisUser() {
        return thisUser;
    }

    /**
     * Calling this function will keep current window open and open the new window.
     *
     * @param title the window title String
     * @param view  the view page's file name
     */
    public void callPage(String title,/* int w, int h, String icon,*/ String view) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/" + view + ".fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root1,900,550/* w, h*/);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../Asset/Icon/" + "telgram" /*icon*/ + ".png")));
            stage.show();
            stage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calling this function will close current window and open the new window instead.
     *
     * @param title the window title String
     * @param view  the view page's file name
     * @param a
     */
    public void changePage(String title,/* int w, int h, String icon, */String view, Event a) {
        Parent blah = null;
        try {
        blah = FXMLLoader.load(getClass().getResource("../View/" + view + ".fxml"));
            Scene scene = new Scene(blah);
            Stage stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../Asset/Icon/" + "telgram" /*icon*/ + ".png")));
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
