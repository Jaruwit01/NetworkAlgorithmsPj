import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 5000;

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            // Read city name and distance from environment variables or command-line arguments
            String cityName = System.getenv("CITY_NAME"); // or args[0] if using command-line arguments
            int distance = Integer.parseInt(System.getenv("DISTANCE")); // or Integer.parseInt(args[1]) if using command-line arguments

            // Send city information to the server
            output.writeUTF(cityName);
            output.writeInt(distance);

            // Receive acknowledgment from the server
            boolean isAcknowledged = input.readBoolean();
            if (isAcknowledged) {
                System.out.println("City information sent successfully");
            } else {
                System.out.println("Failed to send city information");
            }

            socket.close();
            System.out.println("Disconnected from server");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
