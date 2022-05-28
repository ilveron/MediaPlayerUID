package it.unical.sadstudents.mediaplayeruid.controller;




import it.unical.sadstudents.mediaplayeruid.model.DatabaseManager;
import it.unical.sadstudents.mediaplayeruid.model.MusicLibrary;
import it.unical.sadstudents.mediaplayeruid.view.MidHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    public MidHandler subScenaAttuale; //se settata a private, rimane grigia come se non venisse mai usata
    private MusicLibrary myMusicLibrary;

    DatabaseManager databaseManager;



    @FXML
    private BorderPane myBorderPane;

    @FXML
    private Slider sliderMediaPlayed;

    @FXML
    private Label timeMediaPlayed, nameMediaPlayed, durationMediaPlayed;

    @FXML
    private ToolBar toolbarMenu;



    @FXML
    void onHome(ActionEvent event) {
        Pane subScenePane = subScenaAttuale.getInstance().init();
        myBorderPane.setCenter(subScenePane);
    }

    @FXML
    void onMusicLibrary(ActionEvent event) {
        Pane subScenePane = subScenaAttuale.getInstance().MusicLibrary();
        myBorderPane.setCenter(subScenePane);

    }

    @FXML
    void onVideoLibrary(ActionEvent event) {
        Pane subScenePane = subScenaAttuale.getInstance().VideoLibrary();
        myBorderPane.setCenter(subScenePane);

    }

    @FXML
    void onPlayQueue(ActionEvent event) {
        Pane subScenePane = subScenaAttuale.getInstance().PlayQueue();
        myBorderPane.setCenter(subScenePane);

    }

    @FXML
    void onPlayLists(ActionEvent event) {
        Pane subScenePane = subScenaAttuale.getInstance().Playlists();
        myBorderPane.setCenter(subScenePane);

    }


    @FXML
    void onSettings(ActionEvent event) {

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane subScenePane = subScenaAttuale.getInstance().init();
        myBorderPane.setCenter(subScenePane);

        try {
            this.testConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //myMusicLibrary = new MusicLibrary();


        /*databaseManager = new DatabaseManager();

        try {
            databaseManager.createConnection();
            databaseManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

    }



    public void testConnection() throws SQLException {
        String url = "jdbc:sqlite:db_name.db";
        Connection con = DriverManager.getConnection(url);
        if(con != null && !con.isClosed())
            System.out.println("Connected!");
    }


    @FXML
    void onVolume(ActionEvent event) {

    }


    @FXML
    void onShuffle(ActionEvent event) {

    }

    @FXML
    void onSkipBack(ActionEvent event) {

    }

    @FXML
    void onSkipForward(ActionEvent event) {

    }

    @FXML
    void onSpeedPlay(ActionEvent event) {

    }

    @FXML
    void onPrevious(ActionEvent event) {

    }

    @FXML
    void onProperties(ActionEvent event) {

    }

    @FXML
    void onRepeat(ActionEvent event) {

    }

    @FXML
    void onScreenMode(ActionEvent event) {

    }

    @FXML
    void onEquilizer(ActionEvent event) {

    }

    @FXML
    void onNext(ActionEvent event) {

    }
    @FXML
    void onPlayPause(ActionEvent event) {

    }
}
