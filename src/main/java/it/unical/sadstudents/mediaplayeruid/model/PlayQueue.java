package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;

public class PlayQueue {

    private static PlayQueue instance = null;

    private PlayQueue (){

        queue = FXCollections.observableArrayList();
        Player.getInstance().currentProperty().addListener(observable -> changeMedia() );//vericare come usare più funzioni
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
        Player.getInstance().createMedia(0);
        SceneHandler.getInstance().setCurrentMidPane("play-queue-view.fxml");

    }

    public void generateNewQueueFromList(ArrayList<File> files){
        queue.clear();
        currentMedia.set(0);
        for(File f: files){
            // TODO: 03/06/2022 GESTIONE ECCEZIONE MEDIA UNSUPPORTED 
            MyMedia media = new MyMedia(f);
            queue.add(media);
            if(!Player.getInstance().getIsRunning())
                Player.getInstance().createMedia(currentMedia.get());
        }
        SceneHandler.getInstance().setCurrentMidPane("play-queue-view.fxml");
    }

    public void addFileToQueue(File file){
        MyMedia myMedia = new MyMedia(file);
        queue.add(myMedia);
    }

    public void addFolderToQueue(ArrayList<File> files){
        for(File file: files){
            MyMedia myMedia = new MyMedia(file);
            queue.add(myMedia);
        }
    }


    public void changeMedia(){
        Integer current = currentMedia.get();
        if (Player.getInstance().getCurrent()==Player.getInstance().getEnd()){
            if (current < PlayQueue.getInstance().getQueue().size()-1) {
                currentMedia.set(++current);
                Player.getInstance().pauseMedia();
                Player.getInstance().createMedia(currentMedia.get());
            }
        }
    }

    public void changeMediaWithButton(Integer direction){
            currentMedia.set(currentMedia.get()+direction);

            if(currentMedia.get() >= 0 && currentMedia.get()<queue.size()){
                Player.getInstance().pauseMedia();
                Player.getInstance().createMedia(currentMedia.get());
            }
            else
                currentMedia.set(currentMedia.get()-direction);

            //VECCHIA VERSIONE:
            /*
            currentMedia.set(currentMedia.get()+direction);

            if (currentMedia.get()>queue.size()-1)
                currentMedia.set(0);
            else if(currentMedia.get()<0){
                currentMedia.set(queue.size()-1);
            }
            */

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
