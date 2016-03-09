import connector.DatabaseConnector;
import endpoint.LibrarianService;

import javax.xml.ws.Endpoint;
import java.io.File;
import java.io.PrintStream;

public class MainServerApp {
    public static void main (String[] args) throws Exception {
        DatabaseConnector.getInstance();
        System.setErr(new PrintStream(new File("server.log")));
        Endpoint endpoint = Endpoint.create(new LibrarianService());
        endpoint.publish("http://127.0.0.1:8888/lib");

    }
}
