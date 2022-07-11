package it.unical.sadstudents.mediaplayeruid.controller;

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

    }
}
