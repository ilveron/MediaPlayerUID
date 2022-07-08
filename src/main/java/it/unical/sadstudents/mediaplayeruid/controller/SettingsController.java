package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblCredits.setText("Version 1.0.0\nSoftware developed in June/July 2022 by the SadStudents' group:\n    > Ernesto Rapisarda\n    > Alessandro Monetti\n    > Andrea Tocci\nAs User Interfaces Design's final project.\nDedicated to Mina & Celentano.");
    }

    @FXML
    private Label lblCredits;

    @FXML
    private Button darkMode;

    @FXML
    private Button lightMode;

    @FXML
    void onLightMode(ActionEvent event) {
        SceneHandler.getInstance().changeTheme("unical");
    }

    @FXML
    void onDarkMode(ActionEvent event) {
        SceneHandler.getInstance().changeTheme("dark");
    }

    @FXML
    void onHotkeysGuide(ActionEvent event) {

    }


}
