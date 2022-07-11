package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.SinglePlaylistView;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import it.unical.sadstudents.mediaplayeruid.view.SubStageHandler;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistCollectionController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TilePane tilePane;

    @FXML
    private Button ButtonCreatePlaylist;


    @FXML
    void onCreatePlaylist(ActionEvent event) {
        int pos=PlaylistCollection.getInstance().createNewPlaylist();
        setContentTilePane();
        String name=PlaylistCollection.getInstance().getPlayListsCollections().get(pos).getName();
        SubStageHandler.getInstance().init("new-playlist-view.fxml",400,240,"Playlist editor",false,name);
        setContentTilePane();
        //int posPlaylist=Playlists.getInstance().createNewPlaylist();
       /* CreateNewPlaylist.getInstance().createPlaylist(Playlists.getInstance().getPlayListsCollections().get(posPlaylist).getImage(),Playlists.getInstance().getPlayListsCollections().get(posPlaylist).getName());
        Playlists.getInstance().getPlayListsCollections().get(posPlaylist).setImage(CreateNewPlaylist.getInstance().getImage());
        Playlists.getInstance().getPlayListsCollections().get(posPlaylist).setName(CreateNewPlaylist.getInstance().getName());
        setContentTilePane();*/
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        if(Playlists.getInstance().getPlayListsCollections().size()<2){
            Playlists.getInstance().createNewPlayList();
            Playlists.getInstance().createNewPlayList();
        }*/
        setContentTilePane();
        SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> setContentTilePane());
        PlaylistCollection.getInstance().updatePlaylistProperty().addListener(observable -> {
            if (PlaylistCollection.getInstance().isUpdatePlaylist()) {
                setContentTilePane();
                System.out.println("AGGIORNAMENTO");
                //PlaylistCollection.getInstance().setUpdatePlaylist(false);
            }
        });

        SceneHandler.getInstance().menuHoverProperty().addListener(observable -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setContentTilePane();
                }
            });
        });



        SceneHandler.getInstance().scaleTransition(ButtonCreatePlaylist);
    }

    private void setContentTilePane(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tilePane.getChildren().clear();
                int size= PlaylistCollection.getInstance().getPlayListsCollections().size();
                for (int i= 0; i<size; ++i){
                    //System.out.println("creato");
                    SinglePlaylistView playList = new SinglePlaylistView(PlaylistCollection.getInstance().getPlayListsCollections().get(i));
                    playList.setFocusTraversable(true);
                    playList.setDim(setDimTilePane());
                    tilePane.getChildren().add(playList);
                    //System.out.println("ciao");
                }
                PlaylistCollection.getInstance().setUpdatePlaylist(false);
            }
        });

    }

    private  double setDimTilePane(){
        double tilePaneSize;
        if(SceneHandler.getInstance().isMenuHover())
            tilePaneSize = (SceneHandler.getInstance().getStage().getWidth())-350;
        else{
            tilePaneSize =(SceneHandler.getInstance().getStage().getWidth())-150;
        }
        return tilePaneSize;
        //int numberOfColumns = ((int)tilePaneSize)/600;
        //tilePane.setPrefColumns(numberOfColumns);
    }
}

