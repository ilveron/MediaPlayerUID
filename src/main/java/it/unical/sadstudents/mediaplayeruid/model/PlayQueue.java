package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.controller.MainController;
import it.unical.sadstudents.mediaplayeruid.controller.PlayQueueController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class PlayQueue implements DataListedModel {
    //VARIABLES
    private ObservableList<MyMedia> queue;
    private SimpleIntegerProperty currentMedia = new SimpleIntegerProperty(0) ;
    private SimpleBooleanProperty isAvideo = new SimpleBooleanProperty(false);
    private boolean shuffleActive=false;
    //END VARIABLES

    //SINGLETON
    private static PlayQueue instance = null;
    private PlayQueue (){
        queue = FXCollections.observableArrayList();
        currentMediaProperty().addListener(observable -> {
            if(queue.size()>0)
                startMedia();
        } );//verificare come usare più funzioni
    }

    public static PlayQueue getInstance(){
        if (instance==null)
            instance = new PlayQueue();
        return instance;
    }
    //END SINGLETON CLASS

    //GETTERS AND SETTERS
    public boolean isIsAvideo() { return isAvideo.get(); }
    public SimpleBooleanProperty isAvideoProperty() { return isAvideo; }
    public void setIsAvideo(boolean isAvideo) { this.isAvideo.set(isAvideo); }

    public boolean isShuffleActive() { return shuffleActive; }
    public void setShuffleActive(boolean shuffleActive) {  this.shuffleActive = shuffleActive; }

    public int getCurrentMedia() {
        return currentMedia.get();
    }
    public SimpleIntegerProperty currentMediaProperty() {
        return currentMedia;
    }
    
    //TODO: Rivedere se è possibile non chiamare da qui changeMedia()
    public void setCurrentMedia(int newCurrentMedia) {
        if(queue.size()>0){
            if(newCurrentMedia < 0 || newCurrentMedia == currentMedia.get()){
                startMedia();
                return;
            }
            else if(newCurrentMedia >= getQueue().size()){
                newCurrentMedia = 0;
            }

            currentMedia.set(newCurrentMedia);

        }
        else{
            currentMedia.set(0);
        }

    }

    public ObservableList<MyMedia> getQueue(){
        return queue;
    }
    //END GETTERS AND SETTERS




    //FUNCTIONS LIST MANIPULATION
    @Override
    public void clearList() {
        queue.clear();
        currentMedia.set(0);
    }

    @Override
    public void addFileToList(File file) {
        MyMedia myMedia = new MyMedia(file);
        queue.add(myMedia);
        if(!Player.getInstance().isMediaLoaded())
            //Player.getInstance().createMedia(currentMedia.get());
            startMedia();

    }

    @Override
    public void addFolderToList(ArrayList<File> files) {
        for(File file: files){
            MyMedia myMedia = new MyMedia(file);
            queue.add(myMedia);
            if(!Player.getInstance().isMediaLoaded())
                //Player.getInstance().createMedia(currentMedia.get());
                startMedia();
        }

    }

    public void generateNewQueue(File file){
        clearList();
        addFileToList(file);

    }

    public void generateNewQueueFromList(ArrayList<File> files){
        clearList();
        addFolderToList(files);
    }
    //END FUNCTIONS LIST MANIPULATION

    //FUNCTIONS LIST-ITEM SELECTION AND SEND TO PLAYER
    public void startMedia(){
        if (queue.get(currentMedia.get()).getPath().toLowerCase().endsWith(".mp4")){
            isAvideo.set(true);
        }
        else isAvideo.set(false);
        Player.getInstance().pauseMedia();
        Player.getInstance().createMedia(currentMedia.get());

    }

    public void changeMedia(Integer direction){
        // TODO: 07/06/2022 vedere se vale la pena di fare in modo che non richiami canzoni già suonate
        if(shuffleActive) {
            Random random=new Random();
            int nextMedia=random.nextInt(0,queue.size());
            while (nextMedia==getCurrentMedia())
                nextMedia=random.nextInt(0,queue.size());
            setCurrentMedia(nextMedia);
        }
        else {
            setCurrentMedia(getCurrentMedia()+direction);
        }
    }
    //END LIST-ITEM SELECTION AND SEND TO PLAYER


}
