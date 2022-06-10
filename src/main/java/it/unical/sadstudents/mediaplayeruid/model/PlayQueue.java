package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.ThreadManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayQueue implements DataListedModel{
    //VARIABLES
    private ObservableList<MyMedia> queue;
    private ArrayList<String> alreadyPlayed;
    private SimpleIntegerProperty currentMedia = new SimpleIntegerProperty(0) ;
    private SimpleBooleanProperty isAVideo = new SimpleBooleanProperty(false);
    private boolean shuffleActive=false;
    //END VARIABLES

    //SINGLETON
    private static PlayQueue instance = null;
    private PlayQueue (){
        queue = FXCollections.observableArrayList();
        alreadyPlayed = new ArrayList<>();
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
    public boolean getIsAVideo() { return isAVideo.get(); }
    public SimpleBooleanProperty isAVideoProperty() { return isAVideo; }
    public void setIsAVideo(boolean isAVideo) { this.isAVideo.set(isAVideo); }

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
    public void addFileToListFromOtherModel(MyMedia myMedia) {
        queue.add(myMedia);
        if(!Player.getInstance().isMediaLoaded())
            //Player.getInstance().createMedia(currentMedia.get());
            startMedia();
    }

    @Override
    public void addFilesToList(List<File> files) {
        for(File file: files){
            MyMedia myMedia = ThreadManager.getInstance().createMyMedia(file);
            queue.add(myMedia);
            if (myMedia.getPath().toLowerCase().endsWith(".mp4")){
                VideoLibrary.getInstance().addFileToListFromOtherModel(myMedia);
            }
            else{
                MusicLibrary.getInstance().addFileToListFromOtherModel(myMedia);
            }

            if(!Player.getInstance().isMediaLoaded())
                //Player.getInstance().createMedia(currentMedia.get());
                startMedia();
        }
    }

    public void generateNewQueue(MyMedia myMedia){
        if(Player.getInstance().isMediaLoaded())
            Player.getInstance().stop();
        clearList();
        addFileToListFromOtherModel(myMedia);
    }



     //END FUNCTIONS LIST MANIPULATION


    //FUNCTIONS LIST-ITEM SELECTION AND SEND TO PLAYER
    public void startMedia(){
        if (queue.get(currentMedia.get()).getPath().toLowerCase().endsWith(".mp4")){
            isAVideo.set(true);
        }
        else isAVideo.set(false);
        if(Player.getInstance().isMediaLoaded())
            Player.getInstance().stop();
        Home.getInstance().addToRecentMedia(queue.get(currentMedia.get()));
        Player.getInstance().createMedia(currentMedia.get());
    }

    public void changeMedia(Integer direction){
        // TODO: 07/06/2022 vedere se vale la pena di fare in modo che non richiami canzoni già suonate
        alreadyPlayed.add(queue.get(getCurrentMedia()).getPath());
        if(shuffleActive) {
            int nextMedia = generateRandomForShuffle();
            if(nextMedia == -1){
                alreadyPlayed.clear();
                nextMedia = generateRandomForShuffle();
            }

            setCurrentMedia(nextMedia);
        }
        else {
            setCurrentMedia(getCurrentMedia()+direction);
        }
    }
    //END LIST-ITEM SELECTION AND SEND TO PLAYER

    private int generateRandomForShuffle() {
        Random random = new Random();
        int index = -1;
        boolean indexFound = false;
        int numbersGenerated = 0;
        while(!indexFound && (numbersGenerated <= queue.size())){
            indexFound = true;
            index = random.nextInt(0, queue.size());
            numbersGenerated++;

            String newPath = queue.get(index).getPath();
            for(int i = 0; i < alreadyPlayed.size(); ++i){
                if (newPath == alreadyPlayed.get(i)) {
                    indexFound = false;
                    break;
                }
            }
        }

        if(numbersGenerated > queue.size())
            return -1;

        return index;
    }


}
