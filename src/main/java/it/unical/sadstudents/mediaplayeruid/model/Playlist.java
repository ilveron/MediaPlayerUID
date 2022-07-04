package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.controller.PlaylistTemplateController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class Playlist  {

    private ObservableList<MyMedia> list;
    private String image;
    private String name;


    public Playlist(String name, String image){
        list= FXCollections.observableArrayList();
        this.image=image;
        this.name=name;
    }


    public void addMedia(MyMedia myMedia){
        list.add(myMedia);
    }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(list, playlist.list) && Objects.equals(image, playlist.image) && Objects.equals(name, playlist.name);
    }
}

