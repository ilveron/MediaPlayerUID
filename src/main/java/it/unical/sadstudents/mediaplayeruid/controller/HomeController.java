package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.RecentMedia;
import it.unical.sadstudents.mediaplayeruid.view.RightClickHandler;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private TilePane tilePane;
    @FXML
    private ImageView imageTest;
    @FXML
    private ScrollPane scrollPane;


    // TODO: 06/06/2022 DECIDERE SE SWITCHARE IN PLAYQUEUE xd !!!
    @FXML
    void addFiles(ActionEvent event) {
        //invoca la funzione di PlayQueue per creare una coda da file
        Home.getInstance().addMediaToPlayAndLibrary(RetrievingEngine.getInstance().retrieveFile(0));

    }

    @FXML
    void addFolder(ActionEvent event) {
        //invoca la funzione di PlayQueue per creare una coda da folder
        Home.getInstance().addMediaToPlayAndLibrary(RetrievingEngine.getInstance().retrieveFolder(0));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();

        setContentTilePane();
        Home.getInstance().changeHappenedProperty().addListener(observable -> setContentTilePane());
        SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> setDimTilePane());


    }


    private void startToolTip() {
        // TODO: 07/06/2022
    }

    private void setContentTilePane(){
        tilePane.getChildren().clear();
        int size= Home.getInstance().getRecentMedia().size();
        for (int i= size-1; i>=0; --i){
            RecentMedia recentMedia = new RecentMedia(Home.getInstance().getRecentMedia().get(i));



            //tasto destro e sinistro sul recentMedia
            recentMedia.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                        if(mouseEvent.getClickCount() == 1){
                            Double x = mouseEvent.getScreenX();
                            Double y = mouseEvent.getScreenY();

                            try {
                                RightClickHandler.getInstance().init("Home",recentMedia.getMyMedia(),x,y,mouseEvent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }
                    else if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 2){
                            PlayQueue.getInstance().generateNewQueue(recentMedia.getMyMedia());
                        }
                        else if (mouseEvent.getClickCount()==1){
                            Platform.runLater(()->{
                                recentMedia.requestFocus();
                            });


                        }
                    }
                }
            });





            recentMedia.setFocusTraversable(true);
            recentMedia.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyCode key = keyEvent.getCode();
                    if (key == KeyCode.ENTER && recentMedia.isFocused()) {
                        PlayQueue.getInstance().generateNewQueue(recentMedia.getMyMedia());
                    }
                }
            });
            tilePane.getChildren().add(recentMedia);
        }



        if(Home.getInstance().isChangeHappened())
            Home.getInstance().setChangeHappened(false);



    }

    private  void setDimTilePane(){
        double tilePaneSize = (SceneHandler.getInstance().getStage().getWidth())-310;
        int numberOfColumns = ((int)tilePaneSize)/230;
        tilePane.setPrefColumns(numberOfColumns);

    }


}