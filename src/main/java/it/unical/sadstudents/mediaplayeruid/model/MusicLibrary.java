package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

public class MusicLibrary implements DataListedModel{
    //VARIABLES
    private ObservableList<MyMedia> Library;
    private Integer KMusic=0;

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
    public Integer getKMusic() {
        return KMusic;
    }


    //FUNCTIONS
    @Override
    public void clearList() {
        Library.clear();
    }

    @Override
    public void addFileToList(File file) {
        MyMedia myMedia = new MyMedia(file);
        Library.add(myMedia);
        if(getKMusic()>1) Library.sort(Comparator.comparing(MyMedia::toString));
        ++KMusic;
  }

    @Override
    public void addFolderToList(ArrayList<File> files) {
        for(File file: files){
            MyMedia myMedia = new MyMedia(file);
            Library.add(myMedia);
            if(getKMusic()>1) Library.sort(Comparator.comparing(MyMedia::toString));
            ++KMusic;
        }
    }

    public void sortList(){
        if(getKMusic()>1) Library.sort(Comparator.comparing(MyMedia::toString));
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
