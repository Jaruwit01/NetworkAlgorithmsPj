import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000); // Replace "localhost" with the server IP address

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String cities = in.readLine();
            System.out.println("Received cities: " + cities);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
