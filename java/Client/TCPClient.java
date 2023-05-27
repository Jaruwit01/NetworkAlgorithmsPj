package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {
    public static void main(String[] args) {
        final String host = "192.168.1.106";
        final int port = 5000;
        DataInputStream input;
        DataOutputStream output;

        try {
            Socket socket = new Socket(host, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            // Send the city name to the server
            String cityName = "City1";
            output.writeUTF(cityName);

            // Read the connected city from the server
            String connectedCity = input.readUTF();
            System.out.println(connectedCity);

            // Read the shortest route from the server
            String shortestRoute = input.readUTF();
            System.out.println(shortestRoute);

            // Read the places traveled from the server
            String placesTraveled = input.readUTF();
            System.out.println(placesTraveled);

            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
