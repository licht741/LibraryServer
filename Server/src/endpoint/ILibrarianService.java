package endpoint;

import exceptions.DBConnectException;
import exceptions.SomeDBException;
import types.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@WebService
@SOAPBinding(style= SOAPBinding.Style.DOCUMENT,use= SOAPBinding.Use.LITERAL, parameterStyle= SOAPBinding.ParameterStyle.WRAPPED)
public interface ILibrarianService {

    @WebMethod
    public boolean authorization(String login, String password);

    @WebMethod
    public ArrayList<Book> getAllBooks();

    @WebMethod
    public HashMapWrapper getBooksInLibrary();

    @WebMethod
    public ArrayList<UserOrder> getUserOrders();

    @WebMethod
    public Integer purchaseBook(int bookId, int shopId, int bookCount, Date purDate);

    @WebMethod
    public ArrayList<User> getUsersList();

    @WebMethod
    public Integer addNewBook(String title, String author, String publishHouse, int pubYear);

    @WebMethod
    public ArrayList<PurchaseOrder> getPurchaseOrders();

    @WebMethod
    public ArrayList<Operation> getBookOperations();
}
