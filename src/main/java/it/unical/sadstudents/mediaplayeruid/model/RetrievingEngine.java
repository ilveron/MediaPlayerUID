package it.unical.sadstudents.mediaplayeruid.model;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class RetrievingEngine {

    private static RetrievingEngine instance = null;

    private RetrievingEngine(){}

    public static RetrievingEngine getInstance(){
        if (instance==null)
            instance = new RetrievingEngine();
        return instance;
    }


    public File retrieveFile(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file to add");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Music Files","*.mp3", "*.wav");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }

    public ArrayList<File> retrieveFolder(){
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose the directory to add");

        File directory = new File(String.valueOf(directoryChooser.showDialog(stage)));
        ArrayList<File> directoryList = new ArrayList<File>();
        directoryList.add(directory);
        ArrayList<File> myFileList = new ArrayList<File>();


        while(!directoryList.isEmpty()){
            directoryList.get(0).listFiles( new FileFilter() {
               @Override
               public boolean accept(File pathname) {
                   if(pathname.isDirectory()){
                       directoryList.add(pathname);
                       return true;
                   }
                   else if(pathname.getName().toLowerCase().endsWith(".mp3") || pathname.getName().toLowerCase().endsWith(".wav")){
                       myFileList.add(pathname);
                   }
               return false;}
                });

            directoryList.remove(0);
        }

        return myFileList;
    }


}
