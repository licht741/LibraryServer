package endpoint;

import dbwrappers.LibrarianWrapper;
import exceptions.DBConnectException;
import exceptions.SomeDBException;
import types.Book;
import types.HashMapWrapper;
import types.ResultWrap;
import types.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@WebService(endpointInterface = "endpoint.ILibrarianService")
public class LibrarianService implements ILibrarianService {
    LibrarianWrapper libWrapper = new LibrarianWrapper();

    public boolean authorization(String login, String password) {
        return true;
    }

    public ArrayList<Book> getAllBooks() throws SomeDBException, DBConnectException {
        return libWrapper.getAllBooks();
    }

    public HashMapWrapper getBooksInLibrary() throws SomeDBException, DBConnectException {
        return libWrapper.getBooksInLibrary();
    }

    public Integer purchaseBook(int bookId, int shopId,
                                int bookCount, Date purDate) throws SomeDBException, DBConnectException {
        return libWrapper.purchaseBook(bookId, shopId, bookCount, purDate);
    }

    public ArrayList<User> getUsersList() throws SomeDBException, DBConnectException {
        return libWrapper.getUsersList();
    }

    public Integer addNewBook(String title, String author,
                              String publishHouse, int pubYear) throws SomeDBException, DBConnectException {
        return libWrapper.addNewBook(title, author, publishHouse, pubYear);
    }

}
