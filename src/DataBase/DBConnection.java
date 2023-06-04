package DataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;


    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    private Connection connection;

    private DBConnection() {

    }


    public void initialize(String url  ,String user , String password) throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
        connection = DriverManager.getConnection(url,user,password);
    }

    public void initializeFromFile(String fileName) throws Exception {
        Properties prop = new Properties();
        // We get the current directory of the class. Not that the path is without package name
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        // load a properties file
        prop.load(new FileInputStream(path + "/" + fileName));
        String driver = prop.getProperty("jdbc.driver");
        String url = prop.getProperty("jdbc.url");
        String username = prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");
        // open the connection
        this.connection = DriverManager.getConnection(url, username, password);
    }


    public Connection getConnection() {
        return connection;
    }
}
