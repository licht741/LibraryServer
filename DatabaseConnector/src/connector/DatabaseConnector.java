package connector;

import exceptions.DBConnectException;
import exceptions.SomeDBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/*
 * Класс, реализующий логику работы подключения к БД
 * Реализуется через паттерн Singleton
 */
public class DatabaseConnector {
    private static Connection connection;

    private static void initializeConnection() throws DBConnectException, SomeDBException {
        try {
            Properties paramConnection = getConnectionParam("database.prop");
            Class.forName(paramConnection.getProperty("driver")).newInstance();
            connection = DriverManager.getConnection(paramConnection.getProperty("url"), paramConnection);
        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new SomeDBException(exc.getMessage());
        }
        catch (IOException exc) {
            exc.printStackTrace();
            throw new DBConnectException(exc.getMessage());
        }
        catch (Exception exc) {
            exc.printStackTrace();
            throw new DBConnectException(exc.getMessage());
        }
    }

    public synchronized static Connection getInstance() throws DBConnectException, SomeDBException, IOException {
        try {
            if (connection == null || connection.isClosed())
                initializeConnection();
            return connection;
        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new SomeDBException(exc.getMessage());
        }

    }

    private static Properties getConnectionParam(String configurationFile) throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(configurationFile);
        properties.load(fileInputStream);
        fileInputStream.close();
        return properties;
    }
}

