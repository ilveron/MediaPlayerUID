package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.Settings;
import it.unical.sadstudents.mediaplayeruid.model.PlaylistCollection;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

public class SubStageHandler {
    private Stage stage;
    private Scene scene;
    private Alert alert;
    private String playlistName;
    private boolean updated = false;

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    //SINGLETON
    private static SubStageHandler instance = null;
    private SubStageHandler(){ alert = new Alert(Alert.AlertType.NONE);   }
    public static SubStageHandler getInstance(){
        if (instance==null)
            instance = new SubStageHandler();
        return instance;
    }
    //END SINGLETON


    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void init(String source, double width, double height, String title, boolean resizable, String playlistName){
        this.playlistName = playlistName;
        updated=false;
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(source));
        try{
            scene = new Scene(loader.load(),width,height);
            stage = new Stage();
            stage.setResizable(resizable);

            for (String font : Settings.fonts) {
                Font.loadFont(Objects.requireNonNull(MainApplication.class.getResourceAsStream(font)), 10);
            }

            scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/"+ Settings.theme+".css")).toExternalForm());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("image/logoMediaPlayerUID-48x48.png")));
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                   if(source=="new-playlist-view.fxml" ){
                       int index = PlaylistCollection.getInstance().getPlaylistWidthName(playlistName);
                       if(PlaylistCollection.getInstance().getPlayListsCollections().get(index).isInitialized()){
                           if(!SceneHandler.getInstance().showConfirmationAlert("Discard changes?")) {
                               windowEvent.consume();
                               stage.show();
                           }

                       }else{
                           if(updated){
                               if(!SceneHandler.getInstance().showConfirmationAlert("Do you really want to close?"+System.lineSeparator()+ "The playlist will not be created.")) {
                                   windowEvent.consume();
                                   stage.show();
                               }
                               else{
                                   PlaylistCollection.getInstance().deletePlaylist(PlaylistCollection.getInstance().getPlayListsCollections().size()-1);
                               }
                           }
                           else{
                               PlaylistCollection.getInstance().deletePlaylist(PlaylistCollection.getInstance().getPlayListsCollections().size()-1);
                           }
                       }

                   }
                   else if(source=="addMediaToPlaylist-view.fxml" && updated){
                       if(!SceneHandler.getInstance().showConfirmationAlert("Discard changes?")) {
                           windowEvent.consume();
                           stage.show();
                       }
                   }
                }
            });
        }catch(Exception exception){}

    }
}
