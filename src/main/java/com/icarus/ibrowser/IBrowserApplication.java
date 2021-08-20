package com.icarus.ibrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class IBrowserApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = loadResources();
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("iBrowser - 百度一下");
        primaryStage.show();
    }

    private Parent loadResources() throws java.io.IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("static/view/home.fxml")));
        root.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("static/css/home.css")).toExternalForm());
        return root;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
