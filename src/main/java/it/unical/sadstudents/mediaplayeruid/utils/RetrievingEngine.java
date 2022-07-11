package it.unical.sadstudents.mediaplayeruid.utils;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;


public class RetrievingEngine {

    //TYPES: 0 is standard from home and queue , 1 from MusicLibrary and 2 from VideoLibrary

    //SINGLETON
    private static RetrievingEngine instance = null;
    private RetrievingEngine(){}



    public static RetrievingEngine getInstance(){
        if (instance==null)
            instance = new RetrievingEngine();
        return instance;
    }
    //END SINGLETON


    //SINGLE FILE SELECTION
    public List<File> retrieveFile(int type){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file to add");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Media Files","*.mp3", "*.wav","*.mp4");
        if (type==1)
            extFilter = new FileChooser.ExtensionFilter("Music Files","*.mp3", "*.wav");
        else if (type==2)
            extFilter = new FileChooser.ExtensionFilter("Video Files","*.mp4");

        fileChooser.getExtensionFilters().add(extFilter);
        List<File> files = fileChooser.showOpenMultipleDialog(stage);


        return files;

    }

    //ADD FILES FROM A MAIN FOLDER AND SCANNING ALL SUBFOLDER
    public List<File> retrieveFolder(int type){
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose the directory to add");

        File directory = new File(String.valueOf(directoryChooser.showDialog(stage)));
        ArrayList<File> directoryList = new ArrayList<File>();
        directoryList.add(directory);
       List<File> myFileList = new ArrayList<File>();


        while(!directoryList.isEmpty()){
            directoryList.get(0).listFiles( new FileFilter() {
               @Override
               public boolean accept(File pathname) {
                   if(pathname.isDirectory()){
                       directoryList.add(pathname);
                       return true;
                   }
                   else if (type==0){
                           if (pathname.getName().toLowerCase().endsWith(".mp3") || pathname.getName().toLowerCase().endsWith(".wav") || pathname.getName().toLowerCase().endsWith(".mp4")) {
                               myFileList.add(pathname);
                           }
                       }
                   else if (type==1) {
                           if (pathname.getName().toLowerCase().endsWith(".mp3") || pathname.getName().toLowerCase().endsWith(".wav")) {
                               myFileList.add(pathname);
                           }
                       }

                   else if(type==2) {
                               if (pathname.getName().toLowerCase().endsWith(".mp4")){
                                   myFileList.add(pathname);
                               }
                           }
                   return false;}
                });

            directoryList.remove(0);
        }



        return myFileList;
    }



}
