package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.model.DataExchangePlaylist;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.Playlist;
import it.unical.sadstudents.mediaplayeruid.model.Playlists;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaylistMedia {
    private Playlist playlist;
    private Scene scene;
    private Stage stage;

    private static PlaylistMedia instance = null;
    private PlaylistMedia(){}
    public static PlaylistMedia getInstance(){
        if (instance==null)
            instance = new PlaylistMedia();
        return instance;
    }
    public void addPlaylist(MyMedia m){
        for (MyMedia myMedia1: playlist.getMyList()){
            if (m.equals(myMedia1))
                return;
        }
        playlist.addMedia(m);
    }

    public boolean changePlaylistMedia(Playlist playlist){
        this.playlist=playlist;
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("addMediaToPlaylist.fxml"));
        try{
            //Scene scene=new Scene(new AddMediaToPlaylist())
            scene= new Scene(loader.load(),700,400);
            stage=new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Playlist");
            stage.setMinHeight(700);
            stage.setMinWidth(400);
            stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("image/logoMediaPlayerUID-48x48.png")));
            //scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/style.css")).toExternalForm());
            //scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/"+ Settings.theme+".css")).toExternalForm());
            stage.setScene(scene);
            stage.showAndWait();
            //root.prefWidthProperty().bind(this.widthProperty());
        }catch(Exception ignoredException){ignoredException.printStackTrace(); return false;}
        return true;
    }

    public Playlist getPlaylist() {
        try {
            return playlist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
