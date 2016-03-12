package dbwrappers;

import connector.DatabaseConnector;
import creators.ObjectCreator;
import types.Book;
import types.Operation;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReaderWrapper {

    /*
     * Метод возвращает книги, имеющиеся в наличии
     */
    public ArrayList<Book> getAvailableBooks() {
        try {
            ArrayList<Book> arrayList = new ArrayList<Book>();
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement statement = connection.prepareCall("{call GET_AVAILABLE_BOOKS}");
            ResultSet resSet = statement.executeQuery();
            while (resSet.next()) {
                int id = resSet.getInt("book_id");
                String title = resSet.getString("book_title");
                String author = resSet.getString("book_author");
                String publishHouse = resSet.getString("book_pubhouse");
                int y = resSet.getInt("book_year");
                Book book = ObjectCreator.createBook(id, title, author, publishHouse, y);
                arrayList.add(book);
            }
            return arrayList;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    /*
     * Метод возвращает список книг, не возвращённых читателем в библиотеку
     */
    public ArrayList<Operation> getUserBooks(int userID) {
        try {
            ArrayList<Operation> userOperations = new ArrayList<Operation>();
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement statement = connection.prepareCall("{call GET_USERS_BOOKS(?)}");
            statement.setInt(1, userID);
            ResultSet resSet = statement.executeQuery();
            while (resSet.next()) {
                int operationID = resSet.getInt("operation_id");
                String title = resSet.getString("book_title");
                String author = resSet.getString("book_author");
                String pubHouse = resSet.getString("book_pubhouse");
                int pubYear = resSet.getInt("pub_year");
                Date recDate = resSet.getDate("rec_date");
                Date deadline = resSet.getDate("deadline");

                Book book = ObjectCreator.createBook(title, author, pubHouse, pubYear);
                Operation operation = ObjectCreator.createOperation(operationID, "", book, recDate, deadline);
                userOperations.add(operation);
            }
            return userOperations;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    /*
     * Метод добавляет информацию о получении пользователем библиотечной книги
     * Возвращаемый результат:
     *     -1 - Пользователь с данным id не найден
     *     -2 - Доступ пользователя заблокирован
     *     -3 - Книга с данным id отсутствует в базе
     *     -4 - Книга есть в базе, но отсутствует в наличии
     *     -5 - Пользователь уже взял (и не вернул) такую книгу
     *     -6 - Системная ошибка (например, ошибка в подключении)
     *     id (>0) - Книга успешно взята, возвращает id записи
     */
    public int takeBookFromLibrary(int userID, int bookID) {

        java.util.Date receivedDate = new Date();
        java.sql.Date SQLrecDate = new java.sql.Date(receivedDate.getTime());

        Calendar cal = Calendar.getInstance();
        cal.setTime(receivedDate);
        cal.add(Calendar.DATE, 14);
        Date deadline = cal.getTime();
        java.sql.Date SQLdeadline = new java.sql.Date(deadline.getTime());

        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement callableStatement = connection.prepareCall("{call take_book(?, ?, ?, ?, ?)}");
            callableStatement.setInt(1, bookID);
            callableStatement.setInt(2, userID);
            callableStatement.setDate(3, SQLrecDate);
            callableStatement.setDate(4, SQLdeadline);
            callableStatement.registerOutParameter(5, Types.INTEGER);
            callableStatement.execute();

            int result = callableStatement.getInt(5);
            callableStatement.close();

            return result;

        }
        catch (Exception exc) {
            exc.printStackTrace();
            return -6;
        }
    }

    /*
     * Возвращает книгу, взятую в указанной записи
     * Возвращаемый результат:
     *      -2 - Системная ошибка
     *      -1 - Такой записи не существует или уже была закрыта
     *       0 - Книга успешно возвращена
     */
    public int returnBook(int operationID) {
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement callableStatement = connection.prepareCall("{call return_book(?, ?)}");
            callableStatement.setInt(1, operationID);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.execute();
            int result = callableStatement.getInt(2);
            callableStatement.close();
            return result;

        }
        catch (Exception exc) {
            exc.printStackTrace();
            return -2;
        }
    }

    /*
     * Пользователь делает заказ на книгу в указанном количестве
     * Возвращаемый результат:
     *      -2 - Системная ошибка
     *      -1 - Такой книги нет в базе
     *      0  - Заказ успешно размещён
     */
    public int makeOrder(int bookID, int bookCount) {
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement callableStatement = connection.prepareCall("{call make_order(?, ?, ?)}");
            callableStatement.setInt(1, bookID);
            callableStatement.setInt(2, bookCount);
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.execute();
            int retResult = callableStatement.getInt(3);
            callableStatement.close();
            return retResult;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return -2;
        }
    }

    /*
     * Читатель продляет время на возврат книги
     * Возвращаемый результат:
     *      -2 - Системная ошибка
     *      -1 - Записи не найдено, или на данную книгу есть заказ
     *       0 - Время успешно продлено
     */
    public int extendUsage(int recordID) {
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement callableStatement = connection.prepareCall("{call extend_usage(?, ?)}");
            callableStatement.setInt(1, recordID);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.execute();
            int result = callableStatement.getInt(2);
            callableStatement.close();
            return result;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return -2;
        }
    }

    /*
     * Метод возвращает все книги, информация о которых есть в базе
     */
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

    /*
     * Функция, реализующая читательскую авторизация
     * Возвращаемые результаты:
     *      -1 - Пользователь с такими данными не найден
     *      -2 - Авторизация проведена успешно, однако доступ пользователя заблокирован
     *      -3 - Возникла системная ошибка
     *       0 - Авторизация проведена успешно
     */
    public int authorization(String login, String password) {
        String userType = "U";
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement statement = connection.prepareCall("{call authorization(?, ?, ?, ?)}");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, userType);
            statement.registerOutParameter(4, Types.INTEGER);
            statement.execute();
            int result = statement.getInt(4);
            statement.close();;
            return result;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return -3;
        }
    }

    /*
     * Функция, реализующая регистрацию нового читателя
     * Возвращаемые результаты
     *      -1 - Пользователь с таким логином уже существует
     *      -2 - Некорректные входные данные
     *      -3 - Возникла системная ошибка
     *       0 - Регистрация проведена успешно.
     */

    public int registration(String login, String name, String password) {
        String userType = "U";
        try {
            Connection connection = DatabaseConnector.getInstance();
            CallableStatement statement = connection.prepareCall("{call librarian_registration(?, ?, ?, ?, ?)}");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.setString(4, userType);
            statement.registerOutParameter(5, Types.INTEGER);
            statement.execute();
            int result = statement.getInt(5);
            statement.close();;
            return result;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return -3;
        }
    }

}
