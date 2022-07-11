package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.controller.CreateNewPlaylistController;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CreateNewPlaylist {
    private String image="";
    private String name="";
    FXMLLoader loader ;
    Scene scene;
    Stage stage;


    private static CreateNewPlaylist instance = null;
    private CreateNewPlaylist(){}
    public static CreateNewPlaylist getInstance(){
        if (instance==null)
            instance = new CreateNewPlaylist();
        return instance;
    }

    public void createPlaylist(String image,String name){
        this.image=image;
        this.name=name;
        loader = new FXMLLoader(MainApplication.class.getResource("new-playlist-view.fxml"));
        try{
            scene= new Scene(loader.load(),300,400);
            stage=new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Create Playlist");
            stage.setMinHeight(300);
            stage.setMinWidth(400);
            stage.setMaxHeight(300);
            stage.setMaxWidth(400);
            stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("image/logoMediaPlayerUID-48x48.png")));
            stage.setScene(scene);
            stage.showAndWait();
        }catch(Exception ignoredException){return ;}
    }

    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
