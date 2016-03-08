package dbwrappers;

import connector.DatabaseConnector;
import exceptions.DBConnectException;
import exceptions.SomeDBException;
import types.*;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LibrarianWrapper {

    public AuthWrap libAuthorization(String login, String passwd) throws SomeDBException, DBConnectException {
        try {
            Connection connection = DatabaseConnector.getInstance();
            Statement statement = connection.createStatement();
            CallableStatement callableStatement = connection.prepareCall("{call AUTHORIZATION(?, ?, ?, ?, ?, ?)}");
            callableStatement.setString(1, login);
            callableStatement.setString(2, passwd);
            callableStatement.setString(3, "L");
            callableStatement.registerOutParameter(4, Types.INTEGER);
            callableStatement.registerOutParameter(5, Types.INTEGER);
            callableStatement.registerOutParameter(6, Types.CHAR);
            callableStatement.executeQuery();

            int outRes  = callableStatement.getInt(4);
            int outID = callableStatement.getInt(5);
            String outUserName = callableStatement.getString(6);
            return new AuthWrap(outID, outID, outUserName);
        }
        catch (SQLException exc) {
            throw new SomeDBException(exc.getMessage());
        }
    }

    public ArrayList<Book> getAllBooks() throws SomeDBException, DBConnectException {
        try {
            Connection connection = DatabaseConnector.getInstance();
            Statement statement = connection.createStatement();
            String SQLQuery = "select * from bookslist";
            ResultSet resSet = statement.executeQuery(SQLQuery);

            ArrayList<Book> arrayList = new ArrayList<Book>();
            while (resSet.next()) {
                int id = resSet.getInt("id");
                String title = resSet.getString("title");
                String author = resSet.getString("author");
                String publishHouse = resSet.getString("publishhouse");
                int y = resSet.getInt("year");
                Year year = Year.of(y);
                Book book = new Book(id, title, author, publishHouse, year);
                arrayList.add(book);
            }
            resSet.close();
            statement.close();
            return arrayList;
        }
        catch (SQLException exc) {
            throw new SomeDBException(exc.getMessage());
        }
    }

    public HashMapWrapper getBooksInLibrary() throws SomeDBException, DBConnectException {
        Connection connection = DatabaseConnector.getInstance();
        try {
            CallableStatement cStatement = connection.prepareCall("{call get_books_in_library()}");
            ResultSet resSet = cStatement.executeQuery();

            HashMap<Book, Integer> books = new HashMap<Book, Integer>();

            while (resSet.next()) {
                int id = resSet.getInt("id");
                String title = resSet.getString("title");
                String author = resSet.getString("author");
                String publishHouse = resSet.getString("publishhouse");
                int y = resSet.getInt("pubyear");
                Year year = Year.of(y);
                int count = resSet.getInt("bookcount");
                Book book = new Book(id, title, author, publishHouse, year);
                books.put(book, count);

            }
            resSet.close();
            cStatement.close();
            return new HashMapWrapper(books);
        }
        catch (SQLException exc) {
            throw new SomeDBException(exc.getMessage());
        }

    }

    public int purchaseBook(int bookId, int shopId,
                                            int bookCount, Date purDate)
            throws SomeDBException, DBConnectException {
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement cStatement = connection.prepareCall("{call receipt_books(?, ?, ?, ?)}");
            cStatement.setInt(1, bookId);
            cStatement.setInt(2, shopId);
            cStatement.setInt(3, bookCount);
            cStatement.setDate(4, new java.sql.Date(purDate.getTime()));
            int result = cStatement.executeUpdate();
            cStatement.close();
            return result;
        }
        catch (SQLException exc) {
            throw new SomeDBException(exc.getMessage());
        }
    }

    public ArrayList<User> getUsersList() throws SomeDBException, DBConnectException {
        try {
            Connection connection = DatabaseConnector.getInstance();
            Statement statement = connection.createStatement();
            String SQLQuery = "select id, login, name, enable from users where type = 'U'";
            ResultSet resSet = statement.executeQuery(SQLQuery);

            ArrayList<User> arrayList = new ArrayList<User>();
            while (resSet.next()) {
                int id = resSet.getInt("id");
                String login = resSet.getString("login");
                String name = resSet.getString("name");
                boolean isEnable = resSet.getString("Enable").equals("T");
                User user = new User(id, login, name, isEnable);
                arrayList.add(user);
            }
            resSet.close();
            statement.close();

            return arrayList;
        }
        catch (SQLException exc) {
            throw new SomeDBException(exc.getMessage());
        }
    }

    public int addNewBook(String title, String author,
                                          String publishHouse, int pubYear) throws SomeDBException, DBConnectException {
        Connection connection = DatabaseConnector.getInstance();
        try {


            String insStatement = String.format("insert into bookslist(title, author, " +
                    "publishhouse, \"YEAR\") values('%s', '%s', '%s', %s)", title, author, publishHouse, pubYear);
            Statement cStatement = connection.createStatement();

            int success = cStatement.executeUpdate(insStatement);
            if (success == 0)
                return 0;
            cStatement.close();
            String SQLStatement = String.format("select id from bookslist where " +
                    "title = '%s' and author = '%s' and " +
                    "publishhouse = '%s' and \"YEAR\" = %s", title, author, publishHouse, pubYear);

            Statement statement = connection.createStatement();
            ResultSet rSet = statement.executeQuery(SQLStatement);

            int id = -1;
            if (rSet.next())
                id = rSet.getInt("id");
            rSet.close();
            return id;
        }
        catch (SQLException exc) {
            throw new SomeDBException(exc.getMessage());
        }
    }

}
