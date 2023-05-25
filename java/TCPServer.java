import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000); // Choose a suitable port number

            while (true) {
                Socket clientSocket = serverSocket.accept();

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Send the sequence of cities to the client
                String[] cities = {"city1", "city2", "city3", "city4"} ; // Replace with your own city sequence
                Scanner  scanner = new Scanner(System.in);
                for (String city : cities) {
                    out.println(city);
                    System.out.println("Sent city: " + city);
                    String message = scanner.nextLine();
                    out.println(city+message);
                }
                // System.out.println("Enter your message: ");
                // String message = scanner.nextLine();
                // out.println(""+message);
                // out.println(cities);


                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
