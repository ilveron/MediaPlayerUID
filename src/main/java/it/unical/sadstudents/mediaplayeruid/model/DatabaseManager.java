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
    //public void setUrl(String url) {this.url = url;}
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
        if(table=="MyMedia"||table=="Playlist"||table=="Settings"||
           table=="RecentMedia"||table=="Playqueue"||table=="MyMediaPlaylist"||table=="Equalizer")
            return true;
        else  return false;
    }
    //  end function private

    public boolean connect(){
        try {
            connection = DriverManager.getConnection(url);
            if(connection != null && !connection.isClosed()) {
                System.out.println("Aperto DataBase");
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
                System.out.println("Chiuso DataBase");
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
                stmt.setString(7, myMedia.getLength());
                stmt.setString(8, myMedia.getYear());
                stmt.setInt(9,0);
                stmt.setInt(10,0);
                stmt.execute();
                stmt.close();

                return true;
            }
        }catch (SQLException e) {e.printStackTrace();}
        return false;
    }
    public boolean createPlaylist(String name,String image,Integer songs,String duration){
        try {
            if(connection != null && name!=null && !connection.isClosed()&&!isPresent("Name",name,"PlayList")) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO Playlist VALUES(?,?,?,?);");
                stmt.setString(1, name);
                stmt.setString(2,image);
                stmt.setInt(3,songs);
                stmt.setString(4,duration);
                stmt.execute();
                return true;
            }
        }catch (SQLException e){e.printStackTrace();}
        return false;
    }
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
    }
    public boolean insertRecentMedia(MyMedia myMedia){
        try {
            if(connection != null&&myMedia!=null&&!connection.isClosed()) {
                if (!isPresent("Path",myMedia.getPath(),"MyMedia")) {
                    addMedia(myMedia);
                }
                if(!isPresent("Path",myMedia.getPath(),"RecentMedia")) {
                    PreparedStatement stmt = connection.prepareStatement("INSERT INTO RecentMedia (Path) VALUES(?)");
                    stmt.setString(1, myMedia.getPath());
                    stmt.execute();
                    stmt.close(); //???
                    return  true;
                }
            }
        }catch (SQLException e){e.printStackTrace();}
        return false;
    }
    public boolean insertPlayQueue(String path){
        try {
            if(connection != null && path!=null  && !connection.isClosed()&&!isPresent("Path",path,"Playqueue")) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO Playqueue VALUES(?);");
                stmt.setString(1, path);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e){e.printStackTrace();}
        return false;
    }
    /*
    public boolean changeApplicationClosureData(){
        try {
            if(connection != null &&!connection.isClosed()) {
                PreparedStatement stmt = connection.prepareStatement(
                        "UPDATE ApplicationClosureData " +
                            "SET Path=?, Position=?,Volume=?,CurrentTime=?,TypeEqualizer=?,Theme=? " +
                                "WHERE Key=key;");

                if(PlayQueue.getInstance().getQueue().size()!=0)
                    stmt.setString(1,PlayQueue.getInstance().getQueue().get(PlayQueue.getInstance().getCurrentMedia()).getPath() );
                else
                    stmt.setString(1,null);
                stmt.setInt(2,PlayQueue.getInstance().getCurrentMedia());
                stmt.setDouble(3,Player.getInstance().getCurrentMediaTime() );
                stmt.setDouble(4,Player.getInstance().getVolume());
                stmt.setInt(5,AudioEqualizer.getInstance().getCurrentPresetIndex());
                stmt.setString(6, Settings.theme);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e) {e.printStackTrace();}
        return false;
    }
    */
    public boolean changePosixRecentMedia(String Path){
        try {
            if(isPresent("Path",Path,"RecentMedia")&&connection != null&&Path!=null&&!connection.isClosed()) {
                deleteMedia(Path,"RecentMedia");
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO RecentMedia (Path) VALUES(?)");
                stmt.setString(1, Path);
                stmt.execute();
                stmt.close();
                return  true;
            }
        }catch (SQLException e){e.printStackTrace();}
        return false;
    } // TODO: 05/07/2022 da controllare


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

    public boolean setPlaylistSong(Integer songs,String duration,String name){
        try {
            if(connection != null && songs!=-1 &&duration!=null&&!connection.isClosed()) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE Playlist SET Songs=?,TotalDuration=? WHERE Name=?;");
                stmt.setInt(1,songs);
                stmt.setString(2,duration);
                stmt.setString(3,name);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e) {e.printStackTrace();}
        return false;
    }

    public boolean changePlaylist(String NewName,String OldName,String Image){
        try {

            if(connection != null && NewName!=null&&OldName!=null &&Image!=null&& !connection.isClosed()) {
                PreparedStatement stmt = connection.prepareStatement
                        ("UPDATE Playlist " +
                        " SET Name=?, Image=?" +
                                " WHERE Name=?");
                stmt.setString(1, NewName);
                stmt.setString(2,Image);
                stmt.setString(3,OldName);
                stmt.execute();
                stmt.close();
                return changMediaPlaylist(NewName,OldName);
            }
        }catch (SQLException e){}
        return false;
    }
    private boolean changMediaPlaylist(String NewName,String OldName){
        try {
            if(connection != null && !connection.isClosed()) {
                PreparedStatement stmt = connection.prepareStatement
                        ("UPDATE MyMediaPlaylist " +
                                " SET Name=?" +
                                " WHERE Name=?");
                stmt.setString(1, NewName);
                stmt.setString(2,OldName);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e){}
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
                                , rs.getString("Length"), rs.getString("Year"), rs.getString("Image")));
                    }
                    else if(filter=="VideoLibrary"){
                        VideoLibrary.getInstance().getVideoLibrary().add(new MyMedia(rs.getString("Title"), rs.getString("Artist"),
                                rs.getString("Album"), rs.getString("Genre"), rs.getString("Path")
                                , rs.getString("Length"), rs.getString("Year"), rs.getString("Image")));
                    }
                }
                stmt.close();
                rs.close();
                return null;
            }

        }catch (SQLException e){e.printStackTrace();}
        return null;
    }
    public void  receiveMediaInPlaylist(String name){
        try {
            if(connection != null&&!connection.isClosed() && isPresent("Name",name,"Playlist")&&isPresent("Name",name,"MyMediaPlaylist")) {
                String query = "select * " +
                        "from MyMedia,MyMediaPlaylist " +
                        "where MyMedia.Path=MyMediaPlaylist.Path and MyMediaPlaylist.Name=?" +
                        ";";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1,name);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    PlaylistCollection.getInstance().getPlayListsCollections().get(PlaylistCollection.getInstance().returnPlaylist(name)).add(
                            (new MyMedia(rs.getString("Title"), rs.getString("Artist"),
                            rs.getString("Album"), rs.getString("Genre"), rs.getString("Path")
                            , rs.getString("Length"), rs.getString("Year"), rs.getString("Image"))));
                }
                stmt.close();
                rs.close();
            }

        }catch (SQLException e){e.printStackTrace();}
    }
    public void receivePlaylist(){
        try {
            if(connection != null&&!connection.isClosed()) {
                String query = "select * from Playlist;";
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String name=rs.getString("Name");
                    PlaylistCollection.getInstance().addPlaylist(new Playlist(name,rs.getString("Image"),rs.getInt("Songs"),rs.getString("TotalDuration")));
                    //receiveMediaInPlaylist(name);
                }
                stmt.close();
                rs.close();
            }

        }catch (SQLException e){e.printStackTrace();}
    }
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
                    String t=rs.getString("Title");
                    Home.getInstance().addToRecentMediaBis(new MyMedia(t, rs.getString("Artist"),
                            rs.getString("Album"), rs.getString("Genre"), rs.getString("Path")
                            , rs.getString("Length"), rs.getString("Year"), rs.getString("Image")));
                }
                stmt.close();
                rs.close();
                return myMedia;
            }

        }catch (SQLException e){e.printStackTrace();}
        return null;
    }
    public void receivePlayqueue(){
        try {
            if(connection != null&&!connection.isClosed()) {
                String query = "select * " +
                        "from MyMedia,Playqueue " +
                        "where MyMedia.Path=Playqueue.Path " +
                        ";";
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                ArrayList<MyMedia> myMedia = new ArrayList<>();
                while (rs.next()) {
                     PlayQueue.getInstance().setList(new MyMedia(rs.getString("Title"), rs.getString("Artist"),
                            rs.getString("Album"), rs.getString("Genre"), rs.getString("Path")
                            , rs.getString("Length"), rs.getString("Year"), rs.getString("Image")));
                }
                stmt.close();
                rs.close();
            }

        }catch (SQLException e){e.printStackTrace();}
        return ;
    }
    /*
    public void receiveApplicationClosureData(){
        try {
            if(connection != null&&!connection.isClosed()) {
                String query = "select * " +
                        "from ApplicationClosureData " +
                        "where Key=key " +
                        ";";
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String path=rs.getString("Path");
                    if(path!=""&&path!=null&&isPresent("Path",path,"MyMedia")) {
                        PlayQueue.getInstance().setCurrentMedia(rs.getInt("Position"));
                        //Player.getInstance().playMedia();
                        //Player.getInstance().pauseMedia();
                        double currentTime=rs.getDouble("CurrentTime");
                        System.out.println(currentTime);
                        Player.getInstance().setCurrentMediaTime(currentTime);
                        Player.getInstance().skipTime();
                    }
                    Player.getInstance().setVolume(rs.getDouble("Volume"));
                    int Equalizer=rs.getInt("TypeEqualizer");
                    if(Equalizer!=-1)
                        AudioEqualizer.getInstance().setCurrentPresetIndex(Equalizer);
                    SceneHandler.getInstance().changeTheme(rs.getString("Theme"));// TODO: 05/07/2022 riselezionare
                }
                stmt.close();
                rs.close();
            }

        }catch (SQLException e){e.printStackTrace();}
        return ;
    }
    public boolean initApplicationClosureData(){
        try {
            if(connection != null && !connection.isClosed()&&!isPresent("Key","key","ApplicationClosureData")) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO ApplicationClosureData VALUES(?,?,?,?,?,?,?);");
                stmt.setString(1,"key" );
                stmt.setString(2,"" );
                stmt.setInt(3,-1);
                stmt.setDouble(4,Player.getInstance().getCurrentMediaTime() );
                stmt.setDouble(5,Player.getInstance().getVolume());
                stmt.setInt(6,AudioEqualizer.getInstance().getCurrentPresetIndex());
                stmt.setString(7,"Dark" );
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e){e.printStackTrace();}
        return false;
    }
    */

    public boolean deleteAll(String tab){
        try {
            if(connection != null&&checkTable(tab)&&!connection.isClosed()){
                PreparedStatement stmt=connection.prepareStatement("DELETE FROM "+tab+";");
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e){e.printStackTrace();}
        return false;
    }
    public boolean deleteAllMedia(String path){
        try {
            if(connection != null&&path!=null && !connection.isClosed()){
                return deleteMedia(path,"MyMedia")&deleteMedia(path,"RecentMedia")&deleteMedia(path,"MyMediaPlaylist");
            }
        }catch (SQLException e){ e.printStackTrace();}
        return false;
    }
    public boolean deleteMedia(String pathMymedia, String tab){
        try {
            if(connection != null&&checkTable(tab)&&pathMymedia!=null && !connection.isClosed()&&isPresent("Path",pathMymedia,tab)){
                deleteMediaPlaylist(pathMymedia,"Path");
                PreparedStatement stmt=connection.prepareStatement("DELETE FROM "+tab+" WHERE Path=?;");
                stmt.setString(1,pathMymedia);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e){ e.printStackTrace();}
        return false;
    }
    public boolean deletePlaylist(String name){
        try {
            if(connection != null&&name!=null && !connection.isClosed()){
                deleteMediaPlaylist(name,"Name");
                PreparedStatement stmt=connection.prepareStatement("DELETE FROM Playlist WHERE Name=?;");
                stmt.setString(1,name);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e){e.printStackTrace();}
        return false;
    }

    public  boolean deleteMediaPlaylist(String element,String Type){
        try {
            if(connection != null&&element!=null && !connection.isClosed()){

                PreparedStatement stmt=connection.prepareStatement("DELETE FROM MyMediaPlaylist WHERE "+Type+"=?;");
                stmt.setString(1,element);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e){e.printStackTrace();}
        return false;
    }

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
                            "Length VARCHAR(10)," +
                            "Year VARCHAR(10)," +
                            "MusicLibrary INTEGER,"+
                            "VideoLibrary INTEGER,"+
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
                            "FOREIGN KEY (Path) REFERENCES MyMedia(Path),"+
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
                    "CREATE TABLE IF NOT EXISTS Playlist(Name VARCHAR(100) NOT NULL," +
                            "Image VARCHAR(255)," +
                            "Songs INT,"+
                            "TotalDuration VARCHAR(10),"+
                            "PRIMARY KEY (Name));";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public void createTablePlayqueue(){
        try {
            String query =
                    "CREATE TABLE IF NOT EXISTS Playqueue(Path VARCHAR(255)," +
                            "FOREIGN KEY (Path) REFERENCES MyMedia(path),"+
                            "PRIMARY KEY (Path));";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    /*
    public void createTableApplicationClosureData(){
        try {
            String query =
                    "CREATE TABLE IF NOT EXISTS ApplicationClosureData(Key VARCHAR(3)," +
                            "Path VARCHAR(255),"+
                            "Position INTEGER,"+
                            "Volume FLOAT(25),"+
                            "CurrentTime FLOAT(25),"+
                            "TypeEqualizer INTEGER,"+
                            "Theme VARCHAR(100),"+
                            "FOREIGN KEY (Path) REFERENCES MyMedia(path),"+
                            "PRIMARY KEY (Key));";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    */

    // Gestione Equalizer
    public void createTableEqualizer(){
        try {
            String query =
                    "CREATE TABLE IF NOT EXISTS Equalizer(" +
                            "Hz32 INTEGER," +
                            "Hz64 INTEGER," +
                            "Hz125 INTEGER," +
                            "Hz250 INTEGER," +
                            "Hz500 INTEGER," +
                            "Hz1000 INTEGER," +
                            "Hz2000 INTEGER," +
                            "Hz4000 INTEGER," +
                            "Hz8000 INTEGER,"+
                            "Hz16000 INTEGER," +
                            "Key VARCHAR(3);" ;
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean initEqualizer(){
        try {
            if(connection != null &&!connection.isClosed()&&isPresent("Key","key","Equalizer")) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO Equalizer VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                stmt.setInt(1,0);
                stmt.setInt(2,0);
                stmt.setInt(3,0);
                stmt.setInt(4,0);
                stmt.setInt(5,0);
                stmt.setInt(6,0);
                stmt.setInt(7,0);
                stmt.setInt(8,0);
                stmt.setInt(9,0);
                stmt.setInt(10,0);
                stmt.setString(11,"key");
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e) {e.printStackTrace();}
        return false;
    }
    public boolean setEqualizer(int []vett){
        try {
            if(connection != null &&!connection.isClosed()) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE Equalizer SET" +
                        "Hz32=?," +
                        "Hz64=?," +
                        "Hz125=?," +
                        "Hz250=?," +
                        "Hz500=?," +
                        "Hz1000=?," +
                        "Hz2000=?," +
                        "Hz4000=?," +
                        "Hz8000=?," +
                        "Hz16000=?  WHERE Key=key ;");
                stmt.setInt(1,vett[0]);
                stmt.setInt(2,vett[1]);
                stmt.setInt(3,vett[2]);
                stmt.setInt(4,vett[3]);
                stmt.setInt(5,vett[4]);
                stmt.setInt(6,vett[5]);
                stmt.setInt(7,vett[6]);
                stmt.setInt(8,vett[7]);
                stmt.setInt(9,vett[8]);
                stmt.setInt(10,vett[9]);
                stmt.execute();
                stmt.close();
                return true;
            }
        }catch (SQLException e) {e.printStackTrace();}
        return false;
    }
    public int[] getEqualizer(){
        try {
            String query = "select * from Equalizer where Key=key;";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            int []settings=new int[10];
            if(rs.next()){
                settings[0]=rs.getInt("Hz32");
                settings[1]=rs.getInt("Hz64");
                settings[2]=rs.getInt("Hz125");
                settings[3]=rs.getInt("Hz250");
                settings[4]=rs.getInt("Hz500");
                settings[5]=rs.getInt("Hz1000");
                settings[6]=rs.getInt("Hz2000");
                settings[7]=rs.getInt("Hz4000");
                settings[8]=rs.getInt("Hz8000");
                settings[9]=rs.getInt("Hz16000");
            }
            return settings;
        }catch (SQLException e){e.printStackTrace();}
        return null;
    }


    //END FUNCTION
}


