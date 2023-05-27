import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        final int PORT = 5000;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                Thread clientThread = new ClientHandler(clientSocket);
                clientThread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Client Handler Thread
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataInputStream input;
        private DataOutputStream output;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                input = new DataInputStream(clientSocket.getInputStream());
                output = new DataOutputStream(clientSocket.getOutputStream());

                // Read the number of cities from the client
                int numCities = input.readInt();

                // Generate random cities with coordinates
                List<String> cities = generateCities(numCities);
                double[][] coordinates = generateCoordinates(numCities);

                // Calculate the optimal route based on the nearest city
                List<String> optimalRoute = calculateOptimalRoute(cities, coordinates);

                // Send the sorted order of cities back to the client
                StringBuilder routeBuilder = new StringBuilder();
                for (String city : optimalRoute) {
                    routeBuilder.append(city).append(" -> ");
                }
                routeBuilder.setLength(routeBuilder.length() - 4); // Remove the trailing "->"
                output.writeUTF(routeBuilder.toString());

                clientSocket.close();
                System.out.println("Client disconnected");

            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Method to generate random cities
    private static List<String> generateCities(int numCities) {
        List<String> cities = new ArrayList<>();
        for (int i = 1; i <= numCities; i++) {
            cities.add("City" + i);
        }
        return cities;
    }

    // Method to generate random coordinates for cities
    private static double[][] generateCoordinates(int numCities) {
        double[][] coordinates = new double[numCities][2];
        Random random = new Random();
        for (int i = 0; i < numCities; i++) {
            coordinates[i][0] = random.nextDouble();
            coordinates[i][1] = random.nextDouble();
        }
        return coordinates;
    }

    // Method to calculate the optimal route based on the nearest city
    private static List<String> calculateOptimalRoute(List<String> cities, double[][] coordinates) {
        // Implement your logic to calculate the optimal route here
        // This code is just a placeholder

        // Randomly shuffle the cities
        List<String> route = new ArrayList<>(cities);
        Random random = new Random();
        for (int i = 0; i < route.size(); i++) {
            int j = random.nextInt(route.size());
            String temp = route.get(i);
            route.set(i, route.get(j));
            route.set(j, temp);
        }
        return route;
    }
}
