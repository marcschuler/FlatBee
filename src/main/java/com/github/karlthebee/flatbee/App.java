package com.github.karlthebee.flatbee;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Starts the demo application
 * @author karlthebee@gmail.com
 * @version 1.0
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent node = (Parent) FXMLLoader.load(getClass().getResource("FlatBeePane.fxml"));
        node.getStylesheets().add(getClass().getResource("FlatBee.css").toString());
        primaryStage.setScene(new Scene(node));
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
