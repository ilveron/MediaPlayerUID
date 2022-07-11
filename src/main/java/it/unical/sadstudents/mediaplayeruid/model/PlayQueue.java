package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.utils.ListedDataInterface;
import it.unical.sadstudents.mediaplayeruid.utils.ThreadManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayQueue implements ListedDataInterface {
    //VARIABLES
    private ObservableList<MyMedia> queue;
    private SimpleIntegerProperty currentMedia = new SimpleIntegerProperty(0) ;
    private SimpleBooleanProperty shuffleActive = new SimpleBooleanProperty(false);
    private Integer shuffleQueueCurrentIndex;
    private ArrayList<Integer> shuffleQueueIndexes;
    private SimpleBooleanProperty deletingInProcess = new SimpleBooleanProperty(false);
    //END VARIABLES

    //SINGLETON
    private static PlayQueue instance = null;
    private PlayQueue (){
        queue = FXCollections.observableArrayList();
        shuffleQueueCurrentIndex = 0;
        currentMediaProperty().addListener(observable -> {
            if(queue.size()>0)
                startMedia();
        } );

    }

    public static PlayQueue getInstance(){
        if (instance==null)
            instance = new PlayQueue();
        return instance;
    }
    //END SINGLETON CLASS

    //GETTERS AND SETTERS


    public boolean isDeletingInProcess() {
        return deletingInProcess.get();
    }

    public SimpleBooleanProperty deletingInProcessProperty() {
        return deletingInProcess;
    }

    public void setDeletingInProcess(boolean deletingInProcess) {
        this.deletingInProcess.set(deletingInProcess);
    }

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

    public SimpleBooleanProperty shuffleActiveProperty() {
        return shuffleActive;
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
        //DatabaseManager.getInstance().insertPlayQueue(myMedia.getPath(),queue.size()-1);
        if(shuffleActive.get())
            generateShuffleList();

        if(!Player.getInstance().isMediaLoaded())
            //Player.getInstance().createMedia(currentMedia.get());
            startMedia();
    }

    public void setList(MyMedia myMedia){
        queue.add(myMedia);
    }

    @Override
    public void addFilesToList(List<File> files) {
        try {
            ThreadManager.getInstance().createMediaBis(files,true,false);
        } catch (InterruptedException e) {
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

        if(Player.getInstance().isMediaLoaded()){
            Player.getInstance().stop();
        }

        if(!isDeletingInProcess())
            Player.getInstance().createMedia(currentMedia.get());

    }

    public void changeMedia(Integer direction){

        if(Player.getInstance().isLoopMode())
            setCurrentMedia(getCurrentMedia());
        else if(shuffleActive.get()) {
            shuffleQueueCurrentIndex += direction;
            if(shuffleQueueCurrentIndex < 0 || shuffleQueueCurrentIndex >= shuffleQueueIndexes.size())
                shuffleQueueCurrentIndex = 0;
            setCurrentMedia(shuffleQueueIndexes.get(shuffleQueueCurrentIndex));
        }
        else{
            setCurrentMedia(getCurrentMedia()+direction);
        }
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
            shuffleQueueIndexes.add(getCurrentMedia());
            while(shuffleQueueIndexes.size() != queue.size())
                shuffleQueueIndexes.add(generateRandomForShuffle());
        }
    }

    public void deleteFromOtherController(MyMedia myMedia){
        for(int pos=0;pos<queue.size();pos++){
            if(myMedia.getPath().equals(queue.get(pos).getPath())){
                if(Player.getInstance().isMediaLoaded() && Player.getInstance().getMediaPlayer().getMedia().getSource().equals(queue.get(pos).getPath())) {
                    Player.getInstance().stop();
                    if(currentMedia.get()>0)
                        currentMedia.set(currentMedia.get()-1);

                }
                DatabaseManager.getInstance().deleteMedia(myMedia.getPath(),"Playqueue");
                if(currentMedia.get()>pos)
                    currentMedia.set(currentMedia.get()-1);
                queue.remove(pos);
                pos--;
            }
        }
    }

    public void clearQueue(){
        queue.clear();
        currentMedia.set(0);
        shuffleActive.set(false);
        shuffleQueueCurrentIndex = 0;
        if(shuffleQueueIndexes != null && shuffleQueueIndexes.size() > 0)
            shuffleQueueIndexes.clear();
    }

    public void removeMedia(int i){
        if(getCurrentMedia()==i){
            Player.getInstance().stop();
            getQueue().remove(i);
            currentMedia.set(currentMedia.get()-1);
            if(i<queue.size())
                startMedia();
        }
        else {
            getQueue().remove(i);
            currentMedia.set(currentMedia.get()-1);

        }
    }

    public void canRestart(){
        deletingInProcess.set(false);
        if(queue.size()>0)
            startMedia();
    }

}
