package com.lab3.task3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class View extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL load = getClass().getResource("/mainView.fxml");
        FXMLLoader loader = new FXMLLoader(load);
        AnchorPane mainPane = loader.load();
        Scene scene = new Scene(mainPane, 570, 690);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
