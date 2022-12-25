package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
            if(conn!=null)
            {
                System.out.println("\nConnected to Database..");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return conn;
    }
}
