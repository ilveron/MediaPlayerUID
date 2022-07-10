package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.KeyCombo;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.thread.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.view.HomeTilePaneHandler;
import it.unical.sadstudents.mediaplayeruid.view.MediaInfo;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import it.unical.sadstudents.mediaplayeruid.view.SubStageHandler;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    @FXML
    private Label artistNameLabel;

    @FXML
    private VBox controllePlayer;

    @FXML
    private Label currentMediaTimeLabel;

    @FXML
    private Label endMediaTimeLabel;

    @FXML
    private FontIcon iconPlayPause;

    @FXML
    private Label mediaNameLabel;

    @FXML
    private Slider mediaSlider;

    @FXML
    private ImageView miniImageView;

    @FXML
    private Button plsEquilizer;

    @FXML
    private Button plsNext;

    @FXML
    private Button plsPlayPause;

    @FXML
    private Button plsPrevious;

    @FXML
    private Button plsProperties;

    @FXML
    private ToggleButton plsRepeat;

    @FXML
    private Button plsScreenMode;

    @FXML
    private ToggleButton plsShuffle;

    @FXML
    private Button plsSkipBack;

    @FXML
    private Button plsSkipForward;

    @FXML
    private ChoiceBox<String> speedChoiceBox;

    @FXML
    private Button volumeButton;

    @FXML
    private FontIcon volumeIcon;

    @FXML
    private Slider volumeSlider;

    @FXML
    void dragDetected(MouseEvent event) {

    }

    @FXML
    void dragDropped(DragEvent event) {

    }

    @FXML
    void dragOver(DragEvent event) {

    }

    @FXML
    void mouseDragEntered(MouseDragEvent event) {

    }

    @FXML
    void mouseDragExited(MouseDragEvent event) {

    }

    @FXML
    void mouseDragOver(MouseDragEvent event) {

    }

    @FXML
    void mouseDragReleased(MouseDragEvent event) {

    }


    private Service<Void> service;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();
        double percentage = 100.0;// * (newValue.doubleValue() - volumeSlider.getMin()) / (volumeSlider.getMax() - volumeSlider.getMin());

        Player.getInstance().isAVideoProperty().addListener(observable -> {
            if(Player.getInstance().getIsAVideo())
                plsScreenMode.setDisable(false);
            else
                plsScreenMode.setDisable(true);
        });
        volumeSlider.setStyle("-track-color: linear-gradient(to right, tertiarySelectionColor " + percentage + "%, white " + percentage + ("%);"));

        mediaSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                //System.out.println(mediaSlider.getValue());
                Player.getInstance().changePosition(mediaSlider.getValue());

            }
        });



        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if(Player.getInstance().getMediaPlayer().isMute())
                    Player.getInstance().getMediaPlayer().setMute(false);
                if (volumeSlider.getValue() == 0) volumeIcon.setIconLiteral("fa-volume-off");
                else if (volumeSlider.getValue() > 50) volumeIcon.setIconLiteral("fa-volume-up");
                else volumeIcon.setIconLiteral("fa-volume-down");
                Player.getInstance().setVolume(volumeSlider.getValue() * 0.01);

                double percentage = 100.0 * (newValue.doubleValue() - volumeSlider.getMin()) / (volumeSlider.getMax() - volumeSlider.getMin());
                volumeSlider.setStyle("-track-color: linear-gradient(to right, tertiarySelectionColor " + percentage + "%, white " + percentage + ("%);"));
            }
        });



        // TODO: 07/06/2022 fixare tooltip plsPlayPause

        Player.getInstance().mediaLoadedProperty().addListener(observable -> {
            if(Player.getInstance().isMediaLoaded()) {
                changeButtonEnabledStatus();
            }
            else {
                changeButtonEnabledStatus();
            }
        });

        plsProperties.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                SceneHandler.getInstance().setInfoMediaPropertyHover(true);
            }
            else{
                SceneHandler.getInstance().setInfoMediaPropertyHover(false);
            }
        });

        // TODO: 07/07/2022 Gestire scomparsa miniImage ad ogni pausa
        Player.getInstance().isRunningProperty().addListener(observable -> {
            switchPlayPauseIcon();
            plsPlayPause.setTooltip(new Tooltip("Pause"));

        });

        HomeTilePaneHandler.getInstance().readyIntegerProperty().addListener(observable -> {
            if (HomeTilePaneHandler.getInstance().getReadyInteger() == HomeTilePaneHandler.getInstance().getMyMediaSingleBoxes().size()){
                if (!Player.getInstance().getIsRunning() && !Player.getInstance().isMediaLoaded())
                    miniImageView.setImage(null);
                else{
                    int index = HomeTilePaneHandler.getInstance().getMyMediaSingleBoxes().size()-1;
                    if(index!=-1)
                        miniImageView.setImage(HomeTilePaneHandler.getInstance().getMyMediaSingleBoxes().get(index).getImage());
                }
            }

        });

        Player.getInstance().currentMediaTimeProperty().addListener(observable -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setMediaSlider();
                    currentMediaTimeLabel.setText(formatTime(Player.getInstance().getCurrentMediaTime()));
                    if ((Player.getInstance().getCurrentMediaTime() == Player.getInstance().getEndMediaTime()) && Player.getInstance().getEndMediaTime()!=0.0) {

                        PlayQueue.getInstance().changeMedia(1);
                    }
                }
            });

        });

        String[] speeds = {"0.50x", "0.75x", "1.00x", "1.25x", "1.50x", "2.00x"};
        for(int i=0; i<speeds.length ; i++){
            speedChoiceBox.getItems().add(speeds[i]);
        }
        speedChoiceBox.setValue("1.00x");
        speedChoiceBox.setOnAction(this::changeSpeed);

        mediaSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double curr = Player.getInstance().getCurrentMediaTime();
                if (Math.abs(curr - newValue.doubleValue()) > 1) {
                    Player.getInstance().changePosition(newValue.doubleValue());
                    ThreadManager.getInstance().cancelTimer();
                    currentMediaTimeLabel.setText(formatTime(newValue.doubleValue()));
                    ThreadManager.getInstance().beginTimer();
                }
                double percentage = 100.0 * (newValue.doubleValue() - mediaSlider.getMin()) / (mediaSlider.getMax() - mediaSlider.getMin());
                mediaSlider.setStyle("-track-color: linear-gradient(to right, tertiarySelectionColor " + percentage + "%, white " + percentage + ("%);"));
            }
        });

        Player.getInstance().endMediaTimeProperty().addListener(observable -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setMediaSliderEnd();
                    endMediaTimeLabel.setText(formatTime(Player.getInstance().getEndMediaTime()));
                }
            });

        });

        Player.getInstance().mediaNameProperty().addListener(observable ->{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    mediaNameLabel.setText(Player.getInstance().getMediaName());
                }
            });
        });

        // TODO: 15/06/2022 why??
        Player.getInstance().artistNameProperty().addListener(observable ->{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    artistNameLabel.setText(Player.getInstance().getArtistName());
                }
            });
        });
        Player.getInstance().isRunningProperty().addListener(observable -> formatTime(Player.getInstance().getCurrentMediaTime()));

        PlayQueue.getInstance().shuffleActiveProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if(oldValue && !newValue){
                    if(plsShuffle.isDisabled()) {
                        plsShuffle.setDisable(false);
                        plsShuffle.fire();
                        plsShuffle.setDisable(true);
                    }
                }

            }
        });



        SceneHandler.getInstance().getStage().fullScreenProperty().addListener(observable -> {
            controlBarHandlerFullScreen();
        });




    }


    public void startToolTip() {
        // TODO: 07/06/2022
        plsShuffle.setTooltip(new Tooltip("Shuffle mode"));
        plsScreenMode.setTooltip(new Tooltip("Screen mode"));
        speedChoiceBox.setTooltip(new Tooltip("Speed play"));
        plsSkipForward.setTooltip(new Tooltip("Skip forward 10s"));
        plsSkipBack.setTooltip(new Tooltip("Skip back 10s"));
        volumeButton.setTooltip(new Tooltip("Volume"));
        plsRepeat.setTooltip(new Tooltip("Repeat"));
        plsEquilizer.setTooltip(new Tooltip("Equilizer"));
        plsProperties.setTooltip(new Tooltip("Info"));
        plsNext.setTooltip(new Tooltip("Next"));
        plsPrevious.setTooltip(new Tooltip("Previous"));
    }

    @FXML
    void onVolumeButtonAction(ActionEvent event) {
        muteUnmuteHandler();
    }

    @FXML
    void onShuffle(ActionEvent event) {
        // TODO: 07/06/2022  se stai facendo girare un video in full screen e attivi onShuffle e skippi si bagga tutto X/
        if (PlayQueue.getInstance().shuffleActiveProperty().get()) {
            PlayQueue.getInstance().shuffleActiveProperty().set(false);
        } else {
            PlayQueue.getInstance().shuffleActiveProperty().set(true);
        }

        if (!PlayQueue.getInstance().isShuffleQueueIndexesGenerated()) {
            PlayQueue.getInstance().generateShuffleList();
            System.out.println("Lista generata!");
        }
    }

    @FXML
    void onPrevious(ActionEvent event) {
        previous();
    }

    private void previous() {
        if (PlayQueue.getInstance().getQueue().size() > 1)
            PlayQueue.getInstance().changeMedia(-1);
    }

    @FXML
    void onSkipBack(ActionEvent event) {
        skipBack();
    }

    private void skipBack() {
        Player.getInstance().tenSecondBack();
    }

    @FXML
    void onSkipForward(ActionEvent event) {
        skipForward();
    }

    private void skipForward() {
        Player.getInstance().tenSecondForward();
    }

    @FXML
    void onNext(ActionEvent event) {
        next();
    }

    private void next() {
        if (PlayQueue.getInstance().getQueue().size() > 1)
            PlayQueue.getInstance().changeMedia(1);
    }

    @FXML
    void onProperties(ActionEvent event) {
        // TODO: 04/07/2022 eliminare , non serve più in quanto gestito con hover

    }


    @FXML
    void onRepeat(ActionEvent event) {
        Player.getInstance().repeat();
    }

    @FXML
    void onScreenMode(ActionEvent event) {
        if(SceneHandler.getInstance().getStage().isFullScreen())
            SceneHandler.getInstance().setFullScreenRequested(false);
        else
            SceneHandler.getInstance().setFullScreenRequested(true);
        // TODO: 09/07/2022 thread per la barra
    }

    private void controlBarHandlerFullScreen() {
        if (SceneHandler.getInstance().getStage().isFullScreen()) {
            service = new Service<>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<>() {
                        @Override
                        protected Void call() throws Exception {
                            Thread.sleep(3000);
                            if(SceneHandler.getInstance().getStage().isFullScreen())
                                controllePlayer.setVisible(false);
                            return null;
                        }
                    };
                }
            };
            SceneHandler.getInstance().getScene().setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    controllePlayer.setVisible(true);
                    if(!service.isRunning() && SceneHandler.getInstance().getStage().isFullScreen()){
                        service.restart();
                    }
                }
            });


            service.start();
        } else {
            service.cancel();
        }
    }



   /* @FXML
    void onSpeedPlay(ActionEvent event) {

    }*/

    @FXML
    void onEquilizer(ActionEvent event) {
        SubStageHandler.getInstance().init("audioEqualizer-view.fxml",484,280,"Audio Equalizer",false,"");
    }


    @FXML
    void onPlayPause(ActionEvent event) {
        playPauseHandler();
    }

    private void playPauseHandler() {
        if (Player.getInstance().getIsRunning()) {
            iconPlayPause.setIconLiteral("fa-play");
            Player.getInstance().pauseMedia();
        } else {
            iconPlayPause.setIconLiteral("fa-pause");
            Player.getInstance().playMedia();
        }
    }

    private String formatTime(double timeDouble) {
        if (timeDouble > 0) {
            int hh = (int) (timeDouble / 3600);
            int mm = (int) ((timeDouble % 3600) / 60);
            int ss = (int) ((timeDouble % 3600) % 60);

            return String.format("%02d:%02d:%02d", hh, mm, ss);
        }

        return "00:00:00";
    }

    public void changeSpeed(ActionEvent event){
        switch(speedChoiceBox.getValue()){
            case "0.50x":
                Player.getInstance().getMediaPlayer().setRate(0.5);
                Player.getInstance().setRate(0.5);
                break;

            case "0.75x":
                Player.getInstance().getMediaPlayer().setRate(0.75);
                Player.getInstance().setRate(0.75);
                break;

            case "1.00x":
                Player.getInstance().getMediaPlayer().setRate(1.0);
                Player.getInstance().setRate(1.0);
                break;

            case "1.25x":
                Player.getInstance().getMediaPlayer().setRate(1.25);
                Player.getInstance().setRate(1.25);
                break;

            case "1.50x":
                Player.getInstance().getMediaPlayer().setRate(1.5);
                Player.getInstance().setRate(1.5);
                break;

            case "2.00x":
                Player.getInstance().getMediaPlayer().setRate(2.0);
                Player.getInstance().setRate(2.0);
                break;
        }

        //Player.getInstance().getMediaPlayer().setRate(Integer.parseInt(speedComboBox.getValue())*0.01);
        //Player.getInstance().getMediaPlayer().setRate(Integer.parseInt(speedComboBox.getValue().substring(0, speedComboBox.getValue().length() - 1)) * 0.01);
    }

    private void setMediaSliderEnd() {
        mediaSlider.setMax(Player.getInstance().getEndMediaTime());
        //durationMediaPlayed.setText(CalcolaTempo(Player.getInstance().getEnd()));
        //System.out.println(CalcolaTempo(Player.getInstance().getEnd()));
    }


    private void changeButtonEnabledStatus() {
        boolean status;
        if (Player.getInstance().isMediaLoaded()) {
            status = false;
        } else
            status = true;
        plsPrevious.setDisable(status);
        plsEquilizer.setDisable(status);
        plsSkipBack.setDisable(status);
        plsSkipForward.setDisable(status);
        plsNext.setDisable(status);
        plsPlayPause.setDisable(status);
        plsProperties.setDisable(status);
        plsRepeat.setDisable(status);
        plsShuffle.setDisable(status);
        speedChoiceBox.setDisable(status);
        volumeButton.setDisable(status);
        mediaSlider.setDisable(status);
        volumeSlider.setDisable(status);
        speedChoiceBox.setDisable(status);
    }



    private void setMediaSlider() {
        mediaSlider.setValue(Player.getInstance().getCurrentMediaTime());
    }

    public void switchPlayPauseIcon() {
        if (Player.getInstance().getIsRunning())
            iconPlayPause.setIconLiteral("fa-pause");
        else {
            iconPlayPause.setIconLiteral("fa-play");
        }

    }

    private void muteUnmuteHandler() {
        // TODO: 15/06/2022 Tenersi da parte una variabile con il volume corrente quando si preme il muto, così da ripristinarla in seguito
        if (Player.getInstance().getMediaPlayer().isMute()) {
            Player.getInstance().getMediaPlayer().setMute(false);
            volumeSlider.setValue(Player.getInstance().getUnmuteVolumeValue());
        } else {
            System.out.println(Player.getInstance().getMediaPlayer().getVolume());
            Player.getInstance().setUnmuteVolumeValue(volumeSlider.getValue());
            volumeSlider.setValue(0);
            Player.getInstance().getMediaPlayer().setMute(true);
        }
    }

    private void volumeChange(int offset) {
        if (volumeSlider.getValue() + offset < 0)
            volumeSlider.setValue(0);
        else if (volumeSlider.getValue() + offset > 100)
            volumeSlider.setValue(100);
        else
            volumeSlider.setValue(volumeSlider.getValue() + offset);
    }



    /*public void setKeyEvent() {

        myBorderPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode key = keyEvent.getCode();


                if (key == KeyCode.SPACE && Player.getInstance().isMediaLoaded()) { //Space	Play/pause
                    plsPlayPause.requestFocus();
                    playPauseHandler();
                } else if (key == KeyCode.S) {//S	Stop
                    Player.getInstance().stop();
                } else if (key == KeyCode.N) {//N	Next track
                    next();
                } else if (key == KeyCode.P) {//P	Previous track
                    previous();
                } else if (key == KeyCode.L) { //L	Normal/loop/repeat
                    plsRepeat.fire();
                } else if (key == KeyCode.T) {//T	Shuffle
                    plsShuffle.fire();
                } else if (key == KeyCode.M) {//M	Mute
                    muteUnmuteHandler();
                } else if (KeyCombo.skipBack.match(keyEvent)) {//ALT + LEFT  Skip back 10s
                    skipBack();
                } else if (KeyCombo.skipForward.match(keyEvent)) {//ALT + RIGHT  Skip forward 10s
                    skipForward();
                } else if (KeyCombo.volumeUp.match(keyEvent)) {//CTRL + UP  Volume up 10%
                    volumeChange(10);
                } else if (KeyCombo.volumeDown.match(keyEvent)) {//CTRL + DOWN  Volume down 10%
                    volumeChange(-10);
                } else if (KeyCombo.quit.match(keyEvent)) {//CTRL + Q  Quit application
                    quit();
                }



            }
        });
        //END FUNCTION CALLED AFTER A LISTENER OR OTHER EVENT
    }

    */
}

