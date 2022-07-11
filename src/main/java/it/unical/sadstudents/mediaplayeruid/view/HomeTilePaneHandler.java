package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.model.Home;
import it.unical.sadstudents.mediaplayeruid.utils.ImageCreator;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class HomeTilePaneHandler {
    private SimpleIntegerProperty readyInteger = new SimpleIntegerProperty(0);
    private ArrayList<MyMediaSingleBox> myMediaSingleBoxes = new ArrayList<>();
    private Thread thread;


    //SINGLETON
    private static HomeTilePaneHandler instance = null;
    private HomeTilePaneHandler(){    }
    public static HomeTilePaneHandler getInstance(){
        if (instance==null)
            instance = new HomeTilePaneHandler();
        return instance;
    }
    //END SINGLETON

    //GETTERS AND SETTERS
    public int getReadyInteger() {
        return readyInteger.get();
    }
    public SimpleIntegerProperty readyIntegerProperty() {
        return readyInteger;
    }
    public void setReadyInteger(int readyInteger) {
        this.readyInteger.set(readyInteger);
    }
    public ArrayList<MyMediaSingleBox> getMyMediaSingleBoxes() {
        return myMediaSingleBoxes;
    }
    public void setMyMediaSingleBoxes(ArrayList<MyMediaSingleBox> myMediaSingleBoxes) {
        this.myMediaSingleBoxes = myMediaSingleBoxes;
    }

    public void listCreator(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<Home.getInstance().getRecentMedia().size(); i++){
                    MyMediaSingleBox myMediaSingleBox = new MyMediaSingleBox(Home.getInstance().getRecentMedia().get(i),"home");
                    myMediaSingleBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                                if(mouseEvent.getClickCount() == 1 ){
                                    getMyMediaSingleBoxes().forEach(myMediaSingleBox1 -> myMediaSingleBox1.getStyleClass().remove("selectedRecentMedia"));
                                    myMediaSingleBox.requestFocus();
                                    myMediaSingleBox.getStyleClass().add("selectedRecentMedia");
                                }
                            }
                        }
                    });
                    myMediaSingleBox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                        @Override
                        public void handle(ContextMenuEvent contextMenuEvent) {
                            myMediaSingleBox.contextMenu(myMediaSingleBox,contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
                        }
                    });
                    myMediaSingleBoxes.add(myMediaSingleBox);
                    readyInteger.set(readyInteger.get()+1);
                }
                for (int i = 0; i< myMediaSingleBoxes.size(); ++i){
                    ImageCreator imageCreator = new ImageCreator();
                    imageCreator.setPane(myMediaSingleBoxes.get(i));
                    imageCreator.start();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void moveWithIndex(int i){
        myMediaSingleBoxes.add(myMediaSingleBoxes.get(i));
        readyInteger.set(readyInteger.get()+1);
        myMediaSingleBoxes.remove(i);
        readyInteger.set(readyInteger.get()-1);
    }

    public void removeWithIndex(int i){
        myMediaSingleBoxes.remove(i);
        if(readyInteger.get()>0)
            readyInteger.set(readyInteger.get()-1);
    }

    public void addSingleItem(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MyMediaSingleBox myMediaSingleBox = new MyMediaSingleBox(Home.getInstance().getRecentMedia().get(Home.getInstance().getRecentMedia().size()-1),"home");
                myMediaSingleBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                            if(mouseEvent.getClickCount() == 1){
                                myMediaSingleBox.requestFocus();
                            }
                        }
                    }
                });
                myMediaSingleBox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent contextMenuEvent) {
                        myMediaSingleBox.contextMenu(myMediaSingleBox,contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
                    }
                });


                myMediaSingleBoxes.add(myMediaSingleBox);
                readyInteger.set(readyInteger.get()+1);

                ImageCreator imageCreator = new ImageCreator();
                imageCreator.setPane(myMediaSingleBoxes.get(myMediaSingleBoxes.size()-1));
                imageCreator.start();
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

}
