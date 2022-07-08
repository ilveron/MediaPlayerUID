package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.Settings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class SubStageHandler {
    private Stage stage;
    private Scene scene;
    private Alert alert;

    //SINGLETON
    private static SubStageHandler instance = null;
    private SubStageHandler(){ alert = new Alert(Alert.AlertType.NONE);   }
    public static SubStageHandler getInstance(){
        if (instance==null)
            instance = new SubStageHandler();
        return instance;
    }
    //END SINGLETON

    public void init(String source,double width, double height,String title,boolean resizable){
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(source));
        try{
            scene = new Scene(loader.load(),width,height);
            stage = new Stage();
            stage.setResizable(resizable);

            for (String font : Settings.fonts) {
                Font.loadFont(Objects.requireNonNull(MainApplication.class.getResourceAsStream(font)), 10);
            }

            scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/"+ Settings.theme+".css")).toExternalForm());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setMinHeight(280);
            stage.setMinWidth(484);
            stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("image/logoMediaPlayerUID-48x48.png")));
            stage.setScene(scene);
            stage.showAndWait();

            //stage.setOnCloseRequest
        }catch(Exception exception){
            exception.printStackTrace();}

    }
}
