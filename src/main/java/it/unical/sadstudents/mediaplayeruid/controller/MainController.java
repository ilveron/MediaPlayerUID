package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;




public class MainController implements Initializable {

    @FXML
    private AnchorPane containerView;


    private File file;

    @FXML
    private VBox leftItems;

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
    private Slider sliderMedia;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Player.getInstance().setMediaView(mediaView);

        switchMidPane();





        //sliderMedia.setMax(Player.getInstance().getEnd());
        //sliderMedia.setValue(0);

        sliderMedia.valueChangingProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                //Player.getInstance().setCurrent(sliderMedia.getValue());
                Player.getInstance().changePosition(sliderMedia.getValue());
            }
        });


        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Player.getInstance().settaVolume(volumeSlider.getValue()*0.01);
            }
        });



        SceneHandler.getInstance().currentMidPaneProperty().addListener(observable -> switchMidPane() );


        Player.getInstance().mediaLoadedProperty().addListener(observable -> changeButtonEnabledStatus());

        Player.getInstance().isRunningProperty().addListener(observable ->cambiaIcona() );

        Player.getInstance().currentProperty().addListener(observable ->settaSliderMedia());
        //Player.getInstance().currentProperty().addListener(observable ->timeMediaPlayed.setText(String.valueOf(Player.getInstance().getCurrent())));

        Player.getInstance().nameMediaProperty().addListener(observable -> nameMediaPlayed.setText(Player.getInstance().getNameMedia()));

        Player.getInstance().endProperty().addListener(observable -> settaFineMedia());


        PlayQueue.getInstance().isAvideoProperty().addListener(observable -> activeVideoView());
        Player.getInstance().isRunningProperty().addListener(observable -> CalcolaTempo());



    }





    public void activeVideoView (){
        if(PlayQueue.getInstance().isIsAvideo()){
            mediaView.setVisible(true);
            btnVideoView.setVisible(true);
            myBorderPane.getCenter().setVisible(false);
            adjustVideoSize();
        }
        SceneHandler.getInstance().getStage().heightProperty().addListener(observable -> adjustVideoSize());
        SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> adjustVideoSize());
        Player.getInstance().isRunningProperty().addListener(observable -> adjustVideoSize());



    }

    public void adjustVideoSize(){
        if(Player.getInstance().getIsRunning()){
            System.out.println("ciao");
            double controllBar = 96.0;
            double menuSize = 270.0;
            double currentWidth = SceneHandler.getInstance().getStage().getWidth() - menuSize;
            double currentHeight = SceneHandler.getInstance().getStage().getHeight() - controllBar;

            mediaView.setFitHeight(currentHeight);
            mediaView.setFitWidth(currentWidth);

            // TODO: 04/06/2022 AGGIUNGERE SPOSTASMENTO SU ASSE Y DEL VIDEO SECONDO SORGENTE

            mediaView.setPreserveRatio(true);
        }
    }





    private String CalcolaTempo(){
        // TODO: 04/06/2022 sempre null 
        if (Player.getInstance().isMediaLoaded() && Player.getInstance().getIsRunning() && Player.getInstance().getMediaPlayer().getMedia().durationProperty().get()!=null){
            Double current = Player.getInstance().getMediaPlayer().getMedia().durationProperty().get().toSeconds();
            System.out.println(current);
            if(current>0) {
                int minuti = (int) (current / 64);
                int ore = (int) (current / 64);
                int secon = (int) (current % 64);
                return String.format("%d:%2d:%2d", ore, minuti, secon);
            }
        }


        return "00:00:00";

    }

    private void settaFineMedia(){
        sliderMedia.setMax(Player.getInstance().getEnd());
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
        boolean status = true;
        if(plsPlayPause.isDisabled()){
            status = false;
        }
        plsPrevious.setDisable(status);
        plsScreenMode.setDisable(status);
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
        sliderMedia.setDisable(status);
        volumeSlider.setDisable(status);
    }



    private void settaSliderMedia() {
        sliderMedia.setValue(Player.getInstance().getCurrent());

        //timeMediaPlayed.setText(CalcolaTempo(Player.getInstance().getCurrent()));
        //progressBar.setProgress((Player.getInstance().getCurrent()/Player.getInstance().getEnd()));
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
            PlayQueue.getInstance().changeMediaWithButton(-1);

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
            PlayQueue.getInstance().changeMediaWithButton(1);
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
