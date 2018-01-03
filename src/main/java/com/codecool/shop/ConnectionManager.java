package com.codecool.shop;

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
        }
    }


    public static Connection getConnection() {
        Connection con = null;
            try {
                con = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.txt.");
            }         // log an exception. for example:
        return con;
    }
}
