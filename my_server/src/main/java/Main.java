import java.io.IOException;

public class Main {
    public static void main(String[] args)
    {
        try {
            Server server = new Server(8090);
            server.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


// uzywamy bilblitoeki maven jackson databind
// ona pozwala zamienic obiekt na stringa i z powrotem

//server
//musi dzialac w sposob wielowatkowy (dla kazdego kilenta wątek)
//trzeba stworzyc klase server ktora ma liste akutalnie podpietych uzytkownikow

//clientThread
//w nim bedzie zawarta logika przetwarzania klienta (nasluchiwanie, wysylanie)