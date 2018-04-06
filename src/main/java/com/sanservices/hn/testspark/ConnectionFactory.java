package com.sanservices.hn.testspark;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 *
 * @author afrech
 */
public class ConnectionFactory {

    private static final Properties CONNECTIONPROPERTIES;

    static {
        try (InputStream stream = ConnectionFactory.class.getClassLoader().getResourceAsStream(Application.environment+"/connection.properties")) {
            CONNECTIONPROPERTIES = new Properties();
            CONNECTIONPROPERTIES.load(stream);
        } catch (IOException ex) {
            throw new RuntimeException("Unable to open connection.properties.", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = CONNECTIONPROPERTIES.getProperty("url");
        String user = CONNECTIONPROPERTIES.getProperty("user");
        String password = CONNECTIONPROPERTIES.getProperty("password");
        return DriverManager.getConnection(url, user, password);
    }
}
