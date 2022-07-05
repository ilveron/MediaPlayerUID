package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.thread.ImageCreator;
import it.unical.sadstudents.mediaplayeruid.view.HomeTilePaneHandler;
import it.unical.sadstudents.mediaplayeruid.view.RecentMedia;
import it.unical.sadstudents.mediaplayeruid.view.RightClickHandler;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {



    @FXML
    private AnchorPane homeAnchorPane;
    @FXML
    private TilePane tilePane;
    @FXML
    private ImageView imageTest;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label loadingLabel;
    @FXML
    private ProgressIndicator loadingIndicator;


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
        System.out.println("inizializzazione");
        startToolTip();

        Home.getInstance().loadingProperty().addListener(observable -> {
            if(!Home.getInstance().isLoading()){
                loadingIndicator.setVisible(false);
                loadingLabel.setVisible(false);
            }
        });


        HomeTilePaneHandler.getInstance().readyIntegerProperty().addListener(observable -> {
            if(HomeTilePaneHandler.getInstance().getReadyInteger()==Home.getInstance().getRecentMedia().size()){
                setContentTilePane();
            }

        });


        setContentTilePane();



        SceneHandler.getInstance().getStage().widthProperty().addListener(observable -> setDimTilePane());



    }


    private void startToolTip() {
        // TODO: 07/06/2022
    }


    public void setContentTilePane(){
        System.out.println("funzione");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tilePane.getChildren().clear();

                int size = HomeTilePaneHandler.getInstance().getRecentMedias().size();
                for (int i = size-1; i>=0;  i--) {


                    tilePane.getChildren().add(HomeTilePaneHandler.getInstance().getRecentMedias().get(i));
                    /*tilePane.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                                    if(mouseEvent.getClickCount() == 2){
                                        PlayQueue.getInstance().generateNewQueue(tilePane.getChildren().get(i).getMyMedia());
                                    }
                                    else if (mouseEvent.getClickCount()==1){
                                            tilePane.getChildren().get(i).requestFocus();

                                    }
                                }
                            }
                        });


                    tilePane.getChildren().get(i).hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                            if (newValue) {
                                tilePane.getChildren().get(i).mouseOverAction();

                            } else {
                                tilePane.getChildren().get(i).mouseOverAction();
                            }
                        });





                    tilePane.getChildren().get(i).setFocusTraversable(true);

                    tilePane.getChildren().get(i).setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent keyEvent) {
                                KeyCode key = keyEvent.getCode();
                                if (key == KeyCode.ENTER && tilePane.getChildren().get(i).isFocused()) {
                                    PlayQueue.getInstance().generateNewQueue(tilePane.getChildren().get(i).getMyMedia());
                                    //RecentMedia tempRecentMedia = recentMedias.get(finalI);

                                }
                            }
                        });


                    if (i == 0) {
                        loadingIndicator.setVisible(false);
                        loadingLabel.setVisible(false);
                    }*/




            }
        }



        });

    }

    private  void setDimTilePane(){
        double tilePaneSize = (SceneHandler.getInstance().getStage().getWidth())-310;
        int numberOfColumns = ((int)tilePaneSize)/230;
        tilePane.setPrefColumns(numberOfColumns);

    }


}