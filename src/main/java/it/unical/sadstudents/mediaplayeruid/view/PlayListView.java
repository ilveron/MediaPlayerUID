package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;


import it.unical.sadstudents.mediaplayeruid.controller.PlaylistTemplateController;
import it.unical.sadstudents.mediaplayeruid.model.Playlist;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PlayListView extends Pane {
    private Playlist playlist;
    private PlaylistTemplateController playlistTemplateController;

    public PlayListView(Playlist playlist){
        this.playlist = playlist;
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("playlist-template.fxml"));
        try{
            AnchorPane root = loader.load();
            playlistTemplateController = loader.getController();
            playlistTemplateController.init(playlist.getMyList(),playlist.getImage(), playlist.getName());
            this.getChildren().add(root);
            //root.prefWidthProperty().bind(this.widthProperty());
        }catch(Exception ignoredException){

        }

    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setDim(double size){
     playlistTemplateController.setDim(size);
    }

}
