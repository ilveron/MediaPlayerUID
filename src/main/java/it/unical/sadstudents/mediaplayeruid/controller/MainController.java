package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.view.*;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.ToDoubleBiFunction;


public class MainController implements Initializable {
    //ELEMENTS ON LAYOUT


    @FXML
    private StackPane containerView;
    @FXML
    private MediaView mediaView;

    @FXML
    private BorderPane myBorderPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private StackPane centralStackPane;

    @FXML
    private AnchorPane infoMediaAnchor;

    @FXML
    private Pane mediaInfoPane;

    private String from = "home-view.fxml", to="";
    private String[] menu = {  "home-view.fxml", "music-library-view.fxml",
                                "video-library-view.fxml", "play-queue-view.fxml",
                                "playlist-view.fxml", "settings-view.fxml"};
    List<String> menuList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //FIRST SETUP OF ITEMS
        startToolTip();

        Player.getInstance().setMediaView(mediaView);
        //mediaView.setVisible(false);

        try {
            Parent parent = (new FXMLLoader(MainApplication.class.getResource("menu-view.fxml"))).load();
            AnchorPane anchorPane = new AnchorPane(parent);
            AnchorPane.setTopAnchor(parent,0.0);
            AnchorPane.setLeftAnchor(parent,0.0);
            AnchorPane.setRightAnchor(parent,0.0);
            AnchorPane.setBottomAnchor(parent,0.0);
            anchorPane.setPrefHeight(660);
            anchorPane.setMinHeight(400);
            myBorderPane.setLeft(anchorPane);

            Parent parent1 = (new FXMLLoader(MainApplication.class.getResource("player-controls-view.fxml"))).load();

            AnchorPane anchorPane1 = new AnchorPane(parent1);
            AnchorPane.setLeftAnchor(parent1,0.0);
            AnchorPane.setRightAnchor(parent1,0.0);
            AnchorPane.setBottomAnchor(parent1,0.0);
            anchorPane1.setPrefHeight(96);
            anchorPane1.setPrefWidth(1344);
            myBorderPane.setBottom(anchorPane1);





            anchorPane.hoverProperty().addListener(observable -> {
                if(anchorPane.isHover()){

                    int startValue = 50;


                    if(anchorPane.widthProperty().get() != 50)
                        startValue = (int)anchorPane.widthProperty().get();

                    SceneHandler.getInstance().resizeAnchorPaneTransition(anchorPane, startValue, (int)anchorPane.maxHeightProperty().get(), 270, (int)anchorPane.maxHeightProperty().get(), 0.5);
                }
                else{
                    int startValue = 270;
                    if(anchorPane.widthProperty().get() != 270)
                        startValue = (int)anchorPane.widthProperty().get();

                    SceneHandler.getInstance().resizeAnchorPaneTransition(anchorPane, startValue,(int)anchorPane.maxHeightProperty().get(), 50, (int)anchorPane.maxHeightProperty().get(), 0.5);
                }
                SceneHandler.getInstance().setMenuHover(anchorPane.isHover());
            });

        } catch (Exception e) {
        }


        switchMidPane();
        activeVideoView();
        //END FIRST SETUP



        SceneHandler.getInstance().currentMidPaneProperty().addListener(observable -> {
            switchMidPane();
        });

        SceneHandler.getInstance().infoMediaPropertyHoverProperty().addListener(observable -> {
            if (SceneHandler.getInstance().isInfoMediaPropertyHover()) {
                if(!infoMediaAnchor.isVisible())
                    infoMediaAnchor.setVisible(true);
            }
            else{
                infoMediaAnchor.setVisible(false);
            }
        });


