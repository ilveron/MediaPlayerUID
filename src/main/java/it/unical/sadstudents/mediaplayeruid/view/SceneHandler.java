package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.Settings;
import it.unical.sadstudents.mediaplayeruid.controller.MainController;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SceneHandler {
    //VARIABLES
    private Scene scene;
    private Stage stage;
    private Pane subScene;
    private String theme = "dark";
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

    public Scene getScene() { return scene; }

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
        //stage.getIcons().add(new Image("file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/logoMediaPlayerUID.png"));
        stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("image/logoMediaPlayerUID-48x48.png")));
        System.setProperty("prism.lcdtext", "false");
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));

        scene = new Scene(loader.load(),1344,756);

        stage.setTitle("MediaPlayer UID");
        stage.setMinHeight(600);
        stage.setMinWidth(806);


        for (String font : Settings.fonts) {
            System.out.println();
            Font.loadFont(Objects.requireNonNull(MainApplication.class.getResourceAsStream(font)), 10);
        }

        //Loads style.css stylesheet
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/"+theme+".css")).toExternalForm());


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

    public void changeTheme() {
        if("dark".equals(theme))
            theme = "unical";
        else
            theme = "dark";
        scene.getStylesheets().clear();

        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource(Settings.style)).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/"+theme+".css")).toExternalForm());
    }

}
