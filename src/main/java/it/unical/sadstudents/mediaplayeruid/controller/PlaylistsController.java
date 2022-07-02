package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.PlayList;
import it.unical.sadstudents.mediaplayeruid.view.RecentMedia;
import it.unical.sadstudents.mediaplayeruid.view.RightClickHandler;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

import java.awt.*;
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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Playlists.getInstance().getPlayListsCollections().size()<2){
            Playlists.getInstance().createNewPlayList();
            Playlists.getInstance().createNewPlayList();
        }
        setContentTilePane();
        SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> setContentTilePane());
    }

    private void setContentTilePane(){
        tilePane.getChildren().clear();
        int size= Playlists.getInstance().getPlayListsCollections().size();
        for (int i= 0; i<size; ++i){
            System.out.println("creato");
            PlayList playList = new PlayList(Playlists.getInstance().getPlayListsCollections().get(i));
            playList.setFocusTraversable(true);
            playList.setDim(setDimTilePane());
            tilePane.getChildren().add(playList);
            System.out.println("ciao");
        }

    }

    private  double setDimTilePane(){
        double tilePaneSize = (SceneHandler.getInstance().getStage().getWidth())-350;
        return tilePaneSize;
        //int numberOfColumns = ((int)tilePaneSize)/600;
        //tilePane.setPrefColumns(numberOfColumns);

    }

}

