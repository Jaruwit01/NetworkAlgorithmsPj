import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    static public void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket sc = null;
        DataInputStream input;
        DataOutputStream output;
        final  int PUERTO = 5000;
        try {
            serverSocket = new ServerSocket(PUERTO);
            System.out.println("Server started");

            while (true) {
                sc = serverSocket.accept();

                System.out.println("Client connected");
                input = new DataInputStream(sc.getInputStream());
                output = new DataOutputStream(sc.getOutputStream());

                String message = input.readUTF();
                System.out.println(message);

                output.writeUTF("Hello from server");
                sc.close();

                System.out.println("Client disconnected");
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}