package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.utils.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class ExitController implements Initializable {

    @FXML
    private ProgressBar progressBarLoading;

    @FXML
    private Label progressType;

    @FXML
    private ProgressBar saving;

    @FXML
    private Label savingLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(SceneHandler.getInstance().getMediaLoadingInProgess()){
            ThreadManager.getInstance().progressBarUpdate(progressBarLoading,"media");
            progressType.setText("MEDIA LOADING IN PROGRESS");
            SceneHandler.getInstance().metadataLoadindagInProgessProperty().addListener(observable -> {
                if(SceneHandler.getInstance().isMetadataLoadindagInProgess())
                {
                    progressType.setText("METADATA LOADING IN PROGRESS");
                    ThreadManager.getInstance().progressBarUpdate(progressBarLoading,"meta");
                }
            });
        }
        else{
            progressType.setText("NOTHING IN UPLOADING");
            progressBarLoading.setProgress(100);
        }

        SceneHandler.getInstance().canStartSavingProperty().addListener(observable ->
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ThreadManager.getInstance().progressBarUpdate(saving,"saving");
                    }
                }));

        Player.getInstance().isRunningProperty().addListener(observable -> {
            if(Player.getInstance().getMediaPlayer()!=null)
                Player.getInstance().stop();

        });





    }
}
