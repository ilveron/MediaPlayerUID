package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.Home;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.view.Playlist;
import it.unical.sadstudents.mediaplayeruid.view.RecentMedia;
import it.unical.sadstudents.mediaplayeruid.view.RightClickHandler;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
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

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistsController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TilePane tilePane;

    @FXML
    void addFiles(ActionEvent event) {

    }

    @FXML
    void addFolder(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContentTilePane();
        SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> setDimTilePane());
    }
    private void setContentTilePane(){
        tilePane.getChildren().clear();
        Playlist playlist=new Playlist("ciao");
        tilePane.getChildren().add(playlist);
    }

    private  void setDimTilePane(){
        double tilePaneSize = (SceneHandler.getInstance().getStage().getWidth())-310;
        int numberOfColumns = ((int)tilePaneSize)/230;
        tilePane.setPrefColumns(numberOfColumns);

    }

}

