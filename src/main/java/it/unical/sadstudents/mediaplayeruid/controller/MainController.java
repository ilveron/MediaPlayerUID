package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.view.MiddlePaneHandler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;




public class MainController implements Initializable {


    private File file;

    @FXML
    private FontIcon iconPlayPause;

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
    private Button plsEquilizer,plsNext,plsPlayPause,plsPrevious,plsProperties,plsRepeat,
            plsScreenMode,plsShuffle,plsSkipBack,plsSpeedPlay,plsSkipForward;
    @FXML
    private MenuButton plsVolume;

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

        Player.getInstance().mediaLoadedProperty().addListener(observable ->activeFunction());

        Player.getInstance().isRunningProperty().addListener(observable ->cambiaIcona() );

        Player.getInstance().currentProperty().addListener(observable ->settaProgressBar());
        //Player.getInstance().currentProperty().addListener(observable ->timeMediaPlayed.setText(String.valueOf(Player.getInstance().getCurrent())));

        Player.getInstance().nameMediaProperty().addListener(observable -> nameMediaPlayed.setText(Player.getInstance().getNameMedia()));




    }

    private void activeFunction(){
        plsPrevious.setDisable(false);
        plsScreenMode.setDisable(false);
        plsEquilizer.setDisable(false);
        plsSkipBack.setDisable(false);
        plsSkipForward.setDisable(false);
        plsNext.setDisable(false);
        plsPlayPause.setDisable(false);
        plsProperties.setDisable(false);
        plsRepeat.setDisable(false);
        plsShuffle.setDisable(false);
        plsSpeedPlay.setDisable(false);
        plsVolume.setDisable(false);
        progressBar.setDisable(false);
        volumeSlider.setDisable(false);
    }

    private void settaProgressBar() {
        progressBar.setProgress((Player.getInstance().getCurrent()/Player.getInstance().getEnd()));
        //timeMediaPlayed.setText(String.valueOf(Player.getInstance().getCurrent()));
        //System.out.println(Player.getInstance().getCurrent());


    }

    @FXML
    void onVolume(ActionEvent event) {


    }

    @FXML
    void onShuffle(ActionEvent event) {

    }

    @FXML
    void onPrevious(ActionEvent event) {
        if(PlayQueue.getInstance().getQueue().size()>1)
            PlayQueue.getInstance().cambiacanzonefrombutton(-1);

    }

    @FXML
    void onSkipBack(ActionEvent event) {
        Player.getInstance().tenSecondBack();
    }

    @FXML
    void onSkipForward(ActionEvent event) {
        Player.getInstance().tenSecondForward();
    }

    @FXML
    void onNext(ActionEvent event) {

        if(PlayQueue.getInstance().getQueue().size()>1)
            PlayQueue.getInstance().cambiacanzonefrombutton(1);
    }

    @FXML
    void onProperties(ActionEvent event) {

    }

    @FXML
    void onRepeat(ActionEvent event) {
        Player.getInstance().restart();

    }

    @FXML
    void onScreenMode(ActionEvent event) {

    }

    @FXML
    void onSpeedPlay(ActionEvent event) {

    }

    @FXML
    void onEquilizer(ActionEvent event) {

    }


    @FXML
    void onPlayPause(ActionEvent event) {
        if(Player.getInstance().getIsRunning()){
            iconPlayPause.setIconLiteral("fa-play");
            Player.getInstance().pauseMedia();
        }
        else{
            iconPlayPause.setIconLiteral("fa-pause");
            Player.getInstance().playMedia();
        }

    }

    public void cambiaIcona (){
        if(Player.getInstance().getIsRunning())
            iconPlayPause.setIconLiteral("fa-pause");
        else{
            iconPlayPause.setIconLiteral("fa-play");
        }

    }







}
