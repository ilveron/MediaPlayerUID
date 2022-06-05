package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SceneHandler {
    //VARIABLES
    private Scene scene;
    private Stage stage;
    private String theme = "dark";
    private Pane subScene;
    private SimpleStringProperty currentMidPane = new SimpleStringProperty("home-view.fxml"); //aggiunto per switchautomatico

    //SINGLETON
    private static SceneHandler instance = null;
    private SceneHandler(){    }
    public static SceneHandler getInstance(){
        if (instance==null)
            instance = new SceneHandler();
        return instance;
    }
    //END SINGLETON

    //GETTERS AND SETTERS
    public Stage getStage() {
        return stage;
    }

    public String getCurrentMidPane() {
        return currentMidPane.get();
    }
    public SimpleStringProperty currentMidPaneProperty() {
        return currentMidPane;
    }
    public void setCurrentMidPane(String currentMidPane) {
        this.currentMidPane.set(currentMidPane);
    }
    //END GETTERS AND SETTERS


    //START MAIN STAGE AND SCENE
    public void init(Stage mainStage) throws Exception {
        stage = mainStage;
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        scene = new Scene(loader.load(), 1200, 800);
        stage.setTitle("MediaPlayer UID");
        stage.setMinHeight(600);
        stage.setMinWidth(750);
        /*for (String font : List.of("fonts/Roboto/Roboto-Regular.ttf", "fonts/Roboto/Roboto-Bold.ttf")) {
            Font.loadFont(Objects.requireNonNull(MainApplication.class.getResource(font)).toExternalForm(), 10);
            //Font.loadFont((MainApplication.class.getResource(font)).toExternalForm(), 10);

        }*/
        for (String style : List.of("css/"+theme+".css", /*"css/fonts.css",*/ "css/style.css")) {
            String resource = Objects.requireNonNull(MainApplication.class.getResource(style)).toExternalForm();
            scene.getStylesheets().add(resource);
        }
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    //SWITCH PANE SEATED IN THE MIDDLE OF THE BORDERPANE
    // TODO: 05/06/2022 RIVEDERE BENE SOTTO
    public Pane switchPane(){
        try{
            subScene = new FXMLLoader().load(MainApplication.class.getResource(currentMidPane.get()));
        }catch(IOException e){}

        return subScene;

    }

}
