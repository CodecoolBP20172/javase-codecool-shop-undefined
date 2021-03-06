package com.codecool.shop.connection;

import com.codecool.shop.exception.DaoConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** ConnectionManager class which handles connection with the database
 * @author      Anikó Barát
 * @version     1.0
 * @since       1.0
 */

public class ConnectionManager {

    private static String url = null;
    private static String user = null;
    private static String password = null;

    private static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);


    /**
     * Static constructor which reads connection data from a file
     * @throws IOException
     */
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

    /**
     * Method through we can get a connection object to make connection with the database
     * @return Connection instance
     */
    public static Connection getConnection() throws DaoConnectionException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new DaoConnectionException(ex.getMessage());
        }
    }
}
