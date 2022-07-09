package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.thread.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button btnHome,btnMusicLibrary,btnPlayQueue,btnPlaylists,btnSettings,btnVideoLibrary,btnVideoView;

    @FXML
    private VBox mainBox;

    @FXML
    private ProgressBar progressBarLoading;

    @FXML
    private Label progressType;

    @FXML
    private ToolBar toolbarMenu;

    @FXML
    private VBox vBoxProgressBar;

    @FXML
    void onHome(ActionEvent event) {
        switchMenu("home-view.fxml",false);
    }

    @FXML
    void onMusicLibrary(ActionEvent event) {
        switchMenu("music-library-view.fxml",false);
    }

    @FXML
    void onVideoLibrary(ActionEvent event) {
        switchMenu("video-library-view.fxml",false);
    }

    @FXML
    void onPlayQueue(ActionEvent event) {
        switchMenu("play-queue-view.fxml",false);
    }

    @FXML
    void onPlayLists(ActionEvent event) {
        switchMenu("playlist-view.fxml",false);
    }

    @FXML
    void onVideoView(ActionEvent event) {
        activeVideo();
    }

    @FXML
    void onSettings(ActionEvent event) {
        switchMenu("settings-view.fxml",false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();
        
        Player.getInstance().isAVideoProperty().addListener(observable -> {
            activeVideo();
        });

        SceneHandler.getInstance().getStage().fullScreenProperty().addListener(observable -> {
            if(SceneHandler.getInstance().getStage().isFullScreen())
                mainBox.setVisible(false);
            else
                mainBox.setVisible(true);
        });

        SceneHandler.getInstance().currentMidPaneProperty().addListener(observable -> {
            changeStyle();
        });

        SceneHandler.getInstance().metadataLoadindagInProgessProperty().addListener(observable -> {
            if(SceneHandler.getInstance().isMetadataLoadindagInProgess())
            {
                progressType.setText("LOADING METADATA IN PROGRESS");
                ThreadManager.getInstance().progressBarUpdate(progressBarLoading,"meta");
            }
        });

        SceneHandler.getInstance().mediaLoadingInProgessProperty().addListener(observable -> {
            if (SceneHandler.getInstance().getMediaLoadingInProgess()){
                vBoxProgressBar.setVisible(true);
                progressType.setText("LOADING MEDIA IN PROGRESS");
                ThreadManager.getInstance().progressBarUpdate(progressBarLoading,"media");
            }
            else{
                vBoxProgressBar.setVisible(false);
            }
        });

        Player.getInstance().mediaLoadedProperty().addListener(observable -> {
            if(Player.getInstance().getIsAVideo())
                buttonVideoTab (true);
            else
                buttonVideoTab(false);
        });

        SceneHandler.getInstance().switchingCurrentMidPaneProperty().addListener(observable -> {
            if(SceneHandler.getInstance().isSwitchingCurrentMidPane())
                toolbarButtonDisable(true);
            else
                toolbarButtonDisable(false);
        });

        //mainBox.setOnMouseClicked(new );
    }



    private void startToolTip() {
        // TODO: 09/07/2022
    }

    private void changeStyle() {
        // TODO: 09/07/2022
    }

    private void switchMenu(String menu,boolean videoRequested){
        SceneHandler.getInstance().setCurrentMidPane(menu);
        SceneHandler.getInstance().setRequestedVideoView(videoRequested);
    }

    private void activeVideo(){
        SceneHandler.getInstance().setRequestedVideoView(true);
    }

    private void buttonVideoTab(boolean status){
        btnVideoView.setVisible(status);
    }

    private void toolbarButtonDisable(boolean status){
        btnHome.setDisable(status);
        btnMusicLibrary.setDisable(status);
        btnVideoLibrary.setDisable(status);
        btnPlayQueue.setDisable(status);
        btnPlaylists.setDisable(status);
    }
}
