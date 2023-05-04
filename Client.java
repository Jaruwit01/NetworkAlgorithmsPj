import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args) {
        final String[] hosts = {"<dummy-node-1-ip>", "<dummy-node-2-ip>", "<dummy-node-3-ip>", "<dummy-node-4-ip>"};
        final int PORT = 5000;

        try {
            for (String host : hosts) {
                Socket socket = new Socket(host, PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                output.writeUTF("Hello from client at " + socket.getLocalAddress().getHostAddress());

                String message = input.readUTF();
                System.out.println("Received message from " + host + ": " + message);

                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
