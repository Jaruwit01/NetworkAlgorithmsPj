import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TSPClient {
    private static final String host = "192.168.1.12";
    private static final int port = 1233;

    public static void main(String[] args) {
        Socket clientSocket;

        try {
            clientSocket = new Socket(host, port);
            System.out.println("Connected to the TSP server...");

            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            String response = readMessage(inputStream);
            System.out.println(response);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the starting city (1-4): ");
            String startingCity = scanner.nextLine();
            outputStream.write(startingCity.getBytes());

            response = readMessage(inputStream);
            System.out.println(response);

            while (true) {
                System.out.print("Enter 'Stop' to stop or press Enter to get the optimal route: ");
                String input = scanner.nextLine();
                outputStream.write(input.getBytes());

                response = readMessage(inputStream);
                System.out.println(response);

                if (input.equals("Stop")) {
                    break;
                }
            }

            clientSocket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readMessage(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[2048];
        int bytesRead = inputStream.read(buffer);
        return new String(buffer, 0, bytesRead);
    }
}
