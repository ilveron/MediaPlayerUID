package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.controller.PlaylistTemplateController;
import it.unical.sadstudents.mediaplayeruid.controller.RecentMediaTemplateController;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Playlist extends Pane {

    private ObservableList<MyMedia> myMedia;
    private String image;
    private String name;

    public Playlist(String name){
        myMedia= FXCollections.observableArrayList();
        this.name=name;
        create();
    }
    public Playlist(MyMedia m,String name){
        myMedia= FXCollections.observableArrayList();
        myMedia.add(m);
        //image default
        this.name=name;
        create();
    }
    public Playlist(ObservableList<MyMedia> myMedia,String name,String image){
        this.image=image;
        this.name=name;
        this.myMedia = myMedia;
        create();
    }

    // TODO: 15/06/2022 image di default da gestire
    public void create(){
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("playlist-template-view.fxml"));
        try{
            AnchorPane root = loader.load();
            PlaylistTemplateController controller = loader.getController();
            controller.init(myMedia,image,name);
            this.getChildren().add(root);
        }catch(Exception ignoredException){}
    }

    public ObservableList<MyMedia> getMyMedia(){return myMedia;}

}

