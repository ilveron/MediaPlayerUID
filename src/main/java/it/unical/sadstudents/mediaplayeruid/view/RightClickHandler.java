package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.controller.RightClickController;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class RightClickHandler extends Pane {
    private MyMedia myMedia;
    private String source;

    //GETTERS AND SETTERS
    public MyMedia getMyMedia() { return myMedia; }
    public void setMyMedia(MyMedia myMedia) { this.myMedia = myMedia; }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    //END GETTERS AND SETTERS


    public RightClickHandler(MyMedia myMedia,Double x, Double y,String source){
        this.source = source;
        this.myMedia = myMedia;
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("rightClick-view.fxml"));
        try{
            AnchorPane root = loader.load();
            //root.setLayoutX(x);
            //root.setLayoutY(y);

            RightClickController controller=loader.getController();
            controller.init(myMedia,source);
            this.getChildren().add(root);
        }catch(Exception ignoredException){}

    }



}
