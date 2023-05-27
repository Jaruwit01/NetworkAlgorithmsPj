import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {
    public static void main(String[] args) {
        final String SERVER_IP = "192.168.1.106"; // Replace with the actual IP address or hostname
        final int SERVER_PORT = 5000;
        DataInputStream input;
        DataOutputStream output;

        try {
            Scanner scanner = new Scanner(System.in);

            // Connect to the server
            Socket sc = new Socket(SERVER_IP, SERVER_PORT);
            input = new DataInputStream(sc.getInputStream());
            output = new DataOutputStream(sc.getOutputStream());

            // Wait for the shore (no need to enter the number of cities)
            String shore = input.readUTF();
            System.out.println("Shore: " + shore);

            // Receive and print the sorted order of cities from the server
            String sortedOrder = input.readUTF();
            System.out.println("Sorted Order of Cities: " + sortedOrder);

            sc.close();

        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
