package com.horoscope.horoscopeapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainClass extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("horoscope-view.fxml"));
        Parent root = fxmlLoader.load();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(root);
        Image backgroundImage = new Image(getClass().getResourceAsStream("/img/backgroundIMG.jpg"));

        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        borderPane.setBackground(new Background(background));

        Screen screen = Screen.getPrimary();
        Scene scene = new Scene(borderPane, screen.getBounds().getWidth() , screen.getBounds().getHeight());

        stage.setTitle("Your daily horoscope");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}