package dbwrappers;

import connector.DatabaseConnector;
import types.*;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LibrarianWrapper {



    public AuthWrap libAuthorization(String login, String passwd) {
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
            return new AuthWrap(outRes, outID, outUserName);
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public ArrayList<Book> getAllBooks() {
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
                Book book = new Book(); //TODO: Вынести в фабричный метод
                book.setId(id);
                book.setTitle(title);
                book.setAuthor(author);
                book.setPublishHouse(publishHouse);
                book.setYear(y);
                arrayList.add(book);
            }
            resSet.close();
            statement.close();
            return arrayList;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public HashMapWrapper getBooksInLibrary() {
        try {
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
                int count = resSet.getInt("bookcount");
                Book book = new Book(id, title, author, publishHouse, y);
                books.put(book, count);

            }
            resSet.close();
            cStatement.close();
            return new HashMapWrapper(books);
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }

    }

    public int purchaseBook(int bookId, int shopId, int bookCount, Date purDate)  {
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement cStatement = connection.prepareCall("{call receipt_books(?, ?, ?, ?)}");
            cStatement.setInt(1, bookId);
            cStatement.setInt(2, shopId);
            cStatement.setInt(3, bookCount);
            cStatement.setDate(4, new java.sql.Date(purDate.getTime()));
            int result = cStatement.executeUpdate();
            return result;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return -1;
        }
    }

    public ArrayList<User> getUsersList() {
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
                User user = new User();

                user.setId(id);
                user.setLogin(login);
                user.setName(name);
                user.setEnable(isEnable);

                arrayList.add(user);
            }
            resSet.close();
            statement.close();

            return arrayList;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public int addNewBook(String title, String author,
                                          String publishHouse, int pubYear) {
        try {
            Connection connection = DatabaseConnector.getInstance();
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
        catch (Exception exc) {
            exc.printStackTrace();
            return -1;
        }
    }

    public ArrayList<UserOrder> getUserOrders() {
        try {
            ArrayList<UserOrder> userOrderArrayList = new ArrayList<UserOrder>();
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement statement = connection.prepareCall("{call GET_USERS_ORDER}");
            ResultSet resSet = statement.executeQuery();
            while (resSet.next()) {
                int id = resSet.getInt("id");
                String title = resSet.getString("title");
                String author = resSet.getString("author");
                String publishHouse = resSet.getString("publishhouse");
                int y = resSet.getInt("pub_year");
                int bCount = resSet.getInt("ord_count");
                Book book = new Book(); //TODO: Вынести в фабричный метод
                book.setId(id);
                book.setTitle(title);
                book.setAuthor(author);
                book.setPublishHouse(publishHouse);
                book.setYear(y);
                UserOrder userOrder = new UserOrder(); //(book, bCount);
                userOrder.setBook(book);
                userOrder.setCount(bCount);
                userOrderArrayList.add(userOrder);
            }
            return userOrderArrayList;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }

    }

    public ArrayList<PurchaseOrder> getPurchaseOrder() {
        try {
            ArrayList<PurchaseOrder> purchaseOrders = new ArrayList<PurchaseOrder>();
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement statement = connection.prepareCall("{call GET_PURCHASE_ORDERS}");
            ResultSet resSet = statement.executeQuery();
            while (resSet.next()) {
                int id = resSet.getInt("book_id");
                String title = resSet.getString("title");
                String author = resSet.getString("author");
                String publishHouse = resSet.getString("publishhouse");
                int y = resSet.getInt("pubyear");
                int bCount = resSet.getInt("book_count");
                Book book = new Book(); //TODO: Вынести в фабричный метод
                book.setId(id);
                book.setTitle(title);
                book.setAuthor(author);
                book.setPublishHouse(publishHouse);
                book.setYear(y);
                String shop = resSet.getString("shop");
                Date date = resSet.getDate("pur_date");

                PurchaseOrder purchaseOrder = new PurchaseOrder(); //(book, shop, bCount, date);

                purchaseOrder.setBook(book);
                purchaseOrder.setShop(shop);
                purchaseOrder.setCount(bCount);
                purchaseOrder.setDate(date);

                purchaseOrders.add(purchaseOrder);
            }
            return purchaseOrders;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public ArrayList<Operation> getBookOperations() {
        try {
            ArrayList<Operation> operations = new ArrayList<Operation>();
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement statement = connection.prepareCall("{call GET_BOOK_OPERATIONS}");
            ResultSet resSet = statement.executeQuery();
            while (resSet.next()) {
                String userName  = resSet.getString("user_name");
                String bookTitle = resSet.getString("book_title");
                String bookAuthor = resSet.getString("book_author");
                String pubHouse = resSet.getString("pub_house");
                int pubYear = resSet.getInt("pub_year");
                Date recDate = resSet.getDate("received_date");
                Date deadline = resSet.getDate("deadline");

                //bookTitle, bookAuthor, pubHouse, pubYear
                Book book = new Book();
                book.setTitle(bookTitle);
                book.setAuthor(bookAuthor);
                book.setPublishHouse(pubHouse);
                book.setYear(pubYear);

                //Operation operation = new Operation(userName, book, recDate, deadline);
                Operation operation = new Operation();

                operation.setBook(book);
                operation.setUser(userName);
                operation.setReceivedDate(recDate);
                operation.setDeadline(deadline);

                operations.add(operation);
            }
            return operations;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

}
