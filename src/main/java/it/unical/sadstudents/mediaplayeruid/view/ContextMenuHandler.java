package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class ContextMenuHandler extends ContextMenu {
    private String source;
    private String playlistName;
    public ContextMenuHandler(MyMedia myMedia,String playlistName,String  source) {
        super ();
        this.source = source;
        this.playlistName = playlistName;
        MenuItem menuItem = new MenuItem();
        MenuItem menuItem1 = new MenuItem();
        MenuItem menuItem2 = new MenuItem();
        MenuItem menuItem3 = new MenuItem();

        if(source!="playlist"){
            menuItem.setText("Play");
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    PlayQueue.getInstance().generateNewQueue(myMedia);
                }
            });

            menuItem1.setText("Delete");
            menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                        if (source == "home"  && SceneHandler.getInstance().showConfirmationAlert("Do you want to remove from Recent Media?")) {
                            for (int i = 0; i < Home.getInstance().getRecentMedia().size(); i++) {
                                if (myMedia.equals(Home.getInstance().getRecentMedia().get(i))) {
                                    Home.getInstance().removeItem(i);
                                    break;
                                }
                            }
                        } else if(source == "video" && SceneHandler.getInstance().showConfirmationAlert("Do you really want to delete this Video?")){
                            for (int i = 0; i < VideoLibrary.getInstance().getVideoLibrary().size(); i++) {
                                if (myMedia.equals(VideoLibrary.getInstance().getVideoLibrary().get(i))) {

                                    for (int j = 0; j < Home.getInstance().getRecentMedia().size(); j++) {
                                        if (myMedia.equals(Home.getInstance().getRecentMedia().get(j))) {
                                            Home.getInstance().removeItem(j);
                                        }
                                    }
                                    if (Player.getInstance().isMediaLoaded() && Player.getInstance().getMediaPlayer().getMedia().getSource() == myMedia.getPath())
                                        Player.getInstance().stop();
                                    PlayQueue.getInstance().deleteFromOtherController(myMedia);
                                    VideoLibrary.getInstance().removeWithIndex(i);
                                    break;
                                }
                            }
                        }
                        else if(source == "musicLibrary" && SceneHandler.getInstance().showConfirmationAlert("Do you really want to delete this Song?") ){
                            for (int i=0; i<MusicLibrary.getInstance().getMusicLibrary().size(); i++){
                                if(myMedia.equals(MusicLibrary.getInstance().getMusicLibrary().get(i))){
                                    MusicLibrary.getInstance().deleteWithIndex(i);
                                    break;
                                }
                            }

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
                        int index = Playlists.getInstance().getPlaylistWidthName(playlistName);
                        for (int i = 0; i<Playlists.getInstance().getPlayListsCollections().get(index).getMyList().size(); i++){
                            MyMedia temp = Playlists.getInstance().getPlayListsCollections().get(index).getMyList().get(i);
                            if(i==0)
                                PlayQueue.getInstance().generateNewQueue(temp);
                            else
                                PlayQueue.getInstance().addFileToListFromOtherModel(temp);
                        }
                    }
                });
            }
            else{
                menuItem.setText("Play from here");
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int index = Playlists.getInstance().getPlaylistWidthName(playlistName);
                        for (int i = 0; i<Playlists.getInstance().getPlayListsCollections().get(index).getMyList().size(); i++){
                            MyMedia temp = Playlists.getInstance().getPlayListsCollections().get(index).getMyList().get(i);
                            if(i==0)
                                PlayQueue.getInstance().generateNewQueue(temp);
                            else
                                PlayQueue.getInstance().addFileToListFromOtherModel(temp);
                        }
                    }
                });
            }

            if(myMedia==null){
                menuItem3.setText("Delete "+playlistName);
                menuItem3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int index = Playlists.getInstance().getPlaylistWidthName(playlistName);

                        Playlists.getInstance().deletePlaylist(index);
                    }
                });
            }
            else{
                menuItem3.setText("Clear "+playlistName);
                menuItem3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int index = Playlists.getInstance().getPlaylistWidthName(playlistName);

                        Playlists.getInstance().getPlayListsCollections().get(index).getMyList().clear();
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
                        int index = Playlists.getInstance().getPlaylistWidthName(playlistName);
                        for (int i = 0; i<Playlists.getInstance().getPlayListsCollections().get(index).getMyList().size(); i++){
                            MyMedia temp = Playlists.getInstance().getPlayListsCollections().get(index).getMyList().get(i);
                            PlayQueue.getInstance().addFileToListFromOtherModel(temp);
                        }
                    }
                });
            }
            menuItem2.setText("Add/remove");
            menuItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int index = Playlists.getInstance().getPlaylistWidthName(playlistName);
                    PlaylistMedia.getInstance().changePlaylistMedia(Playlists.getInstance().getPlayListsCollections().get(index));
                }
            });


        }



        this.getItems().add(menuItem);
        this.getItems().add(menuItem1);
        this.getItems().add(menuItem2);
        this.getItems().add(menuItem3);

        if(source!="playlist"){
            this.getItems().add(new SeparatorMenuItem());

            for(int i = 0; i< Playlists.getInstance().getPlayListsCollections().size(); i++){
                MenuItem temp = new MenuItem(Playlists.getInstance().getPlayListsCollections().get(i).getName());
                int finalI = i;
                temp.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Playlists.getInstance().getPlayListsCollections().get(finalI).addMedia(myMedia);

                    }
                });
                this.getItems().add(temp);
            }
        }

    }
}
