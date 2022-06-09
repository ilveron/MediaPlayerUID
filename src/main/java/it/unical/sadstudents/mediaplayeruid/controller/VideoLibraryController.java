package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.RecentMedia;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class VideoLibraryController  implements Initializable {
    @FXML
    private Button btnAddLibraryToQueue;

    @FXML
    private Button btnAddVideoToQueue;

    @FXML
    private Button btnDelete;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TilePane tilePane;

    @FXML
    void onAddFolder(ActionEvent event) {
        VideoLibrary.getInstance().addFilesToList(RetrievingEngine.getInstance().retrieveFolder(2));
    }

    @FXML
    void onAddMedia(ActionEvent event) {
        VideoLibrary.getInstance().addFilesToList(RetrievingEngine.getInstance().retrieveFile(2));

    }


    @FXML
    void onAddLibraryToQueue(ActionEvent event) {
        for(int i=0;i<MusicLibrary.getInstance().getKMusic();i++){
            PlayQueue.getInstance().addFileToListFromOtherModel((VideoLibrary.getInstance().getVideoLibrary().get(i)));
        }

    }



    @FXML
    void onAddMediaPlayQueue(ActionEvent event) {


    }

    @FXML
    void onDeleteVideo(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();
        setContentTilePane();


        SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> setDimTilePane());
        VideoLibrary.getInstance().changeHappenedProperty().addListener(observable -> setContentTilePane());
        // TODO: 03/06/2022
    }
    
    public void startToolTip(){
        // TODO: 07/06/2022  
    }


    private void setContentTilePane(){

        tilePane.getChildren().clear();
        int size= VideoLibrary.getInstance().getVideoLibrary().size();
        for (int i= size-1; i>=0; --i){
            RecentMedia recentMedia = new RecentMedia(VideoLibrary.getInstance().getVideoLibrary().get(i));
            tilePane.getChildren().add(recentMedia);
        }


        if(VideoLibrary.getInstance().isChangeHappened())
            VideoLibrary.getInstance().setChangeHappened(false);

    }

    private  void setDimTilePane(){
        double tilePaneSize = (SceneHandler.getInstance().getStage().getWidth())-310;
        int numberOfColumns = ((int)tilePaneSize)/230;
        tilePane.setPrefColumns(numberOfColumns);

    }
}
