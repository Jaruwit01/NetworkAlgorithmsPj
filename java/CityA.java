import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CityA {
    public static void main(String[] args) {
        String serverAddress = "192.168.1.106";
        int serverPort = 5000;

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            // Send city information to the server
            String cityName = "CityA";
            int distance = 10;

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
