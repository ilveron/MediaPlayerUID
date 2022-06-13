package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.Home;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.view.RightClickHandler;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RightClickController implements Initializable {


    @FXML
    void deleteFromRecent(ActionEvent event) {
        if(RightClickHandler.getInstance().getSource()=="Home"){
            for (int i=0; i< Home.getInstance().getRecentMedia().size();i++){
                if(Home.getInstance().getRecentMedia().get(i).equals(RightClickHandler.getInstance().getMyMedia())){
                    Home.getInstance().getRecentMedia().remove(i);
                }
            }
        }
        RightClickHandler.getInstance().getStageRightClick().close();

    }

    @FXML
    void playFromRecent(ActionEvent event) {
        PlayQueue.getInstance().generateNewQueue(RightClickHandler.getInstance().getMyMedia());
        RightClickHandler.getInstance().getStageRightClick().close();


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String source = RightClickHandler.getInstance().getSource();

    }
}
