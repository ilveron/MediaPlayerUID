package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;


import it.unical.sadstudents.mediaplayeruid.controller.PlaylistSingleViewController;
import it.unical.sadstudents.mediaplayeruid.model.Playlist;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SinglePlaylistView extends Pane {
    private Playlist playlist;
    private PlaylistSingleViewController playlistTemplateController;

    public SinglePlaylistView(Playlist playlist){
        this.playlist = playlist;
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("playlist-template.fxml"));
        try{
            AnchorPane root = loader.load();
            playlistTemplateController = loader.getController();
            playlistTemplateController.init(playlist);
            this.getChildren().add(root);
        }catch(Exception ignoredException){

            return ;
        }
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setDim(double size){
     playlistTemplateController.setDim(size);
    }



}
