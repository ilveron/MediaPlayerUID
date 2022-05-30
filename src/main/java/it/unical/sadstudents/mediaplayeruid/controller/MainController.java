package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.DatabaseManager;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.view.MiddlePaneHandler;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainController implements Initializable {
    private Timer timer;
    private TimerTask task;
    private boolean runningTimer;

    private File file;



    @FXML
    protected FontIcon iconPlayPause;

    @FXML
    private BorderPane myBorderPane;

    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label timeMediaPlayed, nameMediaPlayed, durationMediaPlayed;

    @FXML
    private ToolBar toolbarMenu;

    @FXML
    void onHome(ActionEvent event) {
        Pane subScenePane = MiddlePaneHandler.getInstance().init();
        myBorderPane.setCenter(subScenePane);
    }

    @FXML
    void onMusicLibrary(ActionEvent event) {
        Pane subScenePane = MiddlePaneHandler.getInstance().MusicLibrary();
        myBorderPane.setCenter(subScenePane);

    }

    @FXML
    void onVideoLibrary(ActionEvent event) {
        Pane subScenePane = MiddlePaneHandler.getInstance().VideoLibrary();
        myBorderPane.setCenter(subScenePane);

    }

    @FXML
    void onPlayQueue(ActionEvent event) {
        Pane subScenePane = MiddlePaneHandler.getInstance().PlayQueue();
        myBorderPane.setCenter(subScenePane);

    }

    @FXML
    void onPlayLists(ActionEvent event) {
        Pane subScenePane = MiddlePaneHandler.getInstance().Playlists();
        myBorderPane.setCenter(subScenePane);

    }


    @FXML
    void onSettings(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Pane subScenePane = MiddlePaneHandler.getInstance().init();
        myBorderPane.setCenter(subScenePane);

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Player.getInstance().settaVolume(volumeSlider.getValue()*0.01);
            }
        });




    }

    @FXML
    void onVolume(ActionEvent event) {

    }

    @FXML
    void onShuffle(ActionEvent event) {

    }

    @FXML
    void onSkipBack(ActionEvent event) {

    }

    @FXML
    void onSkipForward(ActionEvent event) {

    }

    @FXML
    void onSpeedPlay(ActionEvent event) {

    }

    @FXML
    void onPrevious(ActionEvent event) {

    }

    @FXML
    void onProperties(ActionEvent event) {

    }

    @FXML
    void onRepeat(ActionEvent event) {

    }

    @FXML
    void onScreenMode(ActionEvent event) {

    }

    @FXML
    void onEquilizer(ActionEvent event) {

    }

    @FXML
    void onNext(ActionEvent event) {

    }
    @FXML
    void onPlayPause(ActionEvent event) {
        if(Player.getInstance().getIsRunning()){
            iconPlayPause.setIconLiteral("fa-play");
            Player.getInstance().pauseMedia();
        }
        else{
            iconPlayPause.setIconLiteral("fa-pause");
            Player.getInstance().resumeMedia();
        }

    }

    public void cambiaIcona (){
        System.out.println("ci entro");
        iconPlayPause.setIconLiteral("fa-pause");

    }

    public void riceviFile(File file){
        this.file = file;
        Player.getInstance().playMedia(file);

    }


    /*
    public void beginTimer(){

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runningTimer = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current/end);

                if (current/end ==1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task,0,1000);

    }

    public void cancelTimer(){
        runningTimer = false;
        timer.cancel();

    }*/


}
