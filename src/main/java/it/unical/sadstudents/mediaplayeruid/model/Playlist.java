package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.controller.PlaylistTemplateController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Playlist  {

    private ObservableList<MyMedia> list;
    private String image;
    private String name;


    public Playlist(String name,String image){
        list= FXCollections.observableArrayList();
        this.image=image;
        this.name=name;
    }

    // TODO: 15/06/2022 image di default da gestire


    public ObservableList<MyMedia> getMyList(){return list;}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

