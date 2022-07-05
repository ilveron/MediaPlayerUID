package it.unical.sadstudents.mediaplayeruid.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchForFile {

    private static SearchForFile instance = null;
    private SearchForFile() {}
    public static SearchForFile getInstance(){
        if (instance==null)
            instance = new SearchForFile();
        return instance;
    }

    public ObservableList<MyMedia> getSearch(String find,ObservableList<MyMedia> table) {
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
}
