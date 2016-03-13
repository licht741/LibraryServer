package endpoint;

import dbwrappers.LibrarianWrapper;
import exceptions.DBConnectException;
import exceptions.SomeDBException;
import types.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@WebService(endpointInterface = "endpoint.ILibrarianService")
public class LibrarianService implements ILibrarianService {
    LibrarianWrapper libWrapper = new LibrarianWrapper();

    /*
     * Методы для получения данных
     */

    public ArrayList<User> getUsersList() {
        return libWrapper.getUsersList();
    }

    public ArrayList<Operation> findDebtors() {
        return libWrapper.findDebtors();
    }

    public ArrayList<Book> getAllBooks() {
        return libWrapper.getAllBooks();
    }

    public HashMapWrapper getBooksInLibrary() {
        return libWrapper.getBooksInLibrary();
    }

    public ArrayList<UserOrder> getUserOrders() {
        return libWrapper.getUserOrders();
    }

    public ArrayList<PurchaseOrder> getPurchaseOrders() {
        return libWrapper.getPurchaseOrder();
    }

    public ArrayList<Operation> getBookOperations() {
        return libWrapper.getBookOperations();
    }

    /*
     * Методы вызова процедур
     */

    public Integer purchaseBook(int bookId, int shopId, int bookCount, Date purDate) {
        return libWrapper.purchaseBook(bookId, shopId, bookCount, purDate);
    }

    public Integer lockDebtor(int userID) {
        return libWrapper.lockDebtor(userID);
    }

    public Integer unlockDebtor(int userID) {
        return libWrapper.unlockDebtor(userID);
    }

    public boolean authorization(String login, String password) {
        return true;
    }

    public Integer addNewBook(String title, String author, String publishHouse, int pubYear) {
        return libWrapper.addNewBook(title, author, publishHouse, pubYear);
    }

}