        // TODO: 07/07/2022 Gestire scomparsa miniImage ad ogni pausa
        Player.getInstance().isRunningProperty().addListener(observable -> {
            if((Player.getInstance().getIsRunning() && Player.getInstance().isMediaLoaded()) || (!Player.getInstance().getIsRunning() && Player.getInstance().isMediaLoaded())){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MediaInfo mediaInfo = new MediaInfo(PlayQueue.getInstance().getQueue().get(PlayQueue.getInstance().getCurrentMedia()));

                        if(mediaInfoPane.getChildren().size() > 0)
                            mediaInfoPane.getChildren().remove(0);

                        mediaInfoPane.getChildren().add(mediaInfo);
                        int index= HomeTilePaneHandler.getInstance().getMyMediaSingleBoxes().size()-1;
                        if(index!=-1){
                            mediaInfo.setImage(HomeTilePaneHandler.getInstance().getMyMediaSingleBoxes().get(index).getImage());
                        }
                    }
                });
            }
            else{
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        mediaInfoPane.getChildren().remove(0);
                    }
                });
            }


        });

        SceneHandler.getInstance().fullScreenRequestedProperty().addListener(observable -> {
            screenModeHandler(SceneHandler.getInstance().isFullScreenRequested());


        });
        Player.getInstance().isAVideoProperty().addListener(observable -> {
            if(SceneHandler.getInstance().getStage().isFullScreen() && !Player.getInstance().getIsAVideo()){
                //screenModeHandler(false);
                SceneHandler.getInstance().setRequestedVideoView(false);
                SceneHandler.getInstance().setFullScreenRequested(false);
                SceneHandler.getInstance().setCurrentMidPane("play-queue-view.fxml");
            }

        });




        SceneHandler.getInstance().requestedVideoViewProperty().addListener(observable -> activeVideoView());



        SceneHandler.getInstance().requestedVideoViewProperty().addListener(observable -> {
            changeBackgroundMediaView();

        });
        //END LISTENER VARI

        menuList = Arrays.asList(menu);
    }

    private void startToolTip() {
    }

    public void changeBackgroundMediaView(){
        String videoBack = "videoBackgroundColor";
        String normalBack = "primaryTemplate";

        if(SceneHandler.getInstance().isRequestedVideoView()){
            containerView.getStyleClass().remove(normalBack);
            containerView.getStyleClass().add(videoBack);
        }
        else{
            containerView.getStyleClass().remove(videoBack);
            containerView.getStyleClass().add(normalBack);
        }

    }



    private void screenModeHandler(boolean fullScreen) {
        SceneHandler.getInstance().getStage().setFullScreen(fullScreen);
        if (SceneHandler.getInstance().getStage().isFullScreen()) {
            SceneHandler.getInstance().setRequestedVideoView(true);
            mediaView.setVisible(true);
            myBorderPane.getCenter().setVisible(false);
            AnchorPane.setLeftAnchor(containerView, 0.0);
            AnchorPane.setBottomAnchor(containerView, 0.0);
            adjustVideoSize();

            SceneHandler.getInstance().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyCode key = keyEvent.getCode();
                    if (key == KeyCode.ESCAPE) {
                        SceneHandler.getInstance().setFullScreenRequested(false);
                    }
                }
            });

        } else {
            AnchorPane.setLeftAnchor(containerView, 50.0);
            AnchorPane.setBottomAnchor(containerView, 96.00);
            adjustVideoSize();
        }

    }



    //END ACTION EVENT MEDIA CONTROLS BAR


    //FUNCTION CALLED AFTER A LISTENER OR OTHER EVENT
    public void activeVideoView() {
        if (SceneHandler.getInstance().isRequestedVideoView() && Player.getInstance().getIsAVideo()) {
            mediaView.setVisible(true);
            myBorderPane.getCenter().setVisible(false);
            SceneHandler.getInstance().setRequestedVideoView(true);
            adjustVideoSize();
            SceneHandler.getInstance().getStage().heightProperty().addListener(observable -> adjustVideoSize());
            SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> adjustVideoSize());
            Player.getInstance().isRunningProperty().addListener(observable -> adjustVideoSize());

        } else {
            mediaView.setVisible(false);
            SceneHandler.getInstance().setRequestedVideoView(false);
            myBorderPane.getCenter().setVisible(true);
            //plsScreenMode.setDisable(true);
        }
    }

    public void adjustVideoSize() {
        if (Player.getInstance().getIsRunning()) {
            double currentWidth;
            double currentHeight;
            double controllBar = 96.0;
            double menuSize = 50.0;
            if (SceneHandler.getInstance().getStage().isFullScreen()) {
                currentWidth = SceneHandler.getInstance().getStage().getWidth();
                currentHeight = SceneHandler.getInstance().getStage().getHeight();
            } else {
                currentWidth = SceneHandler.getInstance().getStage().getWidth() - menuSize;
                currentHeight = SceneHandler.getInstance().getStage().getHeight() - controllBar;
            }
            mediaView.setFitWidth(currentWidth);
            mediaView.setFitHeight(currentHeight);
            // TODO: 04/06/2022 AGGIUNGERE SPOSTASMENTO SU ASSE Y DEL VIDEO SECONDO SORGENTE
            mediaView.setPreserveRatio(true);
        }
    }


    private void switchMidPane() {

        to = SceneHandler.getInstance().currentMidPaneProperty().get();

        if(centralStackPane.getChildren().size() <= 1){
            centralStackPane.getChildren().add(SceneHandler.getInstance().switchPane());
        }

        if(centralStackPane.getChildren().size() == 2){
            SceneHandler.getInstance().setSwitchingCurrentMidPane(true);

            TranslateTransition translateTransitionOld;
            TranslateTransition translateTransitionNew;

            if(menuList.indexOf(to) > menuList.indexOf(from)){
                translateTransitionOld = SceneHandler.getInstance().translateTransition(centralStackPane.getChildren().get(0), (int)centralStackPane.getChildren().get(0).getTranslateX(), 0, centralStackPane.getChildren().get(0).getTranslateX(), -SceneHandler.getInstance().getScene().getHeight(), Interpolator.EASE_BOTH, 0.75);
                translateTransitionNew = SceneHandler.getInstance().translateTransition(centralStackPane.getChildren().get(1), (int)centralStackPane.getChildren().get(0).getTranslateX(), SceneHandler.getInstance().getScene().getHeight(), centralStackPane.getChildren().get(0).getTranslateX(), 0, Interpolator.EASE_BOTH, 0.75);
            }
            else{
                translateTransitionOld = SceneHandler.getInstance().translateTransition(centralStackPane.getChildren().get(0), (int)centralStackPane.getChildren().get(0).getTranslateX(), 0, centralStackPane.getChildren().get(0).getTranslateX(), SceneHandler.getInstance().getScene().getHeight(), Interpolator.EASE_BOTH, 0.75);
                translateTransitionNew = SceneHandler.getInstance().translateTransition(centralStackPane.getChildren().get(1), (int)centralStackPane.getChildren().get(0).getTranslateX(), -SceneHandler.getInstance().getScene().getHeight(), centralStackPane.getChildren().get(0).getTranslateX(), 0, Interpolator.EASE_BOTH, 0.75);
            }
            //subScene = new FXMLLoader().load(MainApplication.class.getResource(currentMidPane.get()));

            translateTransitionOld.play();
            translateTransitionNew.play();

            translateTransitionNew.setOnFinished(event -> {
                centralStackPane.getChildren().remove(0);
                SceneHandler.getInstance().setSwitchingCurrentMidPane(false);
            });
        }
        from = to;
        to = "";

    }

      private void quit() {
        // TODO: 15/06/2022 Controlli prima di uscire dall'applicazione
        Platform.exit();
    }




}
