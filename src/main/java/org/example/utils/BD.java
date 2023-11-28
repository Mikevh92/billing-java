package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BD {
    private static final String url = "jdbc:sqlite:./fact.db";
    private static Connection conn;
    public static Connection getInstance(){
        if(conn == null){
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return conn;
    }
}
