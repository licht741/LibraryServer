import com.sun.deploy.util.StringUtils;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
public class DatabaseConnector {
    private static DatabaseConnector instance;
    private static Connection connection;

    private static void initializeConnection() throws Exception {
        // // TODO: 06.03.2016 Вынести данные в конфигурационный файл 
        String strURL = "jdbc:firebirdsql:localhost/3050:D:\\ClientServerProject\\Database\\LIBRARYDB.FDB ?charset=\"UTF-8\"";
        String strUser="SYSDBA";
        String strPassword = "masterkey";
        Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();

        connection = DriverManager.getConnection(strURL, strUser, strPassword);
    }

    public synchronized static Connection getInstance() throws Exception {
        if (connection == null || connection.isClosed()) {
            initializeConnection();
        }
        return connection;
    }


}
