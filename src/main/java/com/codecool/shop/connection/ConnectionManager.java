package com.codecool.shop.connection;

import com.codecool.shop.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static String url = null;
    private static String user = null;
    private static String password = null;

    private static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);



    static {

        try {
            FileReader fr = new FileReader("src/main/resources/connection.txt");
            BufferedReader br = new BufferedReader(fr);
            String[] content = br.readLine().split(" ");
            url = "jdbc:postgresql://" + content[1];
            content = br.readLine().split(" ");
            url += "/" + content[1];
            content = br.readLine().split(" ");
            user = content[1];
            content = br.readLine().split(" ");
            password = content[1];

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error reading connection file", e);
        }
    }


    public static Connection getConnection() {
        Connection con = null;
            try {
                con = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                logger.error("Failed to create database connection", ex);
            }         // log an exception. for example:
        return con;
    }
}
