package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class MiddlePaneHandler {
    private static MiddlePaneHandler instance = null;
    private SimpleStringProperty currentMidPane = new SimpleStringProperty("home-view.fxml"); //aggiunto per switchautomatico
    private Pane subScene;

    public static MiddlePaneHandler getInstance(){
        if (instance==null)
            instance = new MiddlePaneHandler();
        return instance;
    }

    private MiddlePaneHandler(){    }

    public String getCurrentMidPane() {
        return currentMidPane.get();
    }

    public SimpleStringProperty currentMidPaneProperty() {
        return currentMidPane;
    }

    public void setCurrentMidPane(String currentMidPane) {
        this.currentMidPane.set(currentMidPane);
    }

    public Pane switchPane(){
        try{
            subScene = new FXMLLoader().load(MainApplication.class.getResource(currentMidPane.get()));
        }catch(IOException e){}

        return subScene;
    }



}
