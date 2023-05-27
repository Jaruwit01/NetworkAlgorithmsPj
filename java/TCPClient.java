// import java.io.DataInputStream;
// import java.io.DataOutputStream;
// import java.io.IOException;
// import java.net.Socket;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;
// import java.util.logging.Level;
// import java.util.logging.Logger;

// public class TCPClient {
//     public static void main(String[] args) {
//         final String HOST = "192.168.1.106";
//         final int PORT = 5000;
//         DataInputStream input;
//         DataOutputStream output;

//         try {
//             Scanner scanner = new Scanner(System.in);

//             // Connect to the server
//             Socket sc = new Socket(HOST, PORT);
//             input = new DataInputStream(sc.getInputStream());
//             output = new DataOutputStream(sc.getOutputStream());

//             // Get the number of cities from the user
//             System.out.print("Enter the number of cities (up to 4): ");
//             int numCities = scanner.nextInt();
//             output.writeInt(numCities);

//             scanner.nextLine(); // Consume the newline character

//             // Get the city names and coordinates from the user
//             for (int i = 1; i <= numCities; i++) {
//                 System.out.print("Enter the name of City " + i + ": ");
//                 String cityName = scanner.nextLine();
//                 output.writeUTF(cityName);

//                 System.out.print("Enter the x-coordinate for " + cityName + ": ");
//                 double x = scanner.nextDouble();
//                 output.writeDouble(x);

//                 System.out.print("Enter the y-coordinate for " + cityName + ": ");
//                 double y = scanner.nextDouble();
//                 output.writeDouble(y);

//                 scanner.nextLine(); // Consume the newline character
//             }

//             // Receive and print the optimal route from the server
//             String optimalRoute = input.readUTF();
//             System.out.println("\nOptimal Route: " + optimalRoute);

//             sc.close();

//         } catch (IOException ex) {
//             Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
//         }
//     }
// }



// import java.io.DataInputStream;
// import java.io.DataOutputStream;
// import java.io.IOException;
// import java.net.Socket;
// import java.util.Scanner;
// import java.util.logging.Level;
// import java.util.logging.Logger;

// public class TCPClient {
//     public static void main(String[] args) {
//         final String Host = "192.168.1.106";
//         final int PORT = 5002;
//         DataInputStream input;
//         DataOutputStream output;

//         try {
//             Socket sc = new Socket(Host, PORT);
//             input = new DataInputStream(sc.getInputStream());
//             output = new DataOutputStream(sc.getOutputStream());

//             Scanner scanner = new Scanner(System.in);

//             // Client message loop
//             while (true) {
//                 // Send client message
//                 System.out.print("Client: ");
//                 String clientMessage = scanner.nextLine();
//                 output.writeUTF(clientMessage);

//                 // Exit loop if client message is "bye"
//                 if (clientMessage.equalsIgnoreCase("bye")) {
//                     break;
//                 }

//                 // Read message from server
//                 String serverMessage = input.readUTF();
//                 System.out.println("Server: " + serverMessage);
//             }

//             sc.close();

//         } catch (IOException ex) {
//             Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
//         }
//     }
// }

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

            // Get the number of cities from the user
            System.out.print("Enter the number of cities (up to 4): ");
            int numCities = scanner.nextInt();
            output.writeInt(numCities);

            // Receive and print the sorted order of cities from the server
            String sortedOrder = input.readUTF();
            System.out.println("\nSorted Order of Cities: " + sortedOrder);

            sc.close();

        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

