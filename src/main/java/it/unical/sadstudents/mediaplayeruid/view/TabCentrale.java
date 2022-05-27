package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.Pane;

import java.io.IOException;

public class TabCentrale {
    private static TabCentrale instance = null;
    private Pane subScene;

    public static TabCentrale getInstance(){
        if (instance==null)
            instance = new TabCentrale();
        return instance;
    }

    private TabCentrale(){    }

    public Pane init (){
        try{
            subScene = new FXMLLoader().load(MainApplication.class.getResource("home-view.fxml"));

        }catch (IOException  e){}
        return subScene;
    }

    public Pane MusicLibrary(){
        try {
            subScene = new FXMLLoader().load(MainApplication.class.getResource("music-library-view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return subScene;
    }

    public Pane VideoLibrary(){
        try {
            subScene = new FXMLLoader().load(MainApplication.class.getResource("video-library-view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return subScene;
    }

    public Pane PlayQueue(){
        try {
            subScene = new FXMLLoader().load(MainApplication.class.getResource("play-queue-view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return subScene;
    }

    public Pane Playlists(){
        try {
            subScene = new FXMLLoader().load(MainApplication.class.getResource("playlist-view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return subScene;
    }





}
