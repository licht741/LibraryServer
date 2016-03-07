package dbwrappers;

import connector.DatabaseConnector;
import types.Book;
import types.ResultWrap;
import types.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


// -1 - проблемы c подключением
// -2 - проблемы с авторизацией
// 0  - корректный результат
public class LibrarianWrapper {
    private ArrayList<Integer> authorizedLibrarians;

    public LibrarianWrapper() {
        authorizedLibrarians = new ArrayList<Integer>();
    }

    public ResultWrap<ArrayList<Book>> getAllBooks() throws Exception {
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

        return new ResultWrap<ArrayList<Book>>(0, arrayList);
    }

    public ResultWrap<HashMap<Book, Integer>> getBooksInLibrary() throws Exception {
        Connection connection = DatabaseConnector.getInstance();
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
        return new ResultWrap<HashMap<Book, Integer>>(0, books);
    }

    public ResultWrap<Integer> purchaseBook(int bookId, int shopId, int bookCount, Date purDate) throws Exception {
        Connection connection = DatabaseConnector.getInstance();
        CallableStatement cStatement = connection.prepareCall("{call receipt_books(?, ?, ?, ?)}");
        cStatement.setInt(1, bookId);
        cStatement.setInt(2, shopId);
        cStatement.setInt(3, bookCount);
        cStatement.setDate(4, new java.sql.Date(purDate.getTime()));
        cStatement.executeUpdate();
        cStatement.close();
        return new ResultWrap<Integer>(0, 0);
    }

    public ResultWrap<ArrayList<User>> getUsersList() throws Exception {
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

        return new ResultWrap<ArrayList<User>>(0, arrayList);
    }

    public ResultWrap<Integer> addNewBook(String title, String author, String publishHouse, int pubYear) throws Exception {
        Connection connection = DatabaseConnector.getInstance();

        String insStatement = String.format("insert into bookslist(title, author, " +
                "publishhouse, \"YEAR\") values('%s', '%s', '%s', %s)", title, author, publishHouse, pubYear);
        Statement cStatement = connection.createStatement();

        int success = cStatement.executeUpdate(insStatement);
        if (success == 0)
            return new ResultWrap<Integer>(-1, -1);
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
        return new ResultWrap<Integer>(0,id);

    }

}
