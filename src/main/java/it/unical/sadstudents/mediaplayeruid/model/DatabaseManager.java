package it.unical.sadstudents.mediaplayeruid.model;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    // TODO: 13/06/2022  ricordarsi di cacciare i print dei vari errori, e vedere i nomi delle tabelle
    //VARIABLE
    private Connection connection;
    private String url = "jdbc:sqlite:MediaPlayerDb.db";
    //END VARIABLE

    //SINGLETION
    private static DatabaseManager instance = null;

    private DatabaseManager(){connect();}

    public static DatabaseManager getInstance(){
        if (instance==null)
            instance = new DatabaseManager();
        return instance;
    }
    //END SINGLETION

    //GET AND SETTER
    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    //END GET AND SETTER

    //FUNCTION
    //  function private
    private boolean checkLibrary(String name){
        if(name=="MusicLibrary" || name =="VideoLibrary"||name=="empty") return true;
        return  false;
    }
    private boolean isPresent(String object,String key, String tab){
        try {
            if (connection != null && !connection.isClosed()) {
                String query = "select * from "+tab+" where "+object+"=?;";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1,key);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {return  true;}
                stmt.close();
            }
        }catch (SQLException e){e.printStackTrace();}
        return  false;
    } //gestisco se è presente o meno solo per non far apparire errore
    private boolean checkTable(String table){
        if(table=="MyMedia"||table=="Playlist"||table=="Settings"||table=="RecentMedia") return true;
        return false;
    }
    //  end function private

    public boolean connect(){
        try {
            connection = DriverManager.getConnection(url);
            if(connection != null && !connection.isClosed()) {
                System.out.println("Aperto");
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean disconnect(){
        try {
            if(connection!=null && !connection.isClosed()) {
                System.out.println("Chiuso");
                connection.close();
                return  true;
            }
        }catch (SQLException e){}
        return  false;
    }


    public boolean addMedia(MyMedia myMedia){
        try {
            if(connection != null && myMedia!=null && !connection.isClosed()&&!isPresent("Path",myMedia.getPath(),"MyMedia")) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO MyMedia VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                //stmt.setString(1, tab);
                stmt.setString(1, myMedia.getPath());
                stmt.setString(2, myMedia.getTitle());
                stmt.setString(3, myMedia.getArtist());
                stmt.setString(4, myMedia.getGenre());
                stmt.setString(5, myMedia.getAlbum());
                stmt.setString(6, myMedia.getImageUrl());
                stmt.setDouble(7, myMedia.getLength());
                stmt.setInt(8, myMedia.getYear());
                stmt.setInt(9,0);
                stmt.setInt(10,0);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e) {e.printStackTrace();}
        return false;
    }
    public boolean createPlaylist(String name,String image){
        try {
            if(connection != null && name!=null && !connection.isClosed()&&!isPresent("Name",name,"PlayList")) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO Playlist VALUES(?,?);");
                stmt.setString(1, name);
                stmt.setString(2,image);
                stmt.execute();
                return true;
            }
        }catch (SQLException e){}
        return false;
    } //// TODO: 15/06/2022  da controllare 
    public boolean addMyMediaInPlaylist(String pathMedia,String name){
        try {
            // TODO: 13/06/2022  controllare se esistono???
            if(connection != null && pathMedia!=null && name!=null && !connection.isClosed()) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO MyMediaPlaylist VALUES(?, ?);");
                stmt.setString(1, pathMedia);
                stmt.setString(2,   name);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e){}
        return false;
    } //// TODO: 15/06/2022  da controllare 
    public boolean insertRecentMedia(MyMedia myMedia){
        try {
            if(connection != null&&myMedia!=null&&!connection.isClosed()) {
                if (!isPresent("Path",myMedia.getPath(),"MyMedia")) {
                    addMedia(myMedia);
                }
                if(!isPresent("Path",myMedia.getPath(),"RecentMedia")) {
                    PreparedStatement stmt = connection.prepareStatement("INSERT INTO RecentMedia VALUES(?, ?)");
                    stmt.setString(1, myMedia.getPath());
                    //stmt.set(2,new SimpleDateFormat("yyyy-MM-dd")); calcolare la data
                    stmt.execute();
                    stmt.close(); //???
                    return  true;
                }
            }
        }catch (SQLException e){}
        return false;
    } // TODO: 15/06/2022  da controllare


    public boolean setLibrary(String pathMedia,String nameLibrary){
        try {
            if(connection != null && pathMedia!=null &&checkLibrary(nameLibrary) &&!connection.isClosed()&&isPresent("Path",pathMedia,"MyMedia")) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE MyMedia SET MusicLibrary=?, VideoLibrary=? WHERE Path=?;");
                if(nameLibrary=="MusicLibrary"){
                    stmt.setInt(1,1);
                    stmt.setInt(2,0);
                }else if(nameLibrary=="VideoLibrary"){
                    stmt.setInt(1,0);
                    stmt.setInt(2,1);
                }else{
                    stmt.setInt(1,0);
                    stmt.setInt(2,0);
                }
                stmt.setString(3,pathMedia);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e) {e.printStackTrace();}
        return false;
    }
    public boolean setMediaString(String date,String tipe,String pathMedia){
        try {
            if(connection != null && date!=null &&tipe!=null&&!connection.isClosed()) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE MyMedia SET "+tipe+ "=? WHERE path=?;");
                stmt.setString(1,date);
                stmt.setString(2,pathMedia);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e) {e.printStackTrace();}
        return false;

    }
    public boolean setMediaInt(int date,String tipe,String pathMedia){
        try {
            if(connection != null &&tipe!=null&&!connection.isClosed()) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE MyMedia SET "+tipe+ "=? WHERE path=?;");
                stmt.setInt(1,date);
                stmt.setString(2,pathMedia);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e) {e.printStackTrace();}
        return false;

    }
    public boolean setMediaDouble(Double date,String tipe,String pathMedia){
        try {
            if(connection != null && date!=null &&tipe!=null&&!connection.isClosed()) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE MyMedia SET "+tipe+ "=? WHERE path=?;");
                stmt.setDouble(1,date);
                stmt.setString(2,pathMedia);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e) {e.printStackTrace();}
        return false;

    }

    public ArrayList<MyMedia> receiveMyMedia(String filter){
        try {
            if(connection != null&&checkLibrary(filter)&&!connection.isClosed()) {
                String query = "select * from MyMedia;";
                if (filter != "empty") {
                    query = "select * from MyMedia where "+filter+"=true";
                }
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                //ArrayList<MyMedia> myMedia = new ArrayList<>();
                while (rs.next()) {
                    if(filter=="MusicLibrary") {
                        MusicLibrary.getInstance().getMusicLibrary().add(new MyMedia(rs.getString("Title"), rs.getString("Artist"),
                                rs.getString("Album"), rs.getString("Genre"), rs.getString("Path")
                                , rs.getDouble("Length"), rs.getInt("Year"), rs.getString("Image")));
                    }
                    else if(filter=="VideoLibrary"){
                        VideoLibrary.getInstance().getVideoLibrary().add(new MyMedia(rs.getString("Title"), rs.getString("Artist"),
                                rs.getString("Album"), rs.getString("Genre"), rs.getString("Path")
                                , rs.getDouble("Length"), rs.getInt("Year"), rs.getString("Image")));
                    }
                }
                stmt.close();
                return null;
            }

        }catch (SQLException e){e.printStackTrace();}
        return null;
    }
    public ArrayList<MyMedia> receiveMediaInPlaylist(String name){
        try {
            if(connection != null&&!connection.isClosed() && isPresent("Name",name,"Playlist")) {
                String query = "select * " +
                        "from MyMedia,Playlist,MyMediaPlaylist" +
                        "where MyMedia.Path=MyMediaPlaylist.Path and MyMediaPlaylist.id=Playlist.id and Playlist.Name=?" +
                        ";";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1,name);
                ResultSet rs = stmt.executeQuery();
                ArrayList<MyMedia> myMedia = new ArrayList<>();
                while (rs.next()) {
                    myMedia.add(new MyMedia(rs.getString("Title"), rs.getString("Artist"),
                            rs.getString("Album"), rs.getString("Genre"), rs.getString("Path")
                            , rs.getDouble("Length"), rs.getInt("Year"), rs.getString("Image")));
                }
                stmt.close();
                return myMedia;
            }

        }catch (SQLException e){}
        return null;
    } // TODO: 15/06/2022  da controllare
    public ArrayList<MyMedia> receiveRecentMedia(){
        try {
            if(connection != null&&!connection.isClosed()) {
                String query = "select * " +
                        "from MyMedia,RecentMedia " +
                        "where MyMedia.Path=RecentMedia.Path " +
                        ";";
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                ArrayList<MyMedia> myMedia = new ArrayList<>();
                while (rs.next()) {
                    myMedia.add(new MyMedia(rs.getString("Title"), rs.getString("Artist"),
                            rs.getString("Album"), rs.getString("Genre"), rs.getString("Path")
                            , rs.getDouble("Length"), rs.getInt("Year"), rs.getString("Image")));
                }
                stmt.close();
                return myMedia;
            }

        }catch (SQLException e){}
        return null;
    } //// TODO: 15/06/2022 da controllare


    public boolean deleteAll(String tab){
        try {
            if(connection != null&&checkTable(tab)&&!connection.isClosed()){
                PreparedStatement stmt=connection.prepareStatement("DELETE FROM "+tab+";");
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e){}
        return false;
    }
    public boolean deleteMedia(String pathMymedia, String tab){
        try {
            if(connection != null&&checkTable(tab)&&pathMymedia!=null && !connection.isClosed()&&isPresent("Path",pathMymedia,tab)){
                PreparedStatement stmt=connection.prepareStatement("DELETE FROM "+tab+" WHERE Path=?;");
                stmt.setString(1,pathMymedia);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e){ e.printStackTrace();}
        return false;
    }
    public boolean deletePlaylist(String name, String tab){
        try {
            if(connection != null&&name!=null && !connection.isClosed()){
                PreparedStatement stmt=connection.prepareStatement("DELETE FROM ? WHERE Name=?;");
                stmt.setString(1,tab);
                stmt.setString(2,name);
                return true;
            }
        }catch (SQLException e){}
        return false;
    } //// TODO: 15/06/2022 da controllare 
    //creare pure un delete RecentMedia?

    //se non è presente la tabella finisce nel catch e non esegue il codice

    //Table create tramite codice
    public void createTableMyMedia(){
        try {
            String query =
                    "CREATE TABLE IF NOT EXISTS MyMedia(Path VARCHAR(255)," +
                            "Title VARCHAR(100)," +
                            "Artist VARCHAR(100)," +
                            "Genre VARCHAR(100)," +
                            "Album VARCHAR(100)," +
                            "Image VARCHAR(255)," +
                            "Length FLOAT(25)," +
                            "Year INT," +
                            "MusicLibrary INT,"+
                            "VideoLibrary INT,"+
                            "PRIMARY KEY (Path));";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public void createTableRecentMedia(){
        try {
            String query =
                    "CREATE TABLE IF NOT EXISTS RecentMedia(Path VARCHAR(255)," +
                            "Date DATE," +
                            "FOREIGN KEY (Path) REFERENCES MyMedia(path),"+ // TODO: 15/06/2022 da vedere la data come calcolare millisecondi
                            "PRIMARY KEY (Path));";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public void createTableMediaMyMediaPlaylist(){
        try {
            String query =
                    "CREATE TABLE IF NOT EXISTS MyMediaPlaylist(Path VARCHAR(255)," +
                            "Name VARCHAR(100)," +
                            "FOREIGN KEY (Path) REFERENCES MyMedia(path),"+
                            "FOREIGN KEY (Name) REFERENCES Playlist(Name),"+
                            "PRIMARY KEY (Path,Name));";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public void createTablePlaylist(){
        try {
            String query =
                    "CREATE TABLE IF NOT EXISTS Playlist(Id INT AUTO_INCREMENT," +
                            "Name VARCHAR(100) NOT NULL," +
                            "Image VARCHAR(255)," +
                            "PRIMARY KEY (Id));";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }


    //END FUNCTION
}


