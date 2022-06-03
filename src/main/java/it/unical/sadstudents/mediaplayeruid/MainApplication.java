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
            e.printStackTrace();
        }
    /*
    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("MediaPlayer UID");
        stage.setMinHeight(600);
        stage.setMinWidth(750);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
                System.exit(0);
            }
        });*/
    }

    public static void main(String[] args) {
        launch();
    }
}