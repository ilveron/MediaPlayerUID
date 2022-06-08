package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class RecentMedia {
    private Pane pane ;
    private ModuleLayer.Controller controller;

    public RecentMedia(){
        this.pane = new Pane();

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("recentMediaTemplate-view.fxml"));
            pane = new FXMLLoader().load(MainApplication.class.getResource("recentMediaTemplate-view.fxml"));

            Object controller = fxmlLoader.getController();
            //controller.
        }catch(IOException e){}
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public ModuleLayer.Controller getController() {
        return controller;
    }
}
