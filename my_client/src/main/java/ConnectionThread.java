import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class ConnectionThread extends Thread {
    private Socket socket;
    PrintWriter writer;

    public ConnectionThread(String address, int port) throws IOException {
        socket = new Socket(address, port);
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            //buffered reader requires a reader type, so we use InputStreamReader on input
            writer = new PrintWriter(output, true);
            //autoFlush immediately sends message once it's put here

            String rawMessage;
            //wiadomosc z servera nieobrobiona

            while((rawMessage = reader.readLine()) != null) {
                Message message = new ObjectMapper()
                        .readValue(rawMessage, Message.class);
                //readValue(the message, to what class)

                switch (message.type){
                    case Broadcast -> System.out.println(message.content);
                    case Login -> System.out.println(message.content);
                    case Online -> System.out.println(message.content);
                    case Priv -> System.out.println(message.content);
                }
            }
            //loop to deal with the message


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //as we operated on streams from keyboard etc. we will stream from socket
        //this is stream we use to listen for input data
    }
    public void send(Message message) throws JsonProcessingException {
        String rawMessage = new ObjectMapper()
                .writeValueAsString(message);
        writer.println(rawMessage);
        //wylanie przez siec
    }
    public void login(String login) throws JsonProcessingException {
        Message message = new Message(MessageType.Login, login);
        send(message);
    }
}
