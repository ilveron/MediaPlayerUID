package it.unical.sadstudents.mediaplayeruid.controller;

        import it.unical.sadstudents.mediaplayeruid.model.Player;
        import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
        import javafx.beans.property.SimpleStringProperty;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.layout.Pane;
        import javafx.scene.media.MediaPlayer;
        import javafx.scene.media.MediaView;

        import java.net.URL;
        import java.util.ResourceBundle;

public class VideoController implements Initializable {

    @FXML
    private MediaView mediaView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Player.getInstance().setMediaView(mediaView);


        mediaView.fitWidthProperty().bind(SceneHandler.getInstance().getStage().widthProperty());
        mediaView.fitHeightProperty().bind(SceneHandler.getInstance().getStage().heightProperty());

    }
}