package it.unical.sadstudents.mediaplayeruid.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection con = null;

    public void createConnection() throws SQLException {
        String url = "jdbc:sqlite:test2.db";
        con = DriverManager.getConnection(url);
        if (con != null && !con.isClosed())
            System.out.println("Connected!");
    }

    public void closeConnection() throws SQLException {
        if(con != null)
            con.close();
        con = null;
    }
}
