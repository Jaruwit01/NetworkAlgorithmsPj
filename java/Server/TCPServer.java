package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer {
    private static Map<String, double[]> cityCoordinates = new HashMap<>();
    private static Map<String, List<String>> clientTraveledCities = new HashMap<>();
    private static Map<String, List<String>> clientShortestRoutes = new HashMap<>();

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
        private String cityName;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                input = new DataInputStream(clientSocket.getInputStream());
                output = new DataOutputStream(clientSocket.getOutputStream());

                // Read the city name from the client
                cityName = input.readUTF();

                // Generate cities with coordinates
                List<String> cities = generateCities();
                double[][] coordinates = generateCoordinates(cities);

                // Calculate the optimal route based on the nearest city
                List<String> optimalRoute = calculateOptimalRoute(cityName, cities, coordinates);

                // Send the connected city name to the client
                output.writeUTF("Connected to City: " + cityName);

                // Send the shortest route and the updated places traveled to the client
                StringBuilder routeBuilder = new StringBuilder();
                StringBuilder placesTraveledBuilder = new StringBuilder();

                // Retrieve the previously traveled cities by the client
                List<String> traveledCities = clientTraveledCities.get(cityName);
                if (traveledCities == null) {
                    traveledCities = new ArrayList<>();
                }

                // Add the connected city to the traveled cities
                traveledCities.add(cityName);

                // Update the traveled cities for the client
                clientTraveledCities.put(cityName, traveledCities);

                // Update the shortest route for the client
                clientShortestRoutes.put(cityName, optimalRoute);

                // Build the shortest route and places traveled strings
                for (int i = 0; i < optimalRoute.size(); i++) {
                    String city = optimalRoute.get(i);
                    routeBuilder.append(city).append(" -> ");
                    placesTraveledBuilder.append(city);

                    if (traveledCities.contains(city)) {
                        placesTraveledBuilder.append(" [Traveled]");
                    }

                    if (i != optimalRoute.size() - 1) {
                        routeBuilder.append(", ");
                        placesTraveledBuilder.append(", ");
                    }
                }

                // Send the shortest route and places traveled to the client
                output.writeUTF("Shortest Route: " + routeBuilder.toString());
                output.writeUTF("Places Traveled: " + placesTraveledBuilder.toString());

                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Method to generate cities with coordinates
        private List<String> generateCities() {
            List<String> cities = new ArrayList<>();
            cities.add("City1");
            cities.add("City2");
            cities.add("City3");
            cities.add("City4");
            return cities;
        }

        // Method to generate coordinates for cities
        private double[][] generateCoordinates(List<String> cities) {
            double[][] coordinates = new double[cities.size()][2];

            // Retrieve and assign coordinates from stored data
            for (int i = 0; i < cities.size(); i++) {
                String city = cities.get(i);
                double[] coordinate = cityCoordinates.get(city + "_" + i);
                if (coordinate != null) {
                    coordinates[i] = coordinate;
                } else {
                    // Generate random coordinates for the city if not available
                    coordinates[i][0] = Math.random() * 100;
                    coordinates[i][1] = Math.random() * 100;
                }
            }

            return coordinates;
        }

        // Method to calculate the optimal route based on the nearest city
        private List<String> calculateOptimalRoute(String cityName, List<String> cities, double[][] coordinates) {
            // Implement your logic to calculate the optimal route here
            // This code is just a placeholder

            // Randomly shuffle the cities
            List<String> route = new ArrayList<>(cities);
            return route;
        }
    }
}
