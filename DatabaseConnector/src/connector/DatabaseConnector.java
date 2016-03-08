package connector;

import exceptions.DBConnectException;
import exceptions.SomeDBException;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnector {
    private static DatabaseConnector instance;
    private static Connection connection;

    private static void initializeConnection() throws DBConnectException, SomeDBException {
        // // TODO: 06.03.2016 Вынести данные в конфигурационный файл 
        String strURL = "jdbc:firebirdsql:localhost/3050:D:\\ClientServerProject\\Database\\LIBRARYDB.FDB ?charset=\"WIN1251\"";
        String strUser="SYSDBA";
        String strPassword = "masterkey";
        Properties paramConnection = new Properties();
        paramConnection.setProperty("user", strUser);
        paramConnection.setProperty("password", strPassword);
        paramConnection.setProperty("encoding", "WIN1251");

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
            connection = DriverManager.getConnection(strURL, paramConnection);
        }
        catch (SQLException exc) {
            throw new SomeDBException(exc.getMessage());
        }
        catch (Exception exc) {
            throw new DBConnectException(exc.getMessage());
        }
    }

    public synchronized static Connection getInstance() throws DBConnectException, SomeDBException {
        try {
            if (connection == null || connection.isClosed())
                initializeConnection();
            return connection;
        }
        catch (SQLException exc) {
            throw new SomeDBException(exc.getMessage());
        }

    }


}
