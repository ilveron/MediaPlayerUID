package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.MusicLibrary;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistTemplateController {

    @FXML
    private TableColumn<MyMedia, String> album;

    @FXML
    private AnchorPane anchorPanePlaylist;

    @FXML
    private TableColumn<MyMedia, String> artist;

    @FXML
    private TableColumn<MyMedia, String> genre;

    @FXML
    private ImageView imagePlaylist;

    @FXML
    private Label labelName;

    @FXML
    private TableColumn<MyMedia, Double> length;

    @FXML
    private TableView<MyMedia> tableViewPlaylist;

    @FXML
    private TableColumn<MyMedia, String> title;

    @FXML
    private TableColumn<MyMedia, Integer> year;

    public void init(ObservableList<MyMedia> myMedia,String image,String name) {
        // TODO: 03/06/2022 CARICAMENTO DA DATABASE
        if(image=="")
            image="file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png";

        labelName.setText(name);
        imagePlaylist.setImage(new Image(image));
        tableViewPlaylist.setItems(myMedia);
        title.setCellValueFactory(new PropertyValueFactory<MyMedia, String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<MyMedia, String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<MyMedia, String>("album"));
        genre.setCellValueFactory(new PropertyValueFactory<MyMedia, String>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<MyMedia, Integer>("year"));
        length.setCellValueFactory(new PropertyValueFactory<MyMedia, Double>("length"));
    }
}
