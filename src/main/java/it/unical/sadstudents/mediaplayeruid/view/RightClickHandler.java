package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.Settings;
import it.unical.sadstudents.mediaplayeruid.controller.RightClickController;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;

public class RightClickHandler extends Pane {
    private MyMedia myMedia;
    private Scene sceneRightClick;
    private Stage stageRightClick;
    private String source;
    private MouseEvent mouseEvent;



    //SINGLETON
    private static RightClickHandler instance = null;
    private RightClickHandler(){


    }
    public static RightClickHandler getInstance(){
        if (instance==null)
            instance = new RightClickHandler();
        return instance;
    }
    //END SINGLETON

    //GETTERS AND SETTERS



    public MyMedia getMyMedia() { return myMedia; }

    public void setMyMedia(MyMedia myMedia) { this.myMedia = myMedia; }

    public Scene getSceneRightClick() { return sceneRightClick; }

    public void setSceneRightClick(Scene sceneRightClick) { this.sceneRightClick = sceneRightClick; }

    public Stage getStageRightClick() {
        return stageRightClick;
    }

    public void setStageRightClick(Stage stageRightClick) {
        this.stageRightClick = stageRightClick;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    //END GETTERS AND SETTERS


    //START MAIN STAGE AND SCENE
    public void init(String source, MyMedia myMedia, Double x, Double y,MouseEvent mouseEvent) throws Exception {
        if (stageRightClick !=null)
            stageRightClick.close();

        this.source = source;
        this.myMedia = myMedia;
        this.mouseEvent = mouseEvent;

        stageRightClick = new Stage();
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("rightClick-view.fxml"));

        sceneRightClick = new Scene(loader.load());


        stageRightClick.setX(x);
        stageRightClick.setY(y);
       /* if (source=="Home"){
            stageRightClick.setMaxHeight(237);
        }*/
        stageRightClick.initStyle(StageStyle.UNDECORATED);
        stageRightClick.setScene(sceneRightClick);
        stageRightClick.show();
        Scene mainScene = SceneHandler.getInstance().getScene();
        mainScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEventBis) {
                if(mouseEventBis.getScreenX() !=mouseEvent.getScreenX()  && mouseEventBis.getScreenY() != mouseEvent.getScreenY()){
                    if(mouseEventBis.getClickCount()==1 ){
                        RightClickHandler.getInstance().getStageRightClick().close();

                    }
                }
          }
        });







        /*stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
                System.exit(0);
            }
        });*/

    }



}
