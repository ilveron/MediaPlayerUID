package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.RecentMedia;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
        for(int i=0;i<VideoLibrary.getInstance().getVideoLibrary().size();i++){
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
        if(VideoLibrary.getInstance().getVideoLibrary().size()>0)
            activeButton();


        SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> setDimTilePane());
        VideoLibrary.getInstance().changeHappenedProperty().addListener(observable -> setContentTilePane());
        // TODO: 03/06/2022
    }
    
    public void startToolTip(){
        // TODO: 07/06/2022  
    }


    public void setContentTilePane(){

        tilePane.getChildren().clear();
        int size= VideoLibrary.getInstance().getVideoLibrary().size();
        for (int i= size-1; i>=0; --i){
            RecentMedia recentMedia = new RecentMedia(VideoLibrary.getInstance().getVideoLibrary().get(i),"video");
            tilePane.getChildren().add(recentMedia);
            recentMedia.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    recentMedia.mouseOverAction();

                } else {
                    recentMedia.mouseOverAction();
                }
            });
        }


        if(VideoLibrary.getInstance().isChangeHappened())
            VideoLibrary.getInstance().setChangeHappened(false);

    }

    public  void setDimTilePane(){
        double tilePaneSize = (SceneHandler.getInstance().getStage().getWidth())-310;
        int numberOfColumns = ((int)tilePaneSize)/230;
        tilePane.setPrefColumns(numberOfColumns);

    }

    public void activeButton(){
        btnAddLibraryToQueue.setDisable(false);
        btnDelete.setDisable(false);
        btnAddVideoToQueue.setDisable(false);
    }
}
