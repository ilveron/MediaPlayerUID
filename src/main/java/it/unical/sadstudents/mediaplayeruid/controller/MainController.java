package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.Settings;
import it.unical.sadstudents.mediaplayeruid.keyCombo;
import it.unical.sadstudents.mediaplayeruid.thread.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.view.HomeTilePaneHandler;
import it.unical.sadstudents.mediaplayeruid.view.MediaInfo;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.Objects;
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
    private ProgressBar progressBarLoading;
    @FXML
    private VBox vBoxProgressBar;
    @FXML
    private VBox controllePlayer;
    @FXML
    private ChoiceBox<String> speedChoiceBox;
    @FXML
    private ImageView miniImageView;

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
    private Label currentMediaTimeLabel, mediaNameLabel, endMediaTimeLabel, artistNameLabel,progressType;
    @FXML
    private ToolBar toolbarMenu;
    @FXML
    private Button plsEquilizer, plsNext, plsPlayPause, plsPrevious, plsProperties,
            plsScreenMode, plsSkipBack,  plsSkipForward, volumeButton,lightMode, darkMode, about,
            btnHome, btnMusicLibrary, btnVideoLibrary, btnPlayQueue, btnPlaylists;
    @FXML
    private ToggleButton plsShuffle;
    @FXML
    private ToggleButton plsRepeat;

    @FXML
    private FontIcon volumeIcon;
    @FXML
    private StackPane stackPane;
    @FXML
    private FontIcon repeatIcon;
    @FXML
    private FontIcon shuffleIcon;

    @FXML
    private StackPane centralStackPane;

    @FXML
    private AnchorPane infoMediaAnchor;

    @FXML
    private Pane mediaInfoPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FIRST SETUP OF ITEMS
        startToolTip();
        double percentage = 100.0;// * (newValue.doubleValue() - volumeSlider.getMin()) / (volumeSlider.getMax() - volumeSlider.getMin());
        volumeSlider.setStyle("-track-color: linear-gradient(to right, tertiarySelectionColor " + percentage + "%, white " + percentage + ("%);"));
        Player.getInstance().setMediaView(mediaView);
        switchMidPane();
        setKeyEvent();
        //END FIRST SETUP

        //speedComboBox.butt


        //START LISTENER VARI
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
        SceneHandler.getInstance().currentMidPaneProperty().addListener(observable -> switchMidPane());
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
                if(!infoMediaAnchor.isVisible())
                    infoMediaAnchor.setVisible(true);
            }
            else{
                infoMediaAnchor.setVisible(false);
            }
        });

        Player.getInstance().isRunningProperty().addListener(observable -> {
            switchPlayPauseIcon();
            plsPlayPause.setTooltip(new Tooltip("Pause"));
            if(Player.getInstance().getIsRunning() && Player.getInstance().isMediaLoaded()){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MediaInfo mediaInfo = new MediaInfo(PlayQueue.getInstance().getQueue().get(PlayQueue.getInstance().getCurrentMedia()));

                        mediaInfoPane.getChildren().add(mediaInfo);
                        int index= HomeTilePaneHandler.getInstance().getMyMediaSingleBoxes().size()-1;
                        mediaInfo.setImage(HomeTilePaneHandler.getInstance().getMyMediaSingleBoxes().get(index).getImage());
                        miniImageView.setImage(HomeTilePaneHandler.getInstance().getMyMediaSingleBoxes().get(index).getImage());
                    }
                });
            }


        });
        Player.getInstance().currentMediaTimeProperty().addListener(observable -> {
            setMediaSlider();
            currentMediaTimeLabel.setText(formatTime(Player.getInstance().getCurrentMediaTime()));
            if (Player.getInstance().getCurrentMediaTime() == Player.getInstance().getEndMediaTime()) {
                PlayQueue.getInstance().changeMedia(1);
            }
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
            setMediaSliderEnd();
            endMediaTimeLabel.setText(formatTime(Player.getInstance().getEndMediaTime()));
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

        SceneHandler.getInstance().metadataLoadindagInProgessProperty().addListener(observable -> {
                if(SceneHandler.getInstance().isMetadataLoadindagInProgess())
                {
                    progressType.setText("LOADING METADATA IN PROGRESS");
                    ThreadManager.getInstance().progressBarUpdate(progressBarLoading,"meta");
                }
        });



        Player.getInstance().mediaNameProperty().addListener(observable -> mediaNameLabel.setText(Player.getInstance().getMediaName()));
        PlayQueue.getInstance().isAVideoProperty().addListener(observable -> activeVideoView());
        // TODO: 15/06/2022 why??
        Player.getInstance().artistNameProperty().addListener(observable -> artistNameLabel.setText(Player.getInstance().getArtistName()));
        Player.getInstance().isRunningProperty().addListener(observable -> formatTime(Player.getInstance().getCurrentMediaTime()));
        //END LISTENER VARI


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
    void onLightMode(ActionEvent event) {
        SceneHandler.getInstance().changeTheme("unical");
    }

    @FXML
    void onDarkMode(ActionEvent event) {
        SceneHandler.getInstance().changeTheme("dark");
    }

    @FXML
    void onAbout(ActionEvent event) {
        // TODO: 15/06/2022 Fare quello che ci si aspetta in about
        System.out.println("About");
    }

    //ACTION EVENT MEDIA CONTROLS BAR
    @FXML
    void onVolumeButtonAction(ActionEvent event) {
        muteUnmuteHandler();
    }

    @FXML
    void onShuffle(ActionEvent event) {
        // TODO: 07/06/2022  se stai facendo girare un video in full screen e attivi onShuffle e skippi si bagga tutto X/
        if (PlayQueue.getInstance().isShuffleActive()) {
            PlayQueue.getInstance().setShuffleActive(false);
        } else {
            PlayQueue.getInstance().setShuffleActive(true);
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
        myBorderPane.requestFocus();
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
        screenModeHandler();
    }

    Service<Void> service = null;
    private void screenModeHandler() {



        if (!SceneHandler.getInstance().getStage().isFullScreen()) {
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
            service.start();

            leftItems.setVisible(false);
            AnchorPane.setLeftAnchor(containerView, 0.0);
            AnchorPane.setBottomAnchor(containerView, 0.0);
            SceneHandler.getInstance().getStage().setFullScreen(true);
            adjustVideoSize();

            SceneHandler.getInstance().getScene().setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    controllePlayer.setVisible(true);
                    if(!service.isRunning() && SceneHandler.getInstance().getStage().isFullScreen()){
                        System.out.println("restart");
                        service.restart();
                    }



                }
            });


            SceneHandler.getInstance().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyCode key = keyEvent.getCode();
                    if (key == KeyCode.ESCAPE) {
                        leftItems.setVisible(true);
                        AnchorPane.setLeftAnchor(containerView, 270.0);
                        AnchorPane.setBottomAnchor(containerView, 96.00);
                        service.cancel();
                        adjustVideoSize();
                    }
                }
            });

        } else {
            leftItems.setVisible(true);
            AnchorPane.setLeftAnchor(containerView, 270.0);
            AnchorPane.setBottomAnchor(containerView, 96.00);
            SceneHandler.getInstance().getStage().setFullScreen(false);
            service.cancel();
            adjustVideoSize();
        }
    }

   /* @FXML
    void onSpeedPlay(ActionEvent event) {

    }*/

    @FXML
    void onEquilizer(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("audioEqualizer-view.fxml"));
        try{
            Scene scene = new Scene(loader.load(),484,280);
            Stage stage = new Stage();

            for (String font : Settings.fonts) {
                System.out.println();
                Font.loadFont(Objects.requireNonNull(MainApplication.class.getResourceAsStream(font)), 10);
            }

            scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/"+ Settings.theme+".css")).toExternalForm());
            System.out.println(Settings.theme);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Audio Equalizer");
            stage.setMinHeight(280);
            stage.setMinWidth(484);
            stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("image/logoMediaPlayerUID-48x48.png")));
            stage.setScene(scene);
            stage.showAndWait();

            //stage.setOnCloseRequest
        }catch(Exception exception){
            exception.printStackTrace();}



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

    // TODO: 15/06/2022 RIMUOVERE TUTTO FINO A PROSSIMO COMMENTO
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
    // TODO: 15/06/2022 fino a qui (ASSICURARSI DI TOGLIERLI ANCHE DA SCENE BUILDER)

    //END ACTION EVENT MEDIA CONTROLS BAR


    // TODO: 06/06/2022 RENDERE INVISIBILE VIDEO VIEW DOPO CANCELLAZIONE DELLA PLAY QUEUE 
    //FUNCTION CALLED AFTER A LISTENER OR OTHER EVENT
    public void activeVideoView() {
        if (PlayQueue.getInstance().getIsAVideo()) {
            mediaView.setVisible(true);
            btnVideoView.setVisible(true);
            myBorderPane.getCenter().setVisible(false);
            plsScreenMode.setDisable(false);
            adjustVideoSize();
            SceneHandler.getInstance().getStage().heightProperty().addListener(observable -> adjustVideoSize());
            SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> adjustVideoSize());
            Player.getInstance().isRunningProperty().addListener(observable -> adjustVideoSize());

        } else {
            mediaView.setVisible(false);
            btnVideoView.setVisible(false);
            myBorderPane.getCenter().setVisible(true);
            plsScreenMode.setDisable(true);
        }
    }

    public void adjustVideoSize() {
        if (Player.getInstance().getIsRunning()) {
            double currentWidth;
            double currentHeight;
            double controllBar = 96.0;
            double menuSize = 270.0;
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

    private void switchMidPane() {
        if (SceneHandler.getInstance().getCurrentMidPane() == "video-view.fxml") {
            btnVideoView.setDisable(false);
        }
        Timeline timeline = new Timeline();
        if(centralStackPane.getChildren().size() <= 1){
            centralStackPane.getChildren().add(SceneHandler.getInstance().switchPane());
        }

        // TODO: 03/07/2022 CLIC RIPETUTI BUGGANO TUTTO
        if(centralStackPane.getChildren().size() == 2){
            toolbarButtonDisable(true);
            //subScene = new FXMLLoader().load(MainApplication.class.getResource(currentMidPane.get()));
            TranslateTransition translateTransitionOld = new TranslateTransition(Duration.seconds(0.75));
            translateTransitionOld.setNode(centralStackPane.getChildren().get(0));
            translateTransitionOld.setFromY(0);
            translateTransitionOld.setToY(-SceneHandler.getInstance().getScene().getHeight());
            translateTransitionOld.setInterpolator(Interpolator.EASE_BOTH);

            TranslateTransition translateTransitionNew = new TranslateTransition(Duration.seconds(0.75));
            translateTransitionNew.setNode(centralStackPane.getChildren().get(1));
            translateTransitionNew.setFromY(SceneHandler.getInstance().getScene().getHeight());
            translateTransitionNew.setToY(0);
            translateTransitionNew.setInterpolator(Interpolator.EASE_BOTH);

            translateTransitionOld.play();
            translateTransitionNew.play();

            translateTransitionNew.setOnFinished(event -> {
                centralStackPane.getChildren().remove(0);
                toolbarButtonDisable(false);
            });


        }
        //stackPane.getChildren().get()
        //myBorderPane.setCenter(centralStackPane);
        //subScenePane.autosize();
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

    private void toolbarButtonDisable(boolean status){
        btnHome.setDisable(status);
        btnMusicLibrary.setDisable(status);
        btnVideoLibrary.setDisable(status);
        btnPlayQueue.setDisable(status);
        btnPlaylists.setDisable(status);
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

    private void quit() {
        // TODO: 15/06/2022 Controlli prima di uscire dall'applicazione
        Platform.exit();
    }

    public void setKeyEvent() {

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
                } else if (keyCombo.skipBack.match(keyEvent)) {//ALT + LEFT  Skip back 10s
                    skipBack();
                } else if (keyCombo.skipForward.match(keyEvent)) {//ALT + RIGHT  Skip forward 10s
                    skipForward();
                } else if (keyCombo.volumeUp.match(keyEvent)) {//CTRL + UP  Volume up 10%
                    volumeChange(10);
                } else if (keyCombo.volumeDown.match(keyEvent)) {//CTRL + DOWN  Volume down 10%
                    volumeChange(-10);
                } else if (keyCombo.quit.match(keyEvent)) {//CTRL + Q  Quit application
                    quit();
                }

                myBorderPane.requestFocus();


            }
        });
        //END FUNCTION CALLED AFTER A LISTENER OR OTHER EVENT
    }

}
