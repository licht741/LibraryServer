package endpoint;

import exceptions.DBConnectException;
import exceptions.SomeDBException;
import types.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
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
    public AuthWrap authorization(@WebParam(name = "login") String login, @WebParam(name = "password") String password);

    @WebMethod
    public int registration(@WebParam(name = "login") String login, @WebParam(name = "password") String password,
                            @WebParam(name="name") String name);

    @WebMethod
    public ArrayList<Book> getAllBooks();

    @WebMethod
    public HashMapWrapper getBooksInLibrary();

    @WebMethod
    public ArrayList<UserOrder> getUserOrders();

    @WebMethod
    public Integer purchaseBook(@WebParam(name = "bookID") int bookId, @WebParam(name = "shopID") int shopId,
                                @WebParam(name = "count") int bookCount, @WebParam(name = "purchaseDate") Date purDate);

    @WebMethod
    public ArrayList<User> getUsersList();

    @WebMethod
    public Integer addNewBook(@WebParam(name = "Stitle") String title, @WebParam(name = "author") String author,
                              @WebParam(name = "pubHouse") String publishHouse, @WebParam(name = "pubYear") int pubYear);

    @WebMethod
    public ArrayList<PurchaseOrder> getPurchaseOrders();

    @WebMethod
    public ArrayList<Operation> getBookOperations();

    @WebMethod
    public ArrayList<Operation> findDebtors();

    @WebMethod
    public Integer lockDebtor(@WebParam (name = "userID") int userID);

    @WebMethod
    public Integer unlockDebtor(@WebParam (name = "userID") int userID);

    @WebMethod
    public ArrayList<Store> getStores();

    @WebMethod
    public int addNewStore(@WebParam (name = "name") String name, @WebParam (name = "phone") String phone,
                           @WebParam (name = "address") String address);
}
