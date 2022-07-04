package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.thread.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
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
    private ArrayList<Integer> alreadyPlayed;
    private SimpleIntegerProperty currentMedia = new SimpleIntegerProperty(0) ;
    private SimpleBooleanProperty isAVideo = new SimpleBooleanProperty(false);
    private boolean shuffleActive=false;
    private boolean shuffleQueueIndexesGenerated = false;
    private Integer shuffleQueueCurrentIndex;
    private ArrayList<Integer> shuffleQueueIndexes;
    //END VARIABLES

    //SINGLETON
    private static PlayQueue instance = null;
    private PlayQueue (){
        queue = FXCollections.observableArrayList();
        alreadyPlayed = new ArrayList<>();
        shuffleQueueCurrentIndex = 0;
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
            else if(newCurrentMedia >= getQueue().size())
                newCurrentMedia = 0;

            currentMedia.set(newCurrentMedia);

        }
        else
            currentMedia.set(0);
    }

    public ObservableList<MyMedia> getQueue(){
        return queue;
    }

    public boolean isShuffleQueueIndexesGenerated() {
        return shuffleQueueIndexesGenerated;
    }

    public void setShuffleQueueIndexesGenerated(boolean shuffleQueueIndexesGenerated) {
        this.shuffleQueueIndexesGenerated = shuffleQueueIndexesGenerated;
    }

    public ArrayList<Integer> getShuffleQueueIndexes() {
        return shuffleQueueIndexes;
    }

    public void setShuffleQueueIndexes(ArrayList<Integer> shuffleQueueIndexes) {
        this.shuffleQueueIndexes = shuffleQueueIndexes;
    }

    public Integer getShuffleQueueCurrentIndex() {
        return shuffleQueueCurrentIndex;
    }

    public void setShuffleQueueCurrentIndex(Integer shuffleQueueCurrentIndex) {
        this.shuffleQueueCurrentIndex = shuffleQueueCurrentIndex;
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
        /*for(File file: files){
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
        }*/
        try {
            ThreadManager.getInstance().createMediaBis(files,true,false);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        if(Player.getInstance().isMediaLoaded()){
            Player.getInstance().stop();
        }

        //Home.getInstance().addToRecentMedia(queue.get(currentMedia.get()));
        Player.getInstance().createMedia(currentMedia.get());

    }

    public void changeMedia(Integer direction){
        // TODO: 07/06/2022 vedere se vale la pena di fare in modo che non richiami canzoni già suonate
        alreadyPlayed.add(getCurrentMedia());

        if(Player.getInstance().isLoopMode())
            setCurrentMedia(getCurrentMedia());
        else if(shuffleActive) {
            shuffleQueueCurrentIndex += direction;
            if(shuffleQueueCurrentIndex < 0 || shuffleQueueCurrentIndex >= shuffleQueueIndexes.size())
                shuffleQueueCurrentIndex = 0;
            setCurrentMedia(shuffleQueueIndexes.get(shuffleQueueCurrentIndex));
        }
        else
            // TODO: 11/06/2022 SISTEMARE PASSAGGIO DA SHUFFLE A SEQUENZIALE E VICEVERSA 
            setCurrentMedia(getCurrentMedia()+direction);
    }
    //END LIST-ITEM SELECTION AND SEND TO PLAYER

    private int generateRandomForShuffle() {
        Random random = new Random();
        boolean validIndex = false;
        Integer index = Integer.MIN_VALUE;
        while(!validIndex){
            validIndex = true;
            index = random.nextInt(0, queue.size());
            for(Integer indexInList : shuffleQueueIndexes){
                if(index == indexInList){
                    validIndex = false;
                    break;
                }
            }
        }

        return index;
    }

    public void generateShuffleList(){
        shuffleQueueIndexes = new ArrayList<>();

        if(getQueue().size() > 0){
            //Se presenti, aggiungo le canzoni già riprodotte all'inizio di shuffleQueueIndexes per permettere la riproduzione a ritroso
            if(alreadyPlayed.size() > 0){
                for(Integer index : alreadyPlayed)
                    shuffleQueueIndexes.add(index);

                shuffleQueueCurrentIndex = alreadyPlayed.size();
            }

            while(shuffleQueueIndexes.size() != queue.size())
                shuffleQueueIndexes.add(generateRandomForShuffle());

            shuffleQueueIndexesGenerated = true;
        }
    }

}
