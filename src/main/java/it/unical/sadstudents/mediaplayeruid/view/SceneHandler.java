package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.Settings;
import it.unical.sadstudents.mediaplayeruid.model.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.awt.desktop.AboutEvent;
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
    private SimpleBooleanProperty switchingCurrentMidPane = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty infoMediaPropertyHover = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty fullScreenRequested = new SimpleBooleanProperty(false);

    //SAVING PROCESS DATA
    private double numberOfData=0;
    private double numberOfDataProcessed=0;
    private SimpleBooleanProperty canStartSaving = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty finished = new SimpleBooleanProperty(false);


    private SimpleStringProperty currentMidPane = new SimpleStringProperty("home-view.fxml");//aggiunto per switchautomatico
    private SimpleBooleanProperty requestedVideoView = new SimpleBooleanProperty(false);

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


    public boolean isCanStartSaving() {
        return canStartSaving.get();
    }

    public SimpleBooleanProperty canStartSavingProperty() {
        return canStartSaving;
    }

    public void setCanStartSaving(boolean canStartSaving) {
        this.canStartSaving.set(canStartSaving);
    }

    public double getNumberOfData() {
        return numberOfData;
    }

    public void setNumberOfData(double numberOfData) {
        this.numberOfData = numberOfData;
    }

    public double getNumberOfDataProcessed() {
        return numberOfDataProcessed;
    }

    public void setNumberOfDataProcessed(double numberOfDataProcessed) {
        this.numberOfDataProcessed = numberOfDataProcessed;
    }

    public boolean isFullScreenRequested() {
        return fullScreenRequested.get();
    }

    public void setFullScreenRequested(boolean fullScreenRequested) {
        this.fullScreenRequested.set(fullScreenRequested);
    }

    public SimpleBooleanProperty fullScreenRequestedProperty() {
        return fullScreenRequested;
    }

    public boolean isInfoMediaPropertyHover() {
        return infoMediaPropertyHover.get();
    }

    public SimpleBooleanProperty infoMediaPropertyHoverProperty() {
        return infoMediaPropertyHover;
    }

    public void setInfoMediaPropertyHover(boolean infoMediaPropertyHover) {
        this.infoMediaPropertyHover.set(infoMediaPropertyHover);
    }

    public boolean isSwitchingCurrentMidPane() {
        return switchingCurrentMidPane.get();
    }

    public SimpleBooleanProperty switchingCurrentMidPaneProperty() {
        return switchingCurrentMidPane;
    }

    public void setSwitchingCurrentMidPane(boolean switchingCurrentMidPane) {
        this.switchingCurrentMidPane.set(switchingCurrentMidPane);
    }

    public boolean isRequestedVideoView() {
        return requestedVideoView.get();
    }

    public SimpleBooleanProperty requestedVideoViewProperty() {
        return requestedVideoView;
    }

    public void setRequestedVideoView(boolean requestedVideoView) {
        this.requestedVideoView.set(requestedVideoView);
    }

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
        DatabaseManager.getInstance().createTableEqualizer();
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

                try {
                    if(Player.getInstance().getMediaPlayer()!=null)
                        Player.getInstance().stop();

                    scene= new Scene((new FXMLLoader(MainApplication.class.getResource("exit-view.fxml"))).load(),500,180);
                    stage.hide();
                    stage = new Stage();
                    //stage.initStyle();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        if(!SceneHandler.getInstance().showConfirmationAlert("Do you really want to close?"+System.lineSeparator()+ "Data will probably be corrupted or will not be entirely saved.")){
                            windowEvent.consume();
                            stage.show();
                        }else{exit();}
                    }
                });



                if(mediaLoadingInProgess.get()){
                    if(Player.getInstance().getMediaPlayer()!=null)
                        Player.getInstance().stop();

                    SceneHandler.getInstance().mediaLoadingInProgessProperty().addListener(observable -> {
                        canStartSaving.set(true);
                    });

                }
                else{
                    save();
                }

                SceneHandler.getInstance().canStartSavingProperty().addListener(observable -> save());



               /* numberOfData=PlayQueue.getInstance().getQueue().size();

                //DatabaseManager.getInstance().changeApplicationClosureData();
                DatabaseManager.getInstance().deleteAll("Playqueue");
                for(int i=0;i<numberOfData;i++) {
                    DatabaseManager.getInstance().insertPlayQueue(PlayQueue.getInstance().getQueue().get(i).getPath(),i);
                    numberOfDataProcessed++;
                }
                if(!mediaLoadingInProgess.get())
                    exit();
                SceneHandler.getInstance().mediaLoadingInProgessProperty().addListener(observable -> {
                    if(numberOfDataProcessed==numberOfData)
                        exit();
                    else if()
                });*/
                /*
                for(MyMedia myMedia: PlayQueue.getInstance().getQueue()) {
                    DatabaseManager.getInstance().insertPlayQueue(myMedia.getPath());
                }*/

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

        DatabaseManager.getInstance().initEqualizer();
        DatabaseManager.getInstance().getEqualizer();

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

    public void resizeAnchorPaneTransition(AnchorPane anchorPane, int startX, int startY, int stopX, int stopY, double secondsOfTransition){
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(anchorPane.maxWidthProperty(), startX, Interpolator.EASE_BOTH),
                        new KeyValue(anchorPane.maxHeightProperty(), startY, Interpolator.EASE_BOTH)),

                new KeyFrame(Duration.seconds(secondsOfTransition),
                        new KeyValue(anchorPane.maxWidthProperty(), stopX, Interpolator.EASE_BOTH),
                        new KeyValue(anchorPane.maxHeightProperty(), stopY, Interpolator.EASE_BOTH)));

        timeline.play();
    }

    public void scaleTransition(Node node){
        ScaleTransition onEnter = new ScaleTransition(new Duration(40.0), node);
        onEnter.setToX(1.10);   onEnter.setToY(1.10);

        ScaleTransition onExit = new ScaleTransition(new Duration(40.0), node);
        onExit.setToX(1);   onExit.setToY(1);

        node.setOnMouseEntered(mouseEvent -> onEnter.play());
        node.setOnMouseExited(mouseEvent -> onExit.play());
    }

    private void save(){
        numberOfData=PlayQueue.getInstance().getQueue().size();
        //DatabaseManager.getInstance().changeApplicationClosureData();
        DatabaseManager.getInstance().deleteAll("Playqueue");
        for (int i=0;i<numberOfData; i++){
            DatabaseManager.getInstance().insertPlayQueue(PlayQueue.getInstance().getQueue().get(i).getPath(),i);
            numberOfDataProcessed++;

        }


        exit();
    }



    private void exit(){
        DatabaseManager.getInstance().disconnect();
        Platform.exit();
        System.exit(0);
    }

    public TranslateTransition translateTransition(Node node, double startX, double startY, double stopX, double stopY, Interpolator interpolator, double secondsOfTransition){
        TranslateTransition translate = new TranslateTransition(Duration.seconds(secondsOfTransition));
        translate.setNode(node);
        translate.setFromX(startX);
        translate.setFromY(startY);
        translate.setToX(stopX);
        translate.setToY(stopY);
        translate.setInterpolator(interpolator);

        return translate;
    }

}
