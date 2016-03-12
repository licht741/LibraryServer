package endpoint;


import dbwrappers.ReaderWrapper;
import types.Book;
import types.Operation;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;

@WebService(endpointInterface = "endpoint.IReaderService")
public class ReaderService implements IReaderService{
    ReaderWrapper readerWrapper = new ReaderWrapper();

    public ArrayList<Book> getAvailableBooks() {
        return readerWrapper.getAvailableBooks();
    }

    public ArrayList<Operation> getUserBooks(int userID) {
        return readerWrapper.getUserBooks(userID);
    }

    public int takeBookFromLibrary(int userID, int bookID) {
        return readerWrapper.takeBookFromLibrary(userID, bookID);
    }

    public int returnBook(int operationID) {
        return readerWrapper.returnBook(operationID);
    }

    public int make_order(int bookID, int count) { return readerWrapper.makeOrder(bookID, count);}

    public int extendUsage(int recordID) { return readerWrapper.extendUsage(recordID); }

    public ArrayList<Book> getAllBooks() { return readerWrapper.getAllBooks(); }

    public int authorization(String login, String password) {
        return readerWrapper.authorization(login, password);
    }

    public int registration(String login, String password, String name) {
        return readerWrapper.registration(login, password, name);
    }
}
