import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        client.start("localhost", 8090);
    }
}
//edit configuration
//choose application
//choose main as class
//allow multiple instances