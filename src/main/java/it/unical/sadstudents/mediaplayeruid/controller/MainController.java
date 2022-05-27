package it.unical.sadstudents.mediaplayeruid.controller;




import it.unical.sadstudents.mediaplayeruid.view.TabCentrale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public TabCentrale subScenaAttuale;

    @FXML
    private BorderPane myBorderPane;

    @FXML
    private Label durationMediaPlayed;

    @FXML
    private Label nameMediaPlayed;

    @FXML
    private Slider sliderMediaPlayed;


    @FXML
    private Label tabAttuale;

    @FXML
    private Label timeMediaPlayed;

    @FXML
    private ToolBar toolbarMenu;



    @FXML
    void onHome(ActionEvent event) {
        Pane subScenePane = subScenaAttuale.getInstance().init();
        myBorderPane.setCenter(subScenePane);
        tabAttuale.setText("HOME");
    }

    @FXML
    void onMusicLibrary(ActionEvent event) {
        Pane subScenePane = subScenaAttuale.getInstance().MusicLibrary();
        myBorderPane.setCenter(subScenePane);
        tabAttuale.setText("MUSIC LIBRARY");

    }

    @FXML
    void onVideoLibrary(ActionEvent event) {
        Pane subScenePane = subScenaAttuale.getInstance().VideoLibrary();
        myBorderPane.setCenter(subScenePane);
        tabAttuale.setText("VIDEO LIBRARY");

    }

    @FXML
    void onPlayQueue(ActionEvent event) {
        Pane subScenePane = subScenaAttuale.getInstance().PlayQueue();
        myBorderPane.setCenter(subScenePane);
        tabAttuale.setText("PLAY QUEUE");

    }

    @FXML
    void onPlayLists(ActionEvent event) {
        Pane subScenePane = subScenaAttuale.getInstance().Playlists();
        myBorderPane.setCenter(subScenePane);
        tabAttuale.setText("PLAYLISTS");

    }


    @FXML
    void onSettings(ActionEvent event) {

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane subScenePane = subScenaAttuale.getInstance().init();
        myBorderPane.setCenter(subScenePane);
        tabAttuale.setText("HOME");

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

    }
}
