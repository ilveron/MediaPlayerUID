package it.unical.sadstudents.mediaplayeruid.thread;

import it.unical.sadstudents.mediaplayeruid.model.*;
import javafx.concurrent.Task;

import java.io.File;
import java.util.List;

public class MediaCreatorThread extends Task<List<File>> {
    private List<File> files;
    private List<MyMedia> myMediaList;

    public MediaCreatorThread(List<File> files, List<MyMedia> myMediaList) {
        this.files = files;
        this.myMediaList = myMediaList;
    }

    @Override
    protected List<File> call() throws Exception {
        int cont=0;
        for (int i=0; i<files.size(); i++){
            MyMedia myMedia = new MyMedia(files.get(i));
            myMediaList.add(myMedia);
            System.out.println(++cont);
            if(i==0){
                PlayQueue.getInstance().generateNewQueue(myMedia);
            }
            else
                PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);

            if (myMedia.getPath().toLowerCase().endsWith(".mp4")){
                VideoLibrary.getInstance().addFileToListFromOtherModel(myMedia);
            }
            else{
                MusicLibrary.getInstance().addFileToListFromOtherModel(myMedia);}
        }

        ThreadManager.getInstance().generateMetadataBis(myMediaList);

        return null;
    }
}
