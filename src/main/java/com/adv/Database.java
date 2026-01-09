package com.adv;

import com.zaxxer.hikari.*;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    
    private static final Properties properties = new Properties();
    private static HikariDataSource dataSource;
    
    static {
        try (InputStream input = Database.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Error: unable to find config.properties. Make sure it's in the resources folder.");
            }
            properties.load(input);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.user"));
            config.setPassword(properties.getProperty("db.password"));

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(1000);
            config.setConnectionTimeout(3000);

            dataSource = new HikariDataSource(config);
            System.out.println("Database connection Pool started.");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection connect() throws SQLException {
//        try{
//            System.out.println("Connecting to the database...");
//            Connection connection = DriverManager.getConnection(
//                    properties.getProperty("db.url"),
//                    properties.getProperty("db.user"),
//                    properties.getProperty("db.password")
//            );
//
//            System.out.println("Connection successful!");
//            return connection;
//
//        } catch (SQLException e) {
//            System.err.println("Connection failed!" + e);
//            throw e;
//        }
        return dataSource.getConnection();
    }

}
