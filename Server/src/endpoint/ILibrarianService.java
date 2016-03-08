package endpoint;

import types.Book;
import types.HashMapWrapper;
import types.ResultWrap;
import types.User;

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
    public ArrayList<Book> getAllBooks() throws Exception;

    @WebMethod
    public HashMapWrapper getBooksInLibrary() throws Exception;

    @WebMethod
    public Integer purchaseBook(int bookId, int shopId, int bookCount, Date purDate) throws Exception;

    @WebMethod
    public ArrayList<User> getUsersList() throws Exception;

    @WebMethod
    public Integer addNewBook(String title, String author, String publishHouse, int pubYear) throws Exception;
}
