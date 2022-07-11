package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.ContextMenuHandler;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class MyMediaSingleBoxController  {
    @FXML
    private Label labelDuration;
    @FXML
    private StackPane stackPaneRecent;

    @FXML
    private AnchorPane actionAnchorPane;

    @FXML
    private Label artistLabel;
    @FXML
    private MediaView   mediaViewBis;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;
    @FXML
    private AnchorPane mainRoot;
    @FXML
    private Pane onActionPane;

    @FXML
    private Button btnDelete, btnPlay;

    @FXML
    void onDeleteAction(ActionEvent event) {
        deleteMedia();
    }

    @FXML
    void onPlayAction(ActionEvent event) {
        playMedia();
    }


    private ContextMenu contextMenu;
    private MyMedia myMedia;
    private String source;

    public void init(MyMedia myMedia, String source){
        this.source=source;
        this.myMedia=myMedia;
        artistLabel.setText(myMedia.getArtist());

        artistLabel.setTooltip(new Tooltip(myMedia.getArtist()));

        nameLabel.setText(myMedia.getTitle());

        nameLabel.setTooltip(new Tooltip(myMedia.getTitle()));

        imageView.setImage(new Image("file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png"));
        labelDuration.setText(myMedia.getLength());
        btnPlay.setTooltip(new Tooltip("Play this media (a new queue will be created)"));
        if(source=="home"){
            btnDelete.setTooltip(new Tooltip("Delete media from recent media"));
        }
        else{
            btnDelete.setTooltip(new Tooltip("Delete media from library"));
        }
        SceneHandler.getInstance().scaleTransition(btnDelete);
        SceneHandler.getInstance().scaleTransition(btnPlay);
    }

    public void setImage(Image image){
        imageView.setImage(image);
    }

    public Image getImage(){
        return imageView.getImage();
    }

    public MediaView getMediaViewBis(){
        return mediaViewBis;
    }

    public void onMouseOver(){
        if(!actionAnchorPane.isVisible()){
            actionAnchorPane.setVisible(true);
            imageView.setEffect(new GaussianBlur(20));
        }
        else{
            actionAnchorPane.setVisible(false);
            imageView.setEffect(null);
        }

    }

    public void playMedia(){
        PlayQueue.getInstance().generateNewQueue(myMedia);
    }

    public void deleteMedia(){
            if (source == "home" && SceneHandler.getInstance().showConfirmationAlert("Do you want to remove from Recent Media?")) {
                Home.getInstance().removeItem(myMedia);
            } else if (source!="home" && SceneHandler.getInstance().showConfirmationAlert("Do you really want to delete this Video?")){
                VideoLibrary.getInstance().removeSafe(myMedia);
            }
    }

    public void contextMenuHandle(Node node,double x, double y) {
        if(contextMenu!=null && contextMenu.isShowing())
            contextMenu.hide();

        contextMenu = new ContextMenuHandler(myMedia,"",source,0);
        contextMenu.show(node,x,y);


    }
}
