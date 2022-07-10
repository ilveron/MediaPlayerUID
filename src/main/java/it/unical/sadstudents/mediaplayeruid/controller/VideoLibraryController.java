package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.utils.MyNotification;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import it.unical.sadstudents.mediaplayeruid.view.VideoGalleryTilePaneHandler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class VideoLibraryController implements Initializable {
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
    private MenuButton mbtAdd;
    @FXML
    private TextField textFieldSearch;


    @FXML
    void onAddFolder(ActionEvent event) {
        VideoLibrary.getInstance().addFilesToList(RetrievingEngine.getInstance().retrieveFolder(2));
        if(VideoLibrary.getInstance().getVideoLibrary().size()>0) activeButton(true);
        else activeButton(false);
    }

    @FXML
    void onAddMedia(ActionEvent event) {
        VideoLibrary.getInstance().addFilesToList(RetrievingEngine.getInstance().retrieveFile(2));
        if(VideoLibrary.getInstance().getVideoLibrary().size()>0) activeButton(true);
        else activeButton(false);
    }


    @FXML
    void onAddLibraryToQueue(ActionEvent event) {
        for(int i=0;i<VideoLibrary.getInstance().getVideoLibrary().size();i++){
            PlayQueue.getInstance().addFileToListFromOtherModel((VideoLibrary.getInstance().getVideoLibrary().get(i)));
        }
        MyNotification.notifyInfo("","Library added to queue",3);
    }


    @FXML
    void onAddMediaPlayQueue(ActionEvent event) {
        if(mouseEvent1!=null && index!=-1){
            PlayQueue.getInstance().addFileToListFromOtherModel(VideoGalleryTilePaneHandler.getInstance().getMyMediaSingleBoxes().get(index).getMyMedia());
            index=-1;
            mouseEvent1=null;
            removeStyle();
        }
            

        MyNotification.notifyInfo("","Video added to queue",3);
    }

    @FXML
    void onDeleteVideo(ActionEvent event) {
        VideoLibrary.getInstance().clearAll();
        activeButton(false);
    }

    private int index=-1;
    private MouseEvent mouseEvent1 = null;
    private MouseEvent mouseEvent2 = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();
        setContentTilePane();


        VideoGalleryTilePaneHandler.getInstance().readyIntegerProperty().addListener(observable -> {
            if (VideoGalleryTilePaneHandler.getInstance().getMyMediaSingleBoxes().size()>0)
                setContentTilePane();
        });



        textFieldSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tilePane.getChildren().clear();
                tilePane.getChildren().addAll(SearchForFile.getInstance().getSearchVideo(newValue));
            }
        });

        SceneHandler.getInstance().getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent1!=null && !mouseEvent.getTarget().equals(mouseEvent1.getTarget())){
                    if (mouseEvent.getTarget()!=btnAddVideoToQueue){
                        removeStyle();
                        mouseEvent1=null;
                        index=-1;
                    }
                }
            }
        });


        //VideoLibrary.getInstance().changeHappenedProperty().addListener(observable -> setContentTilePane());

        SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> setDimTilePane());

        SceneHandler.getInstance().scaleTransition(btnAddLibraryToQueue);
        SceneHandler.getInstance().scaleTransition(btnAddVideoToQueue);
        SceneHandler.getInstance().scaleTransition(btnDelete);
        SceneHandler.getInstance().scaleTransition(mbtAdd);
    }
    
    public void startToolTip(){
        textFieldSearch.setTooltip(new Tooltip("Search for a video"));
        mbtAdd.setTooltip(new Tooltip("Add video(s) to the library"));
        btnDelete.setTooltip(new Tooltip("Clear all videos"));
        btnAddVideoToQueue.setTooltip(new Tooltip("Add video to queue"));
        btnAddLibraryToQueue.setTooltip(new Tooltip("Add video library to queue"));
    }


    public void setContentTilePane(){
        //VideoGalleryTilePaneHandler.getInstance().getMyMediaSingleBoxes().get(index).getMyMedia().getPath();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tilePane.getChildren().clear();
                int size = VideoGalleryTilePaneHandler.getInstance().getMyMediaSingleBoxes().size();
                if(size>0)
                    activeButton(true);
                else
                    activeButton(false);

                for (int i =0; i<size;  i++) {
                    int finalI = i;
                    VideoGalleryTilePaneHandler.getInstance().getMyMediaSingleBoxes().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                                if(mouseEvent.getClickCount() == 1 ){
                                    removeStyle();
                                    tilePane.getChildren().get(finalI).getStyleClass().add("selectedRecentMedia");
                                    index=finalI;
                                    tilePane.getChildren().get(finalI).requestFocus();

                                }
                            }
                            mouseEvent1=mouseEvent;
                        }
                    });
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

    public void activeButton(Boolean t){
        textFieldSearch.setDisable(!t);
        btnAddLibraryToQueue.setDisable(!t);
        btnDelete.setDisable(!t);
        btnAddVideoToQueue.setDisable(!t);
    }
    
    public void removeStyle(){
        tilePane.getChildren().forEach(myMediaSingleBox1 -> myMediaSingleBox1.getStyleClass().remove("selectedRecentMedia"));

    }

}
