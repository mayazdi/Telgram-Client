package com.Telgram;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./View/Login.fxml"));
        primaryStage.setTitle("Telgram");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Asset/Icon/telgram.png")));
        primaryStage.setScene(new Scene(root, 1800, 1100));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
