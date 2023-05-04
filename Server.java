import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        final int PORT = 5000;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started");

            while (true) {
                Socket client = serverSocket.accept();
                clients.add(client);
                System.out.println("Client connected: " + client.getInetAddress());

                Thread clientThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataInputStream input = new DataInputStream(client.getInputStream());
                            String message = "";
                            while (true) {
                                message = input.readUTF();
                                System.out.println("Received message: " + message);

                                for (Socket otherClient : clients) {
                                    if (otherClient != client) {
                                        DataOutputStream output = new DataOutputStream(otherClient.getOutputStream());
                                        output.writeUTF(message);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Client disconnected: " + client.getInetAddress());
                            clients.remove(client);
                        }
                    }
                });
                clientThread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
