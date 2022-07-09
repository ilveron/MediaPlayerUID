package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.Settings;
import it.unical.sadstudents.mediaplayeruid.model.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Objects;

public class SceneHandler {
    //VARIABLES
    private Scene scene;
    private Stage stage;
    private Pane subScene;
    private Alert alert;
    private boolean loadingFromDB =true;
    private SimpleBooleanProperty mediaLoadingInProgess = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty metadataLoadindagInProgess = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty startingMediaPlayer = new SimpleBooleanProperty(true);


    private SimpleStringProperty currentMidPane = new SimpleStringProperty("home-view.fxml"); //aggiunto per switchautomatico

    //SINGLETON
    private static SceneHandler instance = null;
    private SceneHandler(){ alert = new Alert(Alert.AlertType.NONE);   }
    public static SceneHandler getInstance(){
        if (instance==null)
            instance = new SceneHandler();
        return instance;
    }
    //END SINGLETON

    //GETTERS AND SETTERS


    public boolean isLoadingFromDB() {
        return loadingFromDB;
    }

    public void setLoadingFromDB(boolean loadingFromDB) {
        this.loadingFromDB = loadingFromDB;
    }

    public boolean isMetadataLoadindagInProgess() {
        return metadataLoadindagInProgess.get();
    }

    public SimpleBooleanProperty metadataLoadindagInProgessProperty() {
        return metadataLoadindagInProgess;
    }

    public void setMetadataLoadindagInProgess(boolean metadataLoadindagInProgess) {
        this.metadataLoadindagInProgess.set(metadataLoadindagInProgess);
    }

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

    public boolean getMediaLoadingInProgess() {
        return mediaLoadingInProgess.get();
    }

    public SimpleBooleanProperty mediaLoadingInProgessProperty() {
        return mediaLoadingInProgess;
    }

    public void setMediaLoadingInProgess(boolean mediaLoadingInProgess) {
        this.mediaLoadingInProgess.set(mediaLoadingInProgess);
    }

    //END GETTERS AND SETTERS


    //START MAIN STAGE AND SCENE
    public void init(Stage mainStage) throws Exception {
        DatabaseManager.getInstance().createTableMyMedia();
        DatabaseManager.getInstance().createTableRecentMedia();
        DatabaseManager.getInstance().createTablePlaylist();
        DatabaseManager.getInstance().createTableMediaMyMediaPlaylist();
        DatabaseManager.getInstance().createTablePlayqueue();
        //DatabaseManager.getInstance().createTableApplicationClosureData();

        stage = mainStage;
        //stage.getIcons().add(new Image("file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/logoMediaPlayerUID.png"));
        stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("image/logoMediaPlayerUID-48x48.png")));
        //System.setProperty("prism.lcdtext", "false");
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));


        scene = new Scene(loader.load(),1344,756);

        stage.setTitle("MediaPlayer UID");
        stage.setMinHeight(655);
        stage.setMinWidth(880);


        for (String font : Settings.fonts) {
            System.out.println();
            Font.loadFont(Objects.requireNonNull(MainApplication.class.getResourceAsStream(font)), 10);
        }

        //Loads style.css stylesheet
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/"+Settings.theme+".css")).toExternalForm());


        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                //DatabaseManager.getInstance().changeApplicationClosureData();
                for(MyMedia myMedia: PlayQueue.getInstance().getQueue()) {
                    DatabaseManager.getInstance().insertPlayQueue(myMedia.getPath());
                }
                DatabaseManager.getInstance().disconnect();
                Platform.exit();
                System.exit(0);
                // TODO: 05/07/2022 modificare con thread pool


            }
        });

        //DatabaseManager.getInstance().initApplicationClosureData();
        DatabaseManager.getInstance().receiveMyMedia("MusicLibrary");
        DatabaseManager.getInstance().receiveMyMedia("VideoLibrary");

        DatabaseManager.getInstance().receivePlayqueue();
        //DatabaseManager.getInstance().receiveApplicationClosureData();

        DatabaseManager.getInstance().receiveRecentMedia();
        HomeTilePaneHandler.getInstance().listCreator();

        DatabaseManager.getInstance().receivePlaylist();
        for(Playlist s: PlaylistCollection.getInstance().getPlayListsCollections())
            DatabaseManager.getInstance().receiveMediaInPlaylist(s.getName());


        VideoGalleryTilePaneHandler.getInstance().listCreator();



    }

    //SWITCH PANE SEATED IN THE MIDDLE OF THE BORDERPANE
    // TODO: 05/06/2022 RIVEDERE BENE SOTTO
    public Pane switchPane(){
        try{
            subScene = new FXMLLoader().load(MainApplication.class.getResource(currentMidPane.get()));
        }catch(IOException e){}

        return subScene;

    }

    public void changeTheme(String theme) {
        if(!theme.equals(Settings.theme)){
            Settings.theme = theme;
            scene.getStylesheets().clear();

            scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource(Settings.style)).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/"+Settings.theme+".css")).toExternalForm());
        }
        //else
            // TODO: 15/06/2022 Mostrare "Theme already applied"


    }

    public boolean showConfirmationAlert(String text) {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma");
        alert.setHeaderText(text);
        alert.setContentText(null);
        alert.showAndWait();
        return alert.getResult() != ButtonType.CANCEL;
    }
    public void createErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setContentText(message);
        alert.show();
    }

}
