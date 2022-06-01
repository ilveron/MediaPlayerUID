package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.view.MiddlePaneHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;

import java.io.File;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

public class PlayQueue {

    private static PlayQueue instance = null;

    private PlayQueue (){

        queue = FXCollections.observableArrayList();
        Player.getInstance().currentProperty().addListener(observable ->cambiacanzone() );//vericare come usare più funzioni
    }

    public static PlayQueue getInstance(){
        if (instance==null)
            instance = new PlayQueue();
        return instance;
    }

    private ObservableList<MyMedia> queue;
    private SimpleIntegerProperty currentMedia = new SimpleIntegerProperty(0) ;

    public int getCurrentMedia() {
        return currentMedia.get();
    }

    public SimpleIntegerProperty currentMediaProperty() {
        return currentMedia;
    }

    public void setCurrentMedia(int currentMedia) {
        this.currentMedia.set(currentMedia);
    }

    public ObservableList<MyMedia> getQueue(){
        return queue;
    }

    public void generateNewQueue(File file){
        queue.clear();
        MyMedia media = new MyMedia(file);
        queue.add(media);
        currentMedia.set(0);
        Player.getInstance().createMedia(queue.get(currentMedia.get()).getPath());
        Player.getInstance().setNameMedia(queue.get(currentMedia.get()).getTitle());
        MiddlePaneHandler.getInstance().setCurrentMidPane("play-queue-view.fxml");

    }

    public void generateNewQueueFromList(File[] files){
        queue.clear();
        currentMedia.set(0);
        for(File f: files){
            MyMedia media = new MyMedia(f);
            queue.add(media);
            if(!Player.getInstance().getIsRunning())
                Player.getInstance().createMedia(queue.get(currentMedia.get()).getPath());
                Player.getInstance().setNameMedia(queue.get(currentMedia.get()).getTitle());
        }
        MiddlePaneHandler.getInstance().setCurrentMidPane("play-queue-view.fxml");
    }

    public void addToQueue(MyMedia myMedia){
        queue.add(myMedia);
    }


    public void cambiacanzone(){
        Integer current = currentMedia.get();
        if (Player.getInstance().getCurrent()==Player.getInstance().getEnd()){
            if (current ==queue.size()-1)currentMedia.set(0);
            else{
                currentMedia.set(++current);

            }
            Player.getInstance().pauseMedia();
            Player.getInstance().createMedia(queue.get(currentMedia.get()).getPath());
        }
    }

    public void cambiacanzonefrombutton(Integer direction){
            currentMedia.set(currentMedia.get()+direction);

            if (currentMedia.get()>queue.size()-1)currentMedia.set(0);
            else if(currentMedia.get()<0){
                currentMedia.set(queue.size()-1);
            }
            Player.getInstance().pauseMedia();
            Player.getInstance().createMedia(queue.get(currentMedia.get()).getPath());

    }

    /*public void compilaMetadati(){
        for(MyMedia myMedia: queue){
            Media media = new Media(myMedia.getPath());
            media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
                System.out.println("ciao");
                if(change.wasAdded()){
                    if("title".equals(change.getKey())) {
                        myMedia.setTitle(media.getMetadata().get("title").toString());
                    }
                    else if ("artist".equals(change.getKey())){
                        myMedia.setArtist(media.getMetadata().get("artist").toString());
                    }
                    else if ("album".equals(change.getKey())){
                        myMedia.setAlbum(media.getMetadata().get("album").toString());
                    }
                    else if ("genre".equals(change.getKey())){
                        myMedia.setGenre(media.getMetadata().get("genre").toString());
                    }
                    else if (media.getDuration()!= null){
                        myMedia.setDuration(media.getDuration().toSeconds());
                        if ( media.getMetadata().get("length")!= null)
                            myMedia.setDuration((Double) media.getMetadata().get("length"));
                    }
                }
            });
        }
    }*/
}
