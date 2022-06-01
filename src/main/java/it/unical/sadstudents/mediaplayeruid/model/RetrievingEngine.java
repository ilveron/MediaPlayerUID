package it.unical.sadstudents.mediaplayeruid.model;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
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

    /*public File[] retrieveFolder(){
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose the directory to add");

        File directory = new File(String.valueOf(directoryChooser.showDialog(stage)));
        ArrayList<File> directoryList = new ArrayList<File>();
        directoryList.add(directory);
        ArrayList<File> tempDirectoryList = new ArrayList<File>();
        tempDirectoryList.add(directory);

        File[] temp;
        while(!tempDirectoryList.isEmpty()){
            System.out.println(tempDirectoryList.get(0));
            temp = tempDirectoryList.get(0).listFiles( new FileFilter() {
                   @Override
                   public boolean accept(File pathname) {
                       System.out.println("Sto esaminando il path "+ pathname );
                       System.out.println(pathname.isDirectory());
                       return pathname.isDirectory();}
               }
            );
            for (File dir: temp){
                System.out.println("Sono Dir "+ dir);
                directoryList.add(dir);
                tempDirectoryList.add(dir);
            }
            temp = null;
            System.out.println("temp ha una dimensione di "+ temp);
            tempDirectoryList.remove(0);
            if(tempDirectoryList.size()>0)System.out.println("sono il prossimo target "+tempDirectoryList.get(0));

        }

        File[] files = null;
        for(File file: directoryList ){
            System.out.println(file);
            files = file.listFiles((new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith(".wav");
                }
            }));
            System.out.println("sono temp media, il tuo incubo e sono grande "+files.length);
        }


        return files;
    }*/


}
