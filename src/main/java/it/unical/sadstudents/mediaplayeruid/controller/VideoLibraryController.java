package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import it.unical.sadstudents.mediaplayeruid.view.VideoGalleryTilePaneHandler;
import javafx.application.Platform;
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
        if(VideoLibrary.getInstance().getVideoLibrary().size()>0)
            activeButton();
        setContentTilePane();

        VideoGalleryTilePaneHandler.getInstance().readyIntegerProperty().addListener(observable -> {
            if (VideoGalleryTilePaneHandler.getInstance().getMyMediaSingleBoxes().size()==VideoLibrary.getInstance().getVideoLibrary().size())
                activeButton();
                setContentTilePane();
        });


        //VideoLibrary.getInstance().changeHappenedProperty().addListener(observable -> setContentTilePane());

        SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> setDimTilePane());
    }
    
    public void startToolTip(){
        // TODO: 07/06/2022  
    }


    public void setContentTilePane(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tilePane.getChildren().clear();
                int size = VideoGalleryTilePaneHandler.getInstance().getMyMediaSingleBoxes().size();
                for (int i =0; i<size;  i++) {
                    tilePane.getChildren().add(VideoGalleryTilePaneHandler.getInstance().getMyMediaSingleBoxes().get(i));
                }
                //loadingIndicator.setVisible(false);
                //loadingLabel.setVisible(false);

            }
        });

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
