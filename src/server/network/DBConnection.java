package server.network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private String url;
    private String user;
    private String pwd;
    private static final int URL_INDEX = 1;
    private static final int USER_INDEX = 1;
    private static final int PWD_INDEX = 1;

    public Connection connect() throws IOException, ClassNotFoundException, SQLException {
        Class.forName(System.getenv("DriverDB"));
        BufferedReader reader = new BufferedReader(new FileReader(System.getenv("Credentials")));
        url = reader.readLine().trim().strip().split("=")[URL_INDEX];
        user = reader.readLine().trim().strip().split("=")[USER_INDEX];
        pwd = reader.readLine().trim().strip().split("=")[PWD_INDEX];
        return DriverManager.getConnection(url, user, pwd);
    }
}
