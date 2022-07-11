package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.controller.MyMediaSingleBoxController;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;

public class MyMediaSingleBox extends Pane {

    private MyMedia myMedia;
    private MyMediaSingleBoxController controller;

    public MyMediaSingleBox(MyMedia myMedia, String source){
        this.myMedia = myMedia;
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("myMediaSingleBox-view.fxml"));
        try{
            AnchorPane root = loader.load();
            controller = loader.getController();
            controller.init(myMedia,source);
            this.getChildren().add(root);
            addKeyMouseEvent();
        }catch(Exception ignoredException){}

    }

    public MyMedia getMyMedia(){return myMedia;}


    public void setImage(Image image){
        controller.setImage(image);
    }

    public Image getImage(){return controller.getImage();}


    public MediaView getMediaView(){
        return controller.getMediaViewBis();
    }

    public void addKeyMouseEvent(){
       this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        PlayQueue.getInstance().generateNewQueue(myMedia);
                    }
                }
            }
       });

       this.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
           if (newValue) {
               SceneHandler.getInstance().scaleTransition(this);
               controller.onMouseOver();
           } else {
               SceneHandler.getInstance().scaleTransition(this);
               controller.onMouseOver();
           }
       });

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode key = keyEvent.getCode();
                if (key == KeyCode.ENTER ) {
                    PlayQueue.getInstance().generateNewQueue(myMedia);
                }
                else if(key == KeyCode.DELETE){
                    controller.deleteMedia();
                }
            }
        });





    }

    public void contextMenu(Node node, double x, double y) {
        controller.contextMenuHandle(node,x,y);
    }
}
