package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.ContextMenuHandler;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;

public class MyMediaSingleBoxController {
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
        nameLabel.setText(myMedia.getTitle());
        imageView.setImage(new Image("file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png"));
        labelDuration.setText(myMedia.getLength());

        //createContextMenu();
        //contextMenu = new ContextMenuHandler(myMedia,"",source);








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

   /* public void createContextMenu(){
        contextMenu = new ContextMenu();
        MenuItem menuItem = new MenuItem("Play");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                playMedia();
            }
        });

        MenuItem menuItem1 = new MenuItem("Delete");
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteMedia();
            }
        });

        MenuItem menuItem2 = new MenuItem("Add To Queue");
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);
            }
        });

        MenuItem menuItem3 = new MenuItem("Add To Playlist");
        menuItem3.setDisable(true);


        contextMenu.getItems().add(menuItem);
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(new SeparatorMenuItem());

        for(int i= 0; i< Playlists.getInstance().getPlayListsCollections().size();i++){
            MenuItem temp = new MenuItem(Playlists.getInstance().getPlayListsCollections().get(i).getName());
            int finalI = i;
            temp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Playlists.getInstance().getPlayListsCollections().get(finalI).addMedia(myMedia);

                }
            });
            contextMenu.getItems().add(temp);
        }

    }*/

    public void contextMenuHandle(Node node,double x, double y) {
        if(contextMenu!=null && contextMenu.isShowing())
            contextMenu.hide();

        contextMenu = new ContextMenuHandler(myMedia,"",source,0);
        contextMenu.show(node,x,y);


    }
}
