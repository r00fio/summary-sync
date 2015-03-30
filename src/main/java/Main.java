import java.io.IOException;

/**
 * Created by pixel on 3/28/15.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.bootstrap();
        server.start();
    }
}
