package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class ContextMenuHandler extends ContextMenu {
    private String source;
    private String playlistName;
    private int row;
    public ContextMenuHandler(MyMedia myMedia,String playlistName,String  source,int row) {
        super ();
        this.source = source;
        this.playlistName = playlistName;
        this.row = row;
        MenuItem menuItem = new MenuItem();
        MenuItem menuItem1 = new MenuItem();
        MenuItem menuItem2 = new MenuItem();
        MenuItem menuItem3 = new MenuItem();

        if(source!="playlist"){
            menuItem.setText("Play");
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(source != "playqueue")
                        PlayQueue.getInstance().generateNewQueue(myMedia);
                    else{
                        PlayQueue.getInstance().setCurrentMedia(row);


                    }
                }
            });

            menuItem1.setText("Delete");
            menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                        if (source == "home"  && SceneHandler.getInstance().showConfirmationAlert("Do you want to remove from Recent Media?")) {
                            Home.getInstance().removeItem(myMedia);

                        } else if(source == "video" && SceneHandler.getInstance().showConfirmationAlert("Do you really want to delete this Video?")){
                            VideoLibrary.getInstance().removeSafe(myMedia);
                            SceneHandler.getInstance().setUpdateViewRequired(true);

                        }
                        else if(source == "musicLibrary" && SceneHandler.getInstance().showConfirmationAlert("Do you really want to delete this Song?") ){
                            MusicLibrary.getInstance().deleteStandard(myMedia);
                            SceneHandler.getInstance().setUpdateViewRequired(true);

                        }
                        else if(source == "playqueue" && SceneHandler.getInstance().showConfirmationAlert("Do you really want to remove from queue?")){
                            PlayQueue.getInstance().removeMedia(row);
                        }
                }
            });

            menuItem2.setText("Add To Queue");
            menuItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);
                }
            });

            menuItem3.setText("Add To Playlist");
            menuItem3.setDisable(true);
        }
        else if(source == "playlist"){

            if(myMedia==null){
                menuItem.setText("Play All");
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int index = PlaylistCollection.getInstance().getPlaylistWidthName(playlistName);
                        if(!PlaylistCollection.getInstance().getPlayListsCollections().get(index).isPlaying()){
                            PlaylistCollection.getInstance().getPlayListsCollections().get(index).setPlaying(true);
                            PlaylistCollection.getInstance().getPlayListsCollections().get(index).playAll(false);
                        }
                    }
                });
            }
            else{
                menuItem.setText("Play from here");
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int index = PlaylistCollection.getInstance().getPlaylistWidthName(playlistName);
                        PlaylistCollection.getInstance().getPlayListsCollections().get(index).addToPlayQueue(myMedia);
                    }
                });
            }

            if(myMedia==null){
                menuItem3.setText("Delete "+playlistName);
                menuItem3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(SceneHandler.getInstance().showConfirmationAlert("Delete the playlist '"+playlistName+"' ?")) {
                            int index= PlaylistCollection.getInstance().getPlaylistWidthName(playlistName);
                            PlaylistCollection.getInstance().deletePlaylist(index);
                        }
                    }
                });
            }
            else{
                menuItem3.setText("Clear "+playlistName);
                menuItem3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int index = PlaylistCollection.getInstance().getPlaylistWidthName(playlistName);
                        PlaylistCollection.getInstance().getPlayListsCollections().get(index).clearMyList();
                    }
                });
            }

            if(myMedia!=null){
                menuItem1.setText("Add Media to queue");
                menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);
                    }
                });
            }
            else{
                menuItem1.setText("Adds all to queue");
                menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int index = PlaylistCollection.getInstance().getPlaylistWidthName(playlistName);
                        PlaylistCollection.getInstance().getPlayListsCollections().get(index).playAll(true);
                    }
                });
            }
            menuItem2.setText("Add/remove");
            menuItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    SubStageHandler.getInstance().init("addMediaToPlaylist-view.fxml",700,761,"Edit Playlist",true, playlistName);

                }
            });
        }

        this.getItems().add(menuItem);
        this.getItems().add(menuItem1);
        if(source!="playqueue")
            this.getItems().add(menuItem2);
        this.getItems().add(menuItem3);

        if(source!="playlist"){
            this.getItems().add(new SeparatorMenuItem());

            for(int i = 0; i< PlaylistCollection.getInstance().getPlayListsCollections().size(); i++){
                MenuItem temp = new MenuItem(PlaylistCollection.getInstance().getPlayListsCollections().get(i).getName());
                int finalI = i;
                temp.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        PlaylistCollection.getInstance().getPlayListsCollections().get(finalI).addMedia(myMedia);

                    }
                });
                this.getItems().add(temp);
            }
        }

    }
}
