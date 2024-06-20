import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    //for class to operate on a different thread it needs to extend thread
    //then we can overwrite run
    Socket client;
    Server server;
    PrintWriter writer;
    //we want it to be usable thru the entire class v
    String clientName;
    public ClientThread(Socket client, Server server){
        this.client = client;
        this.server = server;
    }
    public String getClientName() {
        return clientName;
    }
    public void run() {
        try {
            InputStream input = client.getInputStream();
            OutputStream output = client.getOutputStream();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(input));
            //buffered reader requires a reader type, so we use InputStreamReader on input
            writer = new PrintWriter(output,true);

            //autoFlush immediately sends message once it's put here

            String rawMessage;
            String clientName;
            //wiadomosc z servera nieobrobiona
            // "{"type": "", "content": ""}"

            while((rawMessage = reader.readLine()) != null) {

                    Message message = new ObjectMapper()
                            .readValue(rawMessage, Message.class);

                    switch (message.type) {
                        case Broadcast -> server.broadcast(message);
                        case Login -> login(message.content);
                        case Online -> server.list(this);
                        case Priv -> server.privMessage(rawMessage, this);
                    }


                //loop to deal with the message
            }
        } catch (IOException e) {
            try {
                client.close();
                server.removeClient(this);
            } catch (IOException ex) {
                throw new RuntimeException(e);
            }
        }
    }


        //as we operated on streams from keyboard etc. we will stream from socket
        //this is stream we use to listen for input data

    public void send(Message message) throws JsonProcessingException {
        String rawMessage = new ObjectMapper()
                .writeValueAsString(message);
        writer.println(rawMessage);
        //wylanie przez siec
    }

    public void login(String name) throws JsonProcessingException {
        clientName = name;
        Message message = new Message(MessageType.Broadcast, "Welcome, " + name);
        send(message);
        Message messageForRest = new Message(MessageType.Broadcast, name + " has joined");
        server.broadcast(messageForRest);
    }
}

//jak dziala stumien:
//socket to dwa strumienie, in and out
//jak chcemy cos odebrac to wysttarczyy caly czas nasluchiwac na inpucie
//jak chcemy cos wyslac trzeba 'wlozyc' wiadomosc and flush it

//przy dodawaniu kolejnych typow wiadomosci dodajemy kolejne case

