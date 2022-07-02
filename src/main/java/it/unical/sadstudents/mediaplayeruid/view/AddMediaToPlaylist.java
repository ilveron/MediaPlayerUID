package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.controller.AddMediaToPlaylistController;
import it.unical.sadstudents.mediaplayeruid.controller.PlaylistTemplateController;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.Playlist;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class AddMediaToPlaylist extends Pane {
    private String image;
    private String name;
    //private ObservableList<MyMedia> list;
    private AddMediaToPlaylistController controller;

    public AddMediaToPlaylist(ObservableList<MyMedia> list,String image, String title){
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("addMediaToPlaylist.fxml"));
        try{
            AnchorPane root = loader.load();
            controller = loader.getController();
            //controller.setData(list,image, title);
            this.getChildren().add(root);
        }catch(Exception ignoredException){}

    }
    /*
    public Playlist getPlaylist() {
        return playlist;
    }

    public void setDim(double size){
        playlistTemplateController.setDim(size);
    }*/
}
