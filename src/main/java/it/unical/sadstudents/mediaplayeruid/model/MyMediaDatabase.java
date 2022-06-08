package it.unical.sadstudents.mediaplayeruid.model;

import java.util.HashMap;

public class MyMediaDatabase {

    private HashMap<Integer,MyMedia> myDataBase = new HashMap<Integer, MyMedia>();

    private static MyMediaDatabase instance =null;
    private MyMediaDatabase(){

    }

    public static MyMediaDatabase getInstance(){
        if(instance == null)
            instance = new MyMediaDatabase();
        return instance;
    }

    public HashMap<Integer, MyMedia> getMyDataBase() {
        return myDataBase;
    }

    public void setMyDataBase(HashMap<Integer, MyMedia> myDataBase) {
        this.myDataBase = myDataBase;
    }

    public void addToDatabase(MyMedia myMedia){
        Integer key = myMedia.hashCode();
        myDataBase.put(key,myMedia);
    }


}
