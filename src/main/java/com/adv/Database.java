package com.adv;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    
    private static final Properties properties = new Properties();
    
    static {
        try (InputStream input = Database.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Errror: unable to find config.properties. Make sure it's in the resources folder.");
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection connect() throws SQLException {
        try{
            System.out.println("Connecting to the database...");
            Connection connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
            );
                    
            System.out.println("Connection successful!");
            return connection;

        } catch (SQLException e) {
            System.err.println("Connection failed!");
            throw e;
        }
    }

}
