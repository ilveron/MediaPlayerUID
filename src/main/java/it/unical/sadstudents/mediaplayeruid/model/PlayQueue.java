package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;

import java.io.File;
import java.util.ArrayList;

public class PlayQueue {

    private static PlayQueue instance = null;

    private ObservableList<MyMedia> queue;
    private SimpleIntegerProperty currentMedia = new SimpleIntegerProperty(0) ;
    private SimpleBooleanProperty isAvideo = new SimpleBooleanProperty(false);

    public boolean isIsAvideo() {
        return isAvideo.get();
    }

    public SimpleBooleanProperty isAvideoProperty() {
        return isAvideo;
    }

    public void setIsAvideo(boolean isAvideo) {
        this.isAvideo.set(isAvideo);
    }

    public int getCurrentMedia() {
        return currentMedia.get();
    }

    public SimpleIntegerProperty currentMediaProperty() {
        return currentMedia;
    }


    //TODO: Rivedere se è possibile non chiamare da qui changeMedia()
    public void setCurrentMedia(int newCurrentMedia) {
        if(newCurrentMedia < 0 || newCurrentMedia == currentMedia.get()){
            startMedia();
            //changeMedia();
            return;
        }
        else if(newCurrentMedia >= getQueue().size()){
            newCurrentMedia = 0;
        }

        currentMedia.set(newCurrentMedia);
    }

    public ObservableList<MyMedia> getQueue(){
        return queue;
    }

    private PlayQueue (){
        queue = FXCollections.observableArrayList();
        currentMediaProperty().addListener(observable -> startMedia()/*changeMedia()*/ );//vericare come usare più funzioni
    }

    public static PlayQueue getInstance(){
        if (instance==null)
            instance = new PlayQueue();
        return instance;
    }

    public void generateNewQueue(File file){
        queue.clear();
        MyMedia media = new MyMedia(file);
        queue.add(media);
        currentMedia.set(0);
        startMedia();
        //Player.getInstance().createMedia(0);
        /*if(!queue.get(currentMedia.get()).getPath().toLowerCase().endsWith(".mp4"))
            isAvideo.set(true);*/

    }

    public void generateNewQueueFromList(ArrayList<File> files){
        queue.clear();
        currentMedia.set(0);
        for(File f: files){
            // TODO: 03/06/2022 GESTIONE ECCEZIONE MEDIA UNSUPPORTED
            MyMedia media = new MyMedia(f);
            queue.add(media);
            if(!Player.getInstance().getIsRunning())
                //Player.getInstance().createMedia(currentMedia.get());
                startMedia();
        }
        /*if(!queue.get(currentMedia.get()).getPath().toLowerCase().endsWith(".mp4"))
            isAvideo.set(true);*/
    }

    public void addFileToQueue(File file){
        MyMedia myMedia = new MyMedia(file);
        queue.add(myMedia);
        if(!Player.getInstance().isMediaLoaded())
            //Player.getInstance().createMedia(currentMedia.get());
            startMedia();

    }

    public void addFolderToQueue(ArrayList<File> files){
        for(File file: files){
            MyMedia myMedia = new MyMedia(file);
            queue.add(myMedia);
            if(!Player.getInstance().isMediaLoaded())
                //Player.getInstance().createMedia(currentMedia.get());
                startMedia();
        }
    }


    public void startMedia(){
        if (queue.get(currentMedia.get()).getPath().toLowerCase().endsWith(".mp4")){
            isAvideo.set(true);
        }
        else isAvideo.set(false);
        Player.getInstance().pauseMedia();
        Player.getInstance().createMedia(currentMedia.get());

    }

    /* Sostituita da startMedia
    public void changeMedia(){
        Player.getInstance().pauseMedia();
        Player.getInstance().createMedia(currentMedia.get());
    }*/

    public void changeMediaWithButton(Integer direction){
        setCurrentMedia(getCurrentMedia()+direction);
    }

    //TODO: NON CANCELLARE!!!
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
