package it.unical.sadstudents.mediaplayeruid.controller;


import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import it.unical.sadstudents.mediaplayeruid.view.SubStageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private Label lblCredits;

    @FXML
    private Button colorBlindMode, darkMode, lightMode, btnHotkeysGuide;

    @FXML
    private ImageView imvLogo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblCredits.setText( "Version 1.0.0"+ System.lineSeparator() +
                            "Software developed in June/July 2022 by the " + "SadStudents" +" group:" + System.lineSeparator() +
                            "    > Ernesto Rapisarda" + System.lineSeparator() +
                            "    > Alessandro Monetti" + System.lineSeparator() +
                            "    > Andrea Tocci" + System.lineSeparator() +
                            "As User Interfaces Design's final project." + System.lineSeparator() +
                            "Dedicated to Mina & Celentano.");

        imvLogo.setImage(new Image(MainApplication.class.getResourceAsStream("image/logoMediaPlayerUID-125x125px.png")));
        setTooltips();

        SceneHandler.getInstance().scaleTransition(colorBlindMode);
        SceneHandler.getInstance().scaleTransition(darkMode);
        SceneHandler.getInstance().scaleTransition(lightMode);
        SceneHandler.getInstance().scaleTransition(btnHotkeysGuide);
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
    void onColorBlind(ActionEvent event) { SceneHandler.getInstance().changeTheme("color-blind"); }

    @FXML
    void onHotkeysGuide(ActionEvent event) {
        hotkeysGuide();
    }

    private void hotkeysGuide(){
        SubStageHandler.getInstance().init("hotkeys-guide-view.fxml",500,470,"Hotkeys Guide",false,"");
    }

    private void setTooltips(){
        darkMode.setTooltip(new Tooltip("Dark mode"));
        lightMode.setTooltip(new Tooltip("Light mode (alt. Unical Mode)"));
        colorBlindMode.setTooltip(new Tooltip("Color blind mode (Okabe Ito palette)"));
    }
}
