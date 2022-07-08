package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.PlayListView;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistsController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TilePane tilePane;

    @FXML
    private Button ButtonCreatePlaylist;


    @FXML
    void onCreatePlaylist(ActionEvent event) {
        int posPlaylist=Playlists.getInstance().createNewPlaylist();
        setContentTilePane();
        CreateNewPlaylist.getInstance().createPlaylist(Playlists.getInstance().getPlayListsCollections().get(posPlaylist).getImage(),
                                                        Playlists.getInstance().getPlayListsCollections().get(posPlaylist).getName());
        Playlists.getInstance().getPlayListsCollections().get(posPlaylist).setImage(CreateNewPlaylist.getInstance().getImage());
        Playlists.getInstance().getPlayListsCollections().get(posPlaylist).setName(CreateNewPlaylist.getInstance().getName());
        setContentTilePane();
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

        Playlists.getInstance().deleteProperty().addListener(observable -> {
                if(Playlists.getInstance().getDelete()!=-1){
                    Playlists.getInstance().deletePlaylist(Playlists.getInstance().getDelete());
                    Playlists.getInstance().setDelete(-1);
                    setContentTilePane();
                }
        });
    }

    private void setContentTilePane(){
        tilePane.getChildren().clear();
        int size= Playlists.getInstance().getPlayListsCollections().size();
        for (int i= 0; i<size; ++i){
            //System.out.println("creato");
            PlayListView playList = new PlayListView(Playlists.getInstance().getPlayListsCollections().get(i));

            playList.setFocusTraversable(true);
            playList.setDim(setDimTilePane());
            tilePane.getChildren().add(playList);
            //System.out.println("ciao");
        }

    }

    private  double setDimTilePane(){
        double tilePaneSize = (SceneHandler.getInstance().getStage().getWidth())-350;
        return tilePaneSize;
        //int numberOfColumns = ((int)tilePaneSize)/600;
        //tilePane.setPrefColumns(numberOfColumns);
    }

}

