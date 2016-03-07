package connector;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnector {
    private static DatabaseConnector instance;
    private static Connection connection;

    private static void initializeConnection() throws Exception {
        // // TODO: 06.03.2016 Вынести данные в конфигурационный файл 
        String strURL = "jdbc:firebirdsql:localhost/3050:D:\\ClientServerProject\\Database\\LIBRARYDB.FDB ?charset=\"WIN1251\"";
        String strUser="SYSDBA";
        String strPassword = "masterkey";
        Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();

        Properties paramConnection = new Properties();
        paramConnection.setProperty("user", strUser);
        paramConnection.setProperty("password", strPassword);
        paramConnection.setProperty("encoding", "WIN1251");

        connection = DriverManager.getConnection(strURL, paramConnection);
    }

    public synchronized static Connection getInstance() throws Exception {
        if (connection == null || connection.isClosed()) {
            initializeConnection();
        }
        return connection;
    }


}
