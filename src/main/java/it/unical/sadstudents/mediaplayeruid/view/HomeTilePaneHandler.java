package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.controller.HomeController;
import it.unical.sadstudents.mediaplayeruid.model.Home;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.thread.ImageCreator;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collections;

public class HomeTilePaneHandler {
    private SimpleIntegerProperty readyInteger = new SimpleIntegerProperty(0);
    private ArrayList<RecentMedia> recentMedias = new ArrayList<>();


    //SINGLETON
    private static HomeTilePaneHandler instance = null;
    private HomeTilePaneHandler(){
        Home.getInstance().changeHappenedProperty().addListener(observable -> {
            // TODO: 05/07/2022
        });
    }
    public static HomeTilePaneHandler getInstance(){
        if (instance==null)
            instance = new HomeTilePaneHandler();
        return instance;
    }
    //END SINGLETON


    public int getReadyInteger() {
        return readyInteger.get();
    }

    public SimpleIntegerProperty readyIntegerProperty() {
        return readyInteger;
    }

    public void setReadyInteger(int readyInteger) {
        this.readyInteger.set(readyInteger);
    }

    public ArrayList<RecentMedia> getRecentMedias() {
        return recentMedias;
    }

    public void setRecentMedias(ArrayList<RecentMedia> recentMedias) {
        this.recentMedias = recentMedias;
    }

    public void listCreator(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<Home.getInstance().getRecentMedia().size(); i++){
                    RecentMedia recentMedia = new RecentMedia(Home.getInstance().getRecentMedia().get(i),"home");
                    recentMedias.add(recentMedia);
                    readyInteger.set(readyInteger.get()+1);
                }


                for (int i = 0; i< recentMedias.size(); ++i){
                    ImageCreator imageCreator = new ImageCreator();
                    imageCreator.setPane(recentMedias.get(i));
                    imageCreator.start();
                }


            }
        });

        thread.setDaemon(true);
        thread.start();
    }

}
