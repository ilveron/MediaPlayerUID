package it.unical.sadstudents.mediaplayeruid.controller;


import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import it.unical.sadstudents.mediaplayeruid.view.SubStageHandler;
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
        lblCredits.setText( "Version 1.0.0"+ System.lineSeparator() +
                            "Software developed in June/July 2022 by the SadStudents' group:" + System.lineSeparator() +
                            "    > Ernesto Rapisarda" + System.lineSeparator() +
                            "    > Alessandro Monetti" + System.lineSeparator() +
                            "    > Andrea Tocci" + System.lineSeparator() +
                            "As User Interfaces Design's final project." + System.lineSeparator() +
                            "Dedicated to Mina & Celentano.");

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
        hotkeysGuide();
    }

    private void hotkeysGuide(){
        SubStageHandler.getInstance().init("hotkeys-guide-view.fxml",500,470,"Hotkeys Guide",false,"");
    }
}
