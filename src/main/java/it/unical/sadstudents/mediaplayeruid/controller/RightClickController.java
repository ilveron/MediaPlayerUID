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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RightClickController {
    String source;
    MyMedia myMedia;

    @FXML
    private Button delFrom, playfromBtn;



    @FXML
    void deleteFrom(ActionEvent event) {
       actionDeleteFrom();
    }

    @FXML
    void playFrom(ActionEvent event) {
        actionPlayFrom();
    }

    public void init (MyMedia myMedia, String source){
        this.source=source;
        this.myMedia=myMedia;
        if (source=="Home"){
            delFrom.setText("Delete from Recent");
            playfromBtn.setText("Play");

        }
    }


    public void actionDeleteFrom(){
        if(source=="Home"){
            for (int i=0; i< Home.getInstance().getRecentMedia().size();i++){
                if(Home.getInstance().getRecentMedia().get(i).equals(myMedia)){
                    Home.getInstance().getRecentMedia().remove(i);
                    Home.getInstance().setChangeHappened(true);

                }
            }
        }
    }

    public void actionPlayFrom(){
        if(source=="Home"){
            PlayQueue.getInstance().generateNewQueue(myMedia);
        }

    }



}
