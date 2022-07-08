package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.Settings;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
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
        hotkeysGuide();
    }

    private void hotkeysGuide(){
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("hotkeys-guide-view.fxml"));
        try{
            Scene scene = new Scene(loader.load(),500,470);
            Stage stage = new Stage();
            stage.setResizable(false);

            for (String font : Settings.fonts) {
                Font.loadFont(Objects.requireNonNull(MainApplication.class.getResourceAsStream(font)), 10);
            }

            scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/"+ Settings.theme+".css")).toExternalForm());
            System.out.println(Settings.theme);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Audio Equalizer");
            stage.setMinHeight(470);
            stage.setMinWidth(500);
            stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("image/logoMediaPlayerUID-48x48.png")));
            stage.setScene(scene);
            stage.showAndWait();

            //stage.setOnCloseRequest
        }catch(Exception exception){
            exception.printStackTrace();}
    }
}
