package it.unical.sadstudents.mediaplayeruid.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class VideoLibrary {
    //VARIABLES
    private Integer videoNumber= 0; //numero di video presenti nella libreria
    private ObservableList<MyMedia> videoLibrary;

    //SINGLETON AND CLASS DECLARATION
    private static VideoLibrary instance = null;

    private VideoLibrary (){
        videoLibrary = FXCollections.observableArrayList();
    }

    public static VideoLibrary getInstance(){
        if (instance==null)
            instance = new VideoLibrary();
        return instance;
    }
    //END SINGLETON


    //GETTERS AND SETTERS
    public Integer getVideoNumber() {return videoNumber;}
    public ObservableList<MyMedia> getVideoLibrary() {return videoLibrary;}

    public void setVideoNumber(Integer videoNumber) {this.videoNumber = videoNumber;}
    //END GETTERS AND SETTERS




    /*public void addToVideoLibrarySingleFile(){



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
        if(!Player.getInstance().getIsRunning())
            Player.getInstance().createMedia(currentMedia.get());

    }

    public void addFolderToQueue(ArrayList<File> files){
        for(File file: files){
            MyMedia myMedia = new MyMedia(file);
            queue.add(myMedia);
            if(!Player.getInstance().getIsRunning())
                Player.getInstance().createMedia(currentMedia.get());
        }
    }

*/


}
