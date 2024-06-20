import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Server {
    private ServerSocket socket;
    //server socket actually makes it possible to have server connection
    List<ClientThread> clients = new ArrayList<>();

    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    //method for listening for clients
    public void listen() throws IOException {
        while(true) {
            Socket client = socket.accept();
            //waits forever until a client connects, then assigns him to 'client' variable

            ClientThread thread = new ClientThread(client, this);
            clients.add(thread);

            thread.start();
            //it executes run method in clientThread class
        }
    }

    public void broadcast(Message message) throws JsonProcessingException {
        for (ClientThread client : clients) {
            client.send(message);
        }
    }

    public void list(ClientThread cThread) throws JsonProcessingException {
        String clientsList = clients.stream()
                .map(ClientThread::getClientName)
                .collect(Collectors.joining("\n"));
        String messageContent = "List of clients: \n" + clientsList;
        Message message = new Message(MessageType.Online, messageContent);
        cThread.send(message);
    }

    public void privMessage(String clientName, ClientThread sender) throws JsonProcessingException {
        String[] words = clientName.split(" ");
        String name = words[0];
        System.out.println(name);
        System.out.println(clientName.substring(name.length()));

        String content = clientName.substring(name.length());
        Message message = null;

        for (ClientThread client : clients) {
            if(client.getClientName().equals(name)){
                message = new Message(MessageType.Priv, content);
                client.send(message);
            }
        }

        if (message.equals(null)) {
            Message message1 = new Message(MessageType.Priv, "User " + name + "is not connected.");
            sender.send(message1);
        }
    }

    public void removeClient(ClientThread client) {
        clients.remove(client);
        try {
            broadcast(new Message(MessageType.Broadcast, client.getClientName() + " has left the chat."));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}


