package it.unical.sadstudents.mediaplayeruid.utils;


import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.view.MyMediaSingleBox;
import it.unical.sadstudents.mediaplayeruid.view.VideoGalleryTilePaneHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class SearchForFile {

    private static SearchForFile instance = null;
    private SearchForFile() {}
    public static SearchForFile getInstance(){
        if (instance==null)
            instance = new SearchForFile();
        return instance;
    }

    public ObservableList<MyMedia> getSearch(String find, ObservableList<MyMedia> table) {
        ObservableList<MyMedia> Search=FXCollections.observableArrayList();
        for(int i=0;i<table.size();i++){
            boolean add=false;
            if(table.get(i).getTitle().toLowerCase().indexOf(find.toLowerCase())!=-1)
                add=true;
            else if (table.get(i).getArtist().toLowerCase().indexOf(find.toLowerCase())!=-1)
                add=true;
            else if(table.get(i).getGenre().toLowerCase().indexOf(find.toLowerCase())!=-1)
                add=true;
            else if(table.get(i).getAlbum().toLowerCase().indexOf(find.toLowerCase())!=-1)
                add=true;
            else if(table.get(i).getYear().toString().indexOf(find)!=-1)
                add=true;
            else if(table.get(i).getLength().toString().indexOf(find)!=-1)
                add=true;
            if(add)
                Search.add(table.get(i));
        }
        return Search;
    }

    public ArrayList<MyMediaSingleBox> getSearchVideo(String find){
        ArrayList<MyMediaSingleBox> Search=new ArrayList<>();
        ArrayList<MyMediaSingleBox> Video=VideoGalleryTilePaneHandler.getInstance().getMyMediaSingleBoxes();
        for(int i=0;i< Video.size();i++){
            boolean add=false;
            if(Video.get(i).getMyMedia().getTitle().toLowerCase().indexOf(find.toLowerCase())!=-1)
                add=true;
            else if (Video.get(i).getMyMedia().getArtist().toLowerCase().indexOf(find.toLowerCase())!=-1)
                add=true;
            else if(Video.get(i).getMyMedia().getGenre().toLowerCase().indexOf(find.toLowerCase())!=-1)
                add=true;
            else if(Video.get(i).getMyMedia().getAlbum().toLowerCase().indexOf(find.toLowerCase())!=-1)
                add=true;
            else if(Video.get(i).getMyMedia().getYear().toString().indexOf(find)!=-1)
                add=true;
            else if(Video.get(i).getMyMedia().getLength().toString().indexOf(find)!=-1)
                add=true;
            if(add)
                Search.add(Video.get(i));
        }
        return Search;
    }

}

