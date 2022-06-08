package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.MediaView;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {


    //ELEMENTS ON LAYOUT

    @FXML
    private VBox leftItems;
    @FXML
    private StackPane containerView;
    @FXML
    private MediaView mediaView;
    @FXML
    private FontIcon iconPlayPause;
    @FXML
    private Button btnVideoView;
    @FXML
    private BorderPane myBorderPane;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider mediaSlider;
    @FXML
    private Label currentMediaTimeLabel, mediaNameLabel, endMediaTimeLabel, artistNameLabel;
    @FXML
    private ToolBar toolbarMenu;
    @FXML
    private Button plsEquilizer,plsNext,plsPlayPause,plsPrevious,plsProperties,plsRepeat,
            plsScreenMode,plsShuffle,plsSkipBack,plsSpeedPlay,plsSkipForward;
    @FXML
    private MenuButton plsVolume;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        startToolTip();
        //INITIALIZE AN INVISIBLE MEDIAVIEW
        Player.getInstance().setMediaView(mediaView);

        //AFTER THE LOAD CONTROL IN SCENE HANDLER, THE FUNCTION SET THE CORRECT PANE IN MIDDLE OF BORDER PANE
        switchMidPane();


        //START LISTENER VARI
        /*mediaSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                //System.out.println(mediaSlider.getValue());
                Player.getInstance().changePosition(mediaSlider.getValue());
            }
        });*/


        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Player.getInstance().setVolume(volumeSlider.getValue()*0.01);
            }
        });
        // TODO: 07/06/2022 fixare tooltip plsPlayPause 
        SceneHandler.getInstance().currentMidPaneProperty().addListener(observable -> switchMidPane() );
        Player.getInstance().mediaLoadedProperty().addListener(observable -> changeButtonEnabledStatus());
        Player.getInstance().isRunningProperty().addListener(observable -> {cambiaIcona(); plsPlayPause.setTooltip(new Tooltip("Pause"));});
        Player.getInstance().currentMediaTimeProperty().addListener(observable -> {
            setMediaSlider();
            currentMediaTimeLabel.setText(formatTime(Player.getInstance().getCurrentMediaTime()));
            if(Player.getInstance().getCurrentMediaTime()==Player.getInstance().getEndMediaTime()){
                PlayQueue.getInstance().changeMedia(1);
            }
        });

        mediaSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double curr = Player.getInstance().getCurrentMediaTime();
                if(Math.abs(curr - newValue.doubleValue()) > 1){
                    Player.getInstance().changePosition(newValue.doubleValue());
                    ThreadManager.getInstance().cancelTimer();
                    currentMediaTimeLabel.setText(formatTime(newValue.doubleValue()));
                    ThreadManager.getInstance().beginTimer();
                }
            }
        });

        Player.getInstance().endMediaTimeProperty().addListener(observable -> {
            setMediaSliderEnd();
            endMediaTimeLabel.setText(formatTime(Player.getInstance().getEndMediaTime()));
        });



        Player.getInstance().mediaNameProperty().addListener(observable -> mediaNameLabel.setText(Player.getInstance().getMediaName()));
        //PlayQueue.getInstance().isAvideoProperty().addListener(observable -> activeVideoView());
        Player.getInstance().artistNameProperty().addListener(observable -> artistNameLabel.setText(Player.getInstance().getArtistName()));
        //Player.getInstance().isRunningProperty().addListener(observable -> formatTime());
        //END LISTENER VARI

    }

    public void startToolTip() {
        // TODO: 07/06/2022 
        plsShuffle.setTooltip(new Tooltip("Shuffle model"));
        plsScreenMode.setTooltip(new Tooltip("Screen mode"));
        plsSpeedPlay.setTooltip(new Tooltip("Speed play"));
        plsSkipForward.setTooltip(new Tooltip("Skip forward 10s"));
        plsSkipBack.setTooltip(new Tooltip("Skip back 10s"));
        plsVolume.setTooltip(new Tooltip("Volume"));
        plsRepeat.setTooltip(new Tooltip("Repeat"));
        plsEquilizer.setTooltip(new Tooltip("Equilizer"));
        plsProperties.setTooltip(new Tooltip("Info"));
        plsNext.setTooltip(new Tooltip("Next"));
        plsPrevious.setTooltip(new Tooltip("Previous"));
    }


    //ACTION EVENT MENU

    @FXML
    void onHome(ActionEvent event) {
        mediaView.setVisible(false);
        myBorderPane.getCenter().setVisible(true);
        SceneHandler.getInstance().setCurrentMidPane("home-view.fxml");
    }

    @FXML
    void onMusicLibrary(ActionEvent event) {
        mediaView.setVisible(false);
        myBorderPane.getCenter().setVisible(true);
        SceneHandler.getInstance().setCurrentMidPane("music-library-view.fxml");
    }

    @FXML
    void onVideoLibrary(ActionEvent event) {
        mediaView.setVisible(false);
        myBorderPane.getCenter().setVisible(true);
        SceneHandler.getInstance().setCurrentMidPane("video-library-view.fxml");
    }

    @FXML
    void onPlayQueue(ActionEvent event) {
        mediaView.setVisible(false);
        myBorderPane.getCenter().setVisible(true);
        SceneHandler.getInstance().setCurrentMidPane("play-queue-view.fxml");
    }

    @FXML
    void onPlayLists(ActionEvent event) {
        mediaView.setVisible(false);
        myBorderPane.getCenter().setVisible(true);
        SceneHandler.getInstance().setCurrentMidPane("playlist-view.fxml");
    }

    @FXML
    void onVideoView(ActionEvent event) {
        btnVideoView.setVisible(true);
        mediaView.setVisible(true);
        myBorderPane.getCenter().setVisible(false);

    }

    @FXML
    void onSettings(ActionEvent event) {

    }
    //END ACTION EVENT MENU


    //ACTION EVENT MEDIA CONTROLS BAR
    @FXML
    void onVolume(ActionEvent event) {


    }

    @FXML
    void onShuffle(ActionEvent event) {
        // TODO: 07/06/2022  se stai facendo girare un video in full screen e attivi onShuffle e skippi si bagga tutto X/ 
        if(PlayQueue.getInstance().isShuffleActive()){
            PlayQueue.getInstance().setShuffleActive(false);
            plsShuffle.setStyle("-fx-background-color: transparentBackgroundColor");

        }else{
            PlayQueue.getInstance().setShuffleActive(true);
            plsShuffle.setStyle("-fx-background-color: primarySelectionColor");
        }
    }

    @FXML
    void onPrevious(ActionEvent event) {
        if(PlayQueue.getInstance().getQueue().size()>1)
            PlayQueue.getInstance().changeMedia(-1);

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
            PlayQueue.getInstance().changeMedia(1);
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
       /* if (!SceneHandler.getInstance().getStage().isFullScreen()){
            leftItems.setVisible(false);
            AnchorPane.setLeftAnchor(containerView,0.0);
            AnchorPane.setBottomAnchor(containerView,0.0);
            SceneHandler.getInstance().getStage().setFullScreen(true);
            adjustVideoSize();
            SceneHandler.getInstance().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyCode key = keyEvent.getCode();
                    if (key == KeyCode.ESCAPE){
                        leftItems.setVisible(true);
                        AnchorPane.setLeftAnchor(containerView,270.0);
                        AnchorPane.setBottomAnchor(containerView,96.00);

                        adjustVideoSize();
                    }
                }
            });

        }
        else{
            leftItems.setVisible(true);
            AnchorPane.setLeftAnchor(containerView,270.0);
            AnchorPane.setBottomAnchor(containerView,96.00);
            SceneHandler.getInstance().getStage().setFullScreen(false);
            adjustVideoSize();
        }*/
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
            //plsPlayPause.setTooltip(new Tooltip("Play"));
            iconPlayPause.setIconLiteral("fa-play");
            Player.getInstance().pauseMedia();
        }
        else{
            //plsPlayPause.setTooltip(new Tooltip("Pause"));
            iconPlayPause.setIconLiteral("fa-pause");
            Player.getInstance().playMedia();
        }

    }

    @FXML
    void dragDetected(MouseEvent event) {
        System.out.println("dragDetected");
    }

    @FXML
    void dragDropped(DragEvent event) {
        System.out.println("dragDropped");
    }

    @FXML
    void dragOver(DragEvent event) {
        System.out.println("dragOver");
    }

    @FXML
    void mouseDragEntered(MouseDragEvent event) {
        System.out.println("mouseDragEntered");
    }

    @FXML
    void mouseDragExited(MouseDragEvent event) {
        System.out.println("mouseDragExited");
    }

    @FXML
    void mouseDragOver(MouseDragEvent event) {
        System.out.println("mouseDragOver");
    }

    @FXML
    void mouseDragReleased(MouseDragEvent event) {
        System.out.println("mouseDragReleased");
    }


    //END ACTION EVENT MEDIA CONTROLS BAR


    // TODO: 06/06/2022 RENDERE INVISIBILE VIDEO VIEW DOPO CANCELLAZIONE DELLA PLAY QUEUE 
    //FUNCTION CALLED AFTER A LISTENER OR OTHER EVENT
   /* public void activeVideoView (){
        if(PlayQueue.getInstance().isIsAvideo()){
            mediaView.setVisible(true);
            btnVideoView.setVisible(true);
            myBorderPane.getCenter().setVisible(false);
            plsScreenMode.setDisable(false);
            adjustVideoSize();
            SceneHandler.getInstance().getStage().heightProperty().addListener(observable -> adjustVideoSize());
            SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> adjustVideoSize());
            Player.getInstance().isRunningProperty().addListener(observable -> adjustVideoSize());

        }
        else{
            mediaView.setVisible(false);
            btnVideoView.setVisible(false);
            myBorderPane.getCenter().setVisible(true);
            plsScreenMode.setDisable(true);
        }

    }

    public void adjustVideoSize(){
        if(Player.getInstance().getIsRunning()){
            double currentWidth;
            double currentHeight;
            double controllBar = 96.0;
            double menuSize = 270.0;
            if (SceneHandler.getInstance().getStage().isFullScreen()){
                currentWidth = SceneHandler.getInstance().getStage().getWidth();
                currentHeight = SceneHandler.getInstance().getStage().getHeight();


            }
            else{

                currentWidth = SceneHandler.getInstance().getStage().getWidth() - menuSize;
                currentHeight = SceneHandler.getInstance().getStage().getHeight() - controllBar;
            }
            mediaView.setFitWidth(currentWidth);
            mediaView.setFitHeight(currentHeight);
            // TODO: 04/06/2022 AGGIUNGERE SPOSTASMENTO SU ASSE Y DEL VIDEO SECONDO SORGENTE
            mediaView.setPreserveRatio(true);
        }
    }*/

    private String formatTime(double timeDouble){
        if(timeDouble>0) {
            int hh = (int) (timeDouble / 3600);
            int mm = (int) ((timeDouble % 3600) / 60);
            int ss = (int) ((timeDouble % 3600) % 60);

            return String.format("%02d:%02d:%02d", hh, mm, ss);
        }

        return "00:00:00";
    }

    private void setMediaSliderEnd(){
        mediaSlider.setMax(Player.getInstance().getEndMediaTime());
        //durationMediaPlayed.setText(CalcolaTempo(Player.getInstance().getEnd()));
        //System.out.println(CalcolaTempo(Player.getInstance().getEnd()));
    }

    private void switchMidPane(){
        if (SceneHandler.getInstance().getCurrentMidPane()=="video-view.fxml"){
            btnVideoView.setDisable(false);
        }
        Pane subScenePane = SceneHandler.getInstance().switchPane();
        myBorderPane.setCenter(subScenePane);
    }

    private void changeButtonEnabledStatus(){
        boolean status;
        if(Player.getInstance().isMediaLoaded()){
            status=false;
        }else
            status=true;
        plsPrevious.setDisable(status);
        //plsScreenMode.setDisable(status);
        plsEquilizer.setDisable(status);
        plsSkipBack.setDisable(status);
        plsSkipForward.setDisable(status);
        plsNext.setDisable(status);
        plsPlayPause.setDisable(status);
        plsProperties.setDisable(status);
        plsRepeat.setDisable(status);
        plsShuffle.setDisable(status);
        plsSpeedPlay.setDisable(status);
        plsVolume.setDisable(status);
        mediaSlider.setDisable(status);
        volumeSlider.setDisable(status);
    }

    private void setMediaSlider() {
        mediaSlider.setValue(Player.getInstance().getCurrentMediaTime());

        //timeMediaPlayed.setText(CalcolaTempo(Player.getInstance().getCurrent()));
        //progressBar.setProgress((Player.getInstance().getCurrent()/Player.getInstance().getEnd()));
        //timeMediaPlayed.setText(String.valueOf(Player.getInstance().getCurrent()));
        //System.out.println(Player.getInstance().getCurrent());
    }

    public void cambiaIcona (){
        if(Player.getInstance().getIsRunning())
            iconPlayPause.setIconLiteral("fa-pause");
        else{
            iconPlayPause.setIconLiteral("fa-play");
        }

    }
    //END FUNCTION CALLED AFTER A LISTENER OR OTHER EVENT






}
