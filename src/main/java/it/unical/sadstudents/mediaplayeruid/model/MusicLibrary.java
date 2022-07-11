package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.utils.ListedDataInterface;
import it.unical.sadstudents.mediaplayeruid.utils.ThreadManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Comparator;
import java.util.List;

public class MusicLibrary implements ListedDataInterface {
    //VARIABLES
    private ObservableList<MyMedia> Library;
    //public int sortTF=0;

    //SINGLETON AND CLASS DECLARATION
    private static MusicLibrary instance = null;
    private MusicLibrary (){
        Library = FXCollections.observableArrayList();
    }
    public static MusicLibrary getInstance(){
        if (instance==null)
            instance = new MusicLibrary();
        return instance;
    }
    //END SINGLETON

    //GETTERS
    public ObservableList<MyMedia> getMusicLibrary() {
        return Library;
    }

    private SimpleBooleanProperty refreshButton=new SimpleBooleanProperty(false);

    public boolean isRefreshButton() {return refreshButton.get();}
    public SimpleBooleanProperty refreshButtonProperty() {return refreshButton;}
    public void setRefreshButton(boolean refreshButton) {this.refreshButton.set(refreshButton);}

    //FUNCTIONS
    @Override
    public void clearList() {
        PlayQueue.getInstance().setDeletingInProcess(true);
        for (int i=0;i<Library.size();i++){
            Home.getInstance().removeItem(Library.get(i));
            PlayQueue.getInstance().deleteFromOtherController(Library.get(i));
            PlaylistCollection.getInstance().deleteMediaCompletely(Library.get(i).getPath());
        }

        Library.clear();
        setRefreshButton(true);
        DatabaseManager.getInstance().deleteAllLibrary("MusicLibrary");
        PlayQueue.getInstance().canRestart();

    }

    @Override
    public void addFileToListFromOtherModel(MyMedia myMedia) {
        for (MyMedia myMedia1: Library){
            if (myMedia.equals(myMedia1))
                return;
        }
        Library.add(myMedia);
        setRefreshButton(true);
    }


    @Override
    public void addFilesToList(List<File> files) {
        try {
            ThreadManager.getInstance().createMediaBis(files,false,false);
        } catch (InterruptedException e) {
        }

    }
    public int isPresent(MyMedia m,ObservableList<MyMedia> list){
        for(int i=0;i<list.size();i++){
            if(m.equals(list.get(i))) return i;
        }
        return -1;
    }


    public void sortList(){
        Library.sort(Comparator.comparing(MyMedia::toString));
    }

    public void deleteWithIndex(int index){
        if(index>=0){
            Home.getInstance().removeItem(Library.get(index));
            PlaylistCollection.getInstance().deleteMediaCompletely(Library.get(index).getPath());
            PlayQueue.getInstance().deleteFromOtherController(Library.get(index));
            DatabaseManager.getInstance().deleteMedia(Library.get(index).getPath(),"MyMedia");

            MusicLibrary.getInstance().getMusicLibrary().remove(index);
            setRefreshButton(true);
        }
    }

    public void deleteStandard(MyMedia myMedia){
        Home.getInstance().removeItem(myMedia);
        PlaylistCollection.getInstance().deleteMediaCompletely(myMedia.getPath());
        PlayQueue.getInstance().deleteFromOtherController(myMedia);
        DatabaseManager.getInstance().deleteMedia(myMedia.getPath(),"MyMedia");

        MusicLibrary.getInstance().getMusicLibrary().remove(myMedia);
        setRefreshButton(true);
    }



    public void deleteAll(){
        for(int i=0;i<Library.size();i++){
            deleteCheck(Library.get(i));
        }
        Library.clear();
        DatabaseManager.getInstance().deleteAllLibrary("MusicLibrary");
        PlayQueue.getInstance().canRestart();

    }
    public void deleteCheck(MyMedia myMedia){
        Home.getInstance().removeItem(myMedia);
        PlayQueue.getInstance().deleteFromOtherController(myMedia);
        PlaylistCollection.getInstance().deleteMediaCompletely(myMedia.getPath());
    }

    //END FUNCTIONS

}
