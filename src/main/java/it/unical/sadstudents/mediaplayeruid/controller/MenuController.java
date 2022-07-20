package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.utils.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
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
        activeVideo(Player.getInstance().getIsAVideo());
    }

    @FXML
    void onSettings(ActionEvent event) {
        switchMenu("settings-view.fxml",false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();
        
        Player.getInstance().isAVideoProperty().addListener(observable -> {
            activeVideo(Player.getInstance().getIsAVideo());

            // TODO: 10/07/2022 maybe some damage
        });

        SceneHandler.getInstance().getStage().fullScreenProperty().addListener(observable -> {
            if(SceneHandler.getInstance().getStage().isFullScreen()){
                mainBox.setVisible(false);
            }
            else if (Player.getInstance().getIsAVideo() && !SceneHandler.getInstance().getStage().isFullScreen()){
                mainBox.setVisible(true);
                resetAllStyles();
                btnVideoView.getStyleClass().removeAll("button","toolBarButton");
                btnVideoView.getStyleClass().add("focusedToolBarButton");
            }
            else{
                mainBox.setVisible(true);
            }

        });

        SceneHandler.getInstance().metadataLoadindagInProgessProperty().addListener(observable -> {
            if(SceneHandler.getInstance().isMetadataLoadindagInProgess())
            {
                progressType.setText("METADATA LOADING IN PROGRESS");
                ThreadManager.getInstance().progressBarUpdate(progressBarLoading,"meta");
            }
        });

        SceneHandler.getInstance().mediaLoadingInProgessProperty().addListener(observable -> {
            if (SceneHandler.getInstance().getMediaLoadingInProgess()){
                vBoxProgressBar.setVisible(true);
                progressType.setText("MEDIA LOADING IN PROGRESS");
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


        SceneHandler.getInstance().scaleTransition(btnHome);
        SceneHandler.getInstance().scaleTransition(btnMusicLibrary);
        SceneHandler.getInstance().scaleTransition(btnPlaylists);
        SceneHandler.getInstance().scaleTransition(btnSettings);
        SceneHandler.getInstance().scaleTransition(btnPlayQueue);
        SceneHandler.getInstance().scaleTransition(btnVideoLibrary);
        SceneHandler.getInstance().scaleTransition(btnVideoView);

        btnHome.getStyleClass().removeAll("button","toolBarButton");
        btnHome.getStyleClass().add("focusedToolBarButton");

        for(Node btn: toolbarMenu.getItems()){
            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(btn.getId() != null) {
                        resetAllStyles();
                        btn.getStyleClass().removeAll("button", "toolBarButton");
                        btn.getStyleClass().add("focusedToolBarButton");
                    }
                }
            });
        }

        btnSettings.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resetAllStyles();
                btnSettings.getStyleClass().removeAll("button","toolBarButton");
                btnSettings.getStyleClass().add("focusedToolBarButton");
            }
        });
    }




    private void startToolTip() {
        // TODO: 09/07/2022
    }

    private void resetAllStyles() {
        for(Node btn: toolbarMenu.getItems()){
            if(btn.getStyleClass().indexOf("focusedToolBarButton") != -1){
                btn.getStyleClass().remove("focusedToolBarButton");
                btn.getStyleClass().addAll("button", "toolBarButton");
            }
        }

        if(btnSettings.getStyleClass().indexOf("focusedToolBarButton") != -1){
            btnSettings.getStyleClass().remove("focusedToolBarButton");
            btnSettings.getStyleClass().addAll("button", "toolBarButton");
        }

    }

    private void switchMenu(String menu,boolean videoRequested){
        SceneHandler.getInstance().setCurrentMidPane(menu);
        SceneHandler.getInstance().setRequestedVideoView(videoRequested);
    }

    private void activeVideo(boolean status){
        SceneHandler.getInstance().setRequestedVideoView(status);
    }

    private void buttonVideoTab(boolean status){
        /*btnVideoView.setVisible(status);
        if(status)
            resetAllStyles();
        btnVideoView.getStyleClass().removeAll("button","toolBarButton");
        btnVideoView.getStyleClass().add("focusedToolBarButton");*/
        if(Player.getInstance().getIsAVideo()){
            btnVideoView.setVisible(status);
            resetAllStyles();
            btnVideoView.getStyleClass().removeAll("button","toolBarButton");
            btnVideoView.getStyleClass().add("focusedToolBarButton");
        }
        else if (btnVideoView.isVisible()){
            btnVideoView.getStyleClass().removeAll("focusedToolBarButton");
            btnVideoView.getStyleClass().add(  "toolBarButton");
            btnVideoView.setVisible(status);
            String current = SceneHandler.getInstance().getCurrentMidPane();
            if(current.equals("home-view.fxml")) {
                btnHome.getStyleClass().removeAll("button", "toolBarButton");
                btnHome.getStyleClass().add("focusedToolBarButton");
            }
            else if(current.equals("music-library-view.fxml")) {
                btnMusicLibrary.getStyleClass().removeAll("button", "toolBarButton");
                btnMusicLibrary.getStyleClass().add("focusedToolBarButton");
            }
            else if(current.equals("video-library-view.fxml")) {
                btnVideoLibrary.getStyleClass().removeAll("button", "toolBarButton");
                btnVideoLibrary.getStyleClass().add("focusedToolBarButton");
            }
            else if(current.equals("play-queue-view.fxml")) {
                btnPlayQueue.getStyleClass().removeAll("button", "toolBarButton");
                btnPlayQueue.getStyleClass().add("focusedToolBarButton");
            }
            else if(current.equals("playlist-view.fxml")) {
                btnPlaylists.getStyleClass().removeAll("button", "toolBarButton");
                btnPlaylists.getStyleClass().add("focusedToolBarButton");
            }
            else if(current.equals("settings-view.fxml")) {
                btnSettings.getStyleClass().removeAll("button", "toolBarButton");
                btnSettings.getStyleClass().add("focusedToolBarButton");
            }
        }


    }

    private void toolbarButtonDisable(boolean status){
        btnHome.setDisable(status);
        btnMusicLibrary.setDisable(status);
        btnVideoLibrary.setDisable(status);
        btnPlayQueue.setDisable(status);
        btnPlaylists.setDisable(status);
    }
}
