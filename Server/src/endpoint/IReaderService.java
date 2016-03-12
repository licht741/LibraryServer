package endpoint;

import types.Book;
import types.Operation;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

@WebService
@SOAPBinding(style= SOAPBinding.Style.DOCUMENT,use= SOAPBinding.Use.LITERAL, parameterStyle= SOAPBinding.ParameterStyle.WRAPPED)
public interface IReaderService {
    @WebMethod
    public ArrayList<Book> getAvailableBooks();

    @WebMethod
    public ArrayList<Operation> getUserBooks(@WebParam(name = "userID") int userID);

    @WebMethod
    public int takeBookFromLibrary(@WebParam(name = "userID") int userID, @WebParam(name = "bookID") int bookID);

    @WebMethod
    public int returnBook(@WebParam(name = "operationID") int operationID);

    @WebMethod
    public int make_order(@WebParam(name = "bookID") int bookID, @WebParam(name = "count") int count);

    @WebMethod
    public int extendUsage(@WebParam(name = "userID") int userID);

    @WebMethod
    public ArrayList<Book> getAllBooks();

    @WebMethod
    public int authorization(@WebParam(name = "login") String login, @WebParam(name = "password") String password);

    @WebMethod
    public int registration(@WebParam(name = "login") String login, @WebParam(name = "password") String password,
                            @WebParam(name = "name") String name);
}
