import connector.DatabaseConnector;
import endpoint.LibrarianService;
import endpoint.ReaderService;

import javax.xml.ws.Endpoint;
import java.io.File;
import java.io.PrintStream;

public class MainServerApp {
    public static void main (String[] args) throws Exception {
        System.setErr(new PrintStream(new File("server.log")));

        System.out.println("Сервер начинает работу");

        Endpoint libEndpoint = Endpoint.create(new LibrarianService());
        libEndpoint.publish("http://127.0.0.1:8888/lib");

        Endpoint readerEndpoint = Endpoint.create(new ReaderService());
        readerEndpoint.publish("http://127.0.0.1:8889/reader");

        System.out.println("Веб сервисы запущены");

    }
}
