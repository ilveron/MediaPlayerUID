package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.MusicLibrary;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.DataExchangePlaylist;
import it.unical.sadstudents.mediaplayeruid.model.VideoLibrary;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMediaToPlaylistController implements Initializable {

    ArrayList<Integer> PosizioniSelezionate;

    @FXML
    private TableColumn<MyMedia,String> title, artist, album, genre;
    @FXML
    private TableColumn<MyMedia, Integer> year;
    @FXML
    private TableColumn<MyMedia, Double> length;


    @FXML
    private Button buttonMusicLibrary;

    @FXML
    private Button buttonSearch;

    @FXML
    private Button buttonVideoLibrary;

    @FXML
    private Button ButtonAddToPlayList;


    @FXML
    private ImageView imageMedia;

    @FXML
    private Label labelTitle;


    @FXML
    private TableView<MyMedia> tableView;

    @FXML
    private TextField textField;



    @FXML
    void onMusicLibrary(ActionEvent event) {
        reset();
        tableView.setItems(MusicLibrary.getInstance().getMusicLibrary());
    }

    @FXML
    void onSearch(ActionEvent event) {

    }

    @FXML
    void onVideoLibrary(ActionEvent event) {
        reset();
        tableView.setItems(VideoLibrary.getInstance().getVideoLibrary());
    }

    @FXML
    void onAddToPlaylist(ActionEvent event) {
        for(int i=0;i<PosizioniSelezionate.size();i++){
            DataExchangePlaylist.getInstance().addPlaylist(tableView.getItems().get(PosizioniSelezionate.get(i)));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PosizioniSelezionate=new ArrayList<Integer>(); //se pos >0  allora il pulsante diventa cliccabile
        //start tooltip
        setButtonAdd(true);
        String image= DataExchangePlaylist.getInstance().getImage();
        if(image!=null&&image!="")
            imageMedia.setImage(new Image(image));
        labelTitle.setText(DataExchangePlaylist.getInstance().getName());

        tableView.setItems(MusicLibrary.getInstance().getMusicLibrary());
        title.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("album"));
        genre.setCellValueFactory(new PropertyValueFactory<MyMedia,String>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<MyMedia,Integer>("year"));
        length.setCellValueFactory(new PropertyValueFactory<MyMedia,Double>("length"));


        tableView.setRowFactory(tableView ->{
            final TableRow<MyMedia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                //if(event.getClickCount() > 1 && !row.isEmpty()){
                //    DataExchangePlaylist.getInstance().addPlaylist(row.getItem());
                //}else
                if(event.getClickCount()==1 && !row.isEmpty()){
                    int index=presente(row.getIndex());
                    if(index!=-1){
                        row.setStyle("-fx-background-color: white;"); // TODO: 02/07/2022  vedere che colore mettere
                        row.setFocusTraversable(false);
                        PosizioniSelezionate.remove(index);
                    }else {
                        setButtonAdd(false);
                        row.setFocusTraversable(false);
                        PosizioniSelezionate.add(row.getIndex());
                        row.setStyle("-fx-background-color: RED;");
                    }

                }else if(row.isEmpty()){
                    System.out.println("clear");
                    reset();
                    row.setFocusTraversable(false);
                    PosizioniSelezionate.clear();
                    setButtonAdd(true);

                }

            });
            return row;
        });
    }
    public void setButtonAdd(boolean enable){
        ButtonAddToPlayList.setDisable(enable);
    }

    public int presente(int index){
        for(int i=0;i<PosizioniSelezionate.size();i++) {
            if (PosizioniSelezionate.get(i) == index)
                return i;
        }
        return -1;
    }


    public void reset(){
        // TODO: 02/07/2022  non funziona il reset 
        for(int i=0;i<PosizioniSelezionate.size();i++){
            TableRow<MyMedia> row=tableView.getRowFactory().call(tableView);
            row.setStyle("-fx-background-color: white;");
        }
    }
    /*
    public void setData(ObservableList<MyMedia> list,String image, String title){
        System.out.println("ok ok");
        imageMedia.setImage(new Image(image));
        labelTitle.setText(title);
    }*/

}

