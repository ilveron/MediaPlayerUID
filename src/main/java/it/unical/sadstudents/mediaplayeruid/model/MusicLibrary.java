package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.utils.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.view.VideoGalleryTilePaneHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Comparator;
import java.util.List;

public class MusicLibrary implements DataListedModel{
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

        //if(getKMusic()>1) sortList();
    }


    @Override
    public void addFilesToList(List<File> files) {
        /*boolean isPresent = false;
        for (File file: files){
            MyMedia myMedia = ThreadManager.getInstance().createMyMedia(file);
            for (int i=0; i< Library.size() && !isPresent;i++){
                if(myMedia.equals(Library.get(i))){
                    isPresent = true;
                }
            }
            if (!isPresent){
                Library.add(myMedia);
                //if(getKMusic()>1) sortList();
                //sortTF++;
                ++KMusic;
            }
            isPresent=false;
        }*/
        try {
            ThreadManager.getInstance().createMediaBis(files,false,false);
        } catch (InterruptedException e) {
            e.printStackTrace();
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



    /* ORDINAMENTO HeapSort complessita o(nlogn)
    public void sort()
    {
        int n = Library.size();
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(n, i);
        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            MyMedia temp = Library.get(0);
            Library.set(0,Library.get(i));
            Library.set(i,temp);
            // call max heapify on the reduced heap
            heapify(i, 0);
        }
    }
    void heapify(int n, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2
        // If left child is larger than root
        if (l < n && Library.get(l).equals(Library.get(largest)))
            largest = l;
        // If right child is larger than largest so far
        if (r < n && Library.get(r).equals(Library.get(largest)))
            largest = r;
        // If largest is not root
        if (largest != i) {
            MyMedia swap = Library.get(i);
            Library.set(i,Library.get(largest));
            Library.set(largest,swap);
            // Recursively heapify the affected sub-tree
            heapify(n, largest);
        }
    }
     END ORDINAMENTO */



    //END FUNCTIONS

}
