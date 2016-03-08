import dbwrappers.LibrarianWrapper;
import endpoint.LibrarianService;
import types.AuthWrap;
import types.Book;
import types.ResultWrap;

import javax.xml.transform.Result;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainServerApp {
    public static void main (String[] args) throws Exception {

        Endpoint endpoint = Endpoint.create(new LibrarianService());
        endpoint.publish("http://127.0.0.1:8888/lib");

    }
}
