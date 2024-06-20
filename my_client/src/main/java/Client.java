import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
    public void start(String address, int port) {
        try {
            ConnectionThread thread = new ConnectionThread(address, port);
            thread.start();

            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(System.in)
            );

            String login = reader.readLine();
            thread.login(login);

            while (true) {
                String rawMessage = reader.readLine();
                Message message;

                if(rawMessage.equals("/online")) {
                    message = (new Message(MessageType.Online, rawMessage));
                } else if (rawMessage.startsWith("/ ")) {
                        message = (new Message(MessageType.Priv, rawMessage.substring(2)));
                } else {
                    message = new Message(MessageType.Broadcast, rawMessage);
                }
                thread.send(message);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
