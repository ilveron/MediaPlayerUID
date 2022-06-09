package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.controller.RecentMediaTemplateController;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RecentMedia extends Pane {

    private MyMedia myMedia;

    public RecentMedia(MyMedia myMedia){
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("recentMediaTemplate-view.fxml"));
        try{
            AnchorPane root = loader.load();
            RecentMediaTemplateController controller = loader.getController();
            controller.init(myMedia);
            this.getChildren().add(root);
            //root.prefWidthProperty().bind(this.widthProperty());
        }catch(Exception ignoredException){}

    }

    public MyMedia getMyMedia(){return myMedia;}

}
