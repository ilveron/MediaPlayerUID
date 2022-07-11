package it.unical.sadstudents.mediaplayeruid;

import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage mainStage) throws IOException {
        try {
            SceneHandler.getInstance().init(mainStage);
        } catch(Exception e) {
        }

    }

    public static void main(String[] args) {
        launch();
    }
}