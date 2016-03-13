package dbwrappers;

import connector.DatabaseConnector;
import creators.ObjectCreator;
import types.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/*
 * Класс, реализующий логику работы с базой данных для библиотекаря
 */
public class LibrarianWrapper {

    /*
     * Функция авторизации библиотекаря
     * Возвращает обёртку, содержащую код ошибки, имя пользователя и его идентификатор
     */
    public AuthWrap libAuthorization(String login, String passwd) {
        try {
            Connection connection = DatabaseConnector.getInstance();
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
            AuthWrap authWrap = new AuthWrap();
            authWrap.setResult(outRes);
            authWrap.setUserID(outID);
            authWrap.setUserName(outUserName);

            return authWrap;
        }

        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public ArrayList<Operation> findDebtors() {
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement callableStatement = connection.prepareCall("{call FIND_DEBTORS(?, ?, ?, ?, ?, ?, ?, ?)}");

            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.registerOutParameter(2, Types.CHAR);
            callableStatement.registerOutParameter(3, Types.CHAR);
            callableStatement.registerOutParameter(4, Types.CHAR);
            callableStatement.registerOutParameter(5, Types.CHAR);
            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.registerOutParameter(7, Types.DATE);
            callableStatement.registerOutParameter(8, Types.DATE);

            ResultSet resultSet = callableStatement.executeQuery();
            ArrayList<Operation> operations = new ArrayList<Operation>();

            while (resultSet.next()) {
                int operationID = resultSet.getInt(1);
                String debtorName = resultSet.getString(2);
                String bookTitle = resultSet.getString(3);
                String bookAuthor = resultSet.getString(4);
                String bookPubhouse = resultSet.getString(5);
                int pubYear = resultSet.getInt(6);
                Date recDate = resultSet.getDate(7);
                Date deadline = resultSet.getDate(8);

                Book book = ObjectCreator.createBook(bookTitle, bookAuthor, bookPubhouse, pubYear);
                Operation operation = ObjectCreator.createOperation(operationID, debtorName, book, recDate, deadline);
                operations.add(operation);
            }
            resultSet.close();
            return operations;
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
                Book book = ObjectCreator.createBook(id, title, author, publishHouse, y);
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
                Book book = ObjectCreator.createBook(id, title, author, publishHouse, y);
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

   /*
    * Добавление информации о приобретении книг
    * Возвращаемый результат:
    *       0 - в случае успешного добавления
    *      -1 - в случае возникновения ошибки
    */
    public int purchaseBook(int bookId, int shopId, int bookCount, Date purDate)  {
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement cStatement = connection.prepareCall("{call receipt_books(?, ?, ?, ?)}");
            cStatement.setInt(1, bookId);
            cStatement.setInt(2, shopId);
            cStatement.setInt(3, bookCount);
            cStatement.setDate(4, new java.sql.Date(purDate.getTime()));
            return cStatement.executeUpdate();

        }
        catch (Exception exc) {
            exc.printStackTrace();
            return -1;
        }
    }

    /*
     * Возвращает список зарегистрированных читателей
     */
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
                User user = ObjectCreator.createUser(id, login, name, isEnable);
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

    /*
     * Добавление нового наименования в базу
     * Возвращает:
     *      -1 - в случае ошибки добавление
     *      id - id новой книги в случае успеха
     */
    public int addNewBook(String title, String author,
                                          String publishHouse, int pubYear) {
        try {
            Connection connection = DatabaseConnector.getInstance();
            String insStatement = String.format("insert into bookslist(title, author, " +
                    "publishhouse, \"YEAR\") values('%s', '%s', '%s', %s)",
                    title, author, publishHouse, pubYear);
            Statement cStatement = connection.createStatement();

            int success = cStatement.executeUpdate(insStatement);
            if (success == 0)
                return 0;
            cStatement.close();
            String SQLStatement = String.format("select id from bookslist where " +
                    "title = '%s' and author = '%s' and " +
                    "publishhouse = '%s' and \"YEAR\" = %s",
                    title, author, publishHouse, pubYear);

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
                Book book = ObjectCreator.createBook(id, title, author, publishHouse, y);
                UserOrder userOrder = ObjectCreator.createUserOrder(book, bCount);
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

                Book book = ObjectCreator.createBook(id, title, author, publishHouse, y);
                String shop = resSet.getString("shop");
                Date date = resSet.getDate("pur_date");

                PurchaseOrder purchaseOrder = ObjectCreator.createPurchaseOrder(book, shop, bCount, date);
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

                Book book = ObjectCreator.createBook(bookTitle, bookAuthor, pubHouse, pubYear);
                Operation operation = ObjectCreator.createOperation(userName, book, recDate, deadline);
                operations.add(operation);
            }
            return operations;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    /*
     *
     * Блокирует пользователя с заданным userID
     * Возвращает:
     *      -2 - В случае системной ошибки
     *      -1 - Если пользователь не найден или у него нет долгов
     *       0 - В случае успешной блокировки
     *
     */
    public int lockDebtor(int userID) {
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement callableStatement = connection.prepareCall("{call LOCK_DEBTORS(?, ?)}");
            callableStatement.setInt(1, userID);
            callableStatement.registerOutParameter(2, Types.INTEGER);

            callableStatement.execute();
            int retResult = callableStatement.getInt(2);
            callableStatement.close();
            return retResult;

        }
        catch (Exception exc) {
            exc.printStackTrace();
            return -2;
        }
    }

    /*
 *
 * Разблокирует пользователя с заданным userID
 * Возвращает:
 *      -2 - В случае системной ошибки
 *      -1 - Если пользователь не найден
 *       0 - В случае успешной блокировки
 *
 */
    public int unlockDebtor(int userID) {
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement callableStatement = connection.prepareCall("{call UNLOCK_DEBTORS(?, ?)}");
            callableStatement.setInt(1, userID);
            callableStatement.registerOutParameter(2, Types.INTEGER);

            callableStatement.execute();
            int retResult = callableStatement.getInt(2);
            callableStatement.close();
            return retResult;

        }
        catch (Exception exc) {
            exc.printStackTrace();
            return -2;
        }
    }
}
