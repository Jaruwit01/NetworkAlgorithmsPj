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
        Socket sc = null;
        DataInputStream input;
        DataOutputStream output;
        final int PORT = 5000;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started");

            while (true) {
                sc = serverSocket.accept();
                System.out.println("Client connected");

                input = new DataInputStream(sc.getInputStream());
                output = new DataOutputStream(sc.getOutputStream());

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

                sc.close();
                System.out.println("Client disconnected");
            }

        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
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
        List<String> route = new ArrayList<>();
        List<String> remainingCities = new ArrayList<>(cities);
        Random random = new Random();

        // Select a random starting city
        int startIndex = random.nextInt(remainingCities.size());
        String currentCity = remainingCities.get(startIndex);
        route.add(currentCity);
        remainingCities.remove(startIndex);

        while (!remainingCities.isEmpty()) {
            int minIndex = -1;
            double minDistance = Double.MAX_VALUE;

            // Find the nearest remaining city
            for (int i = 0; i < remainingCities.size(); i++) {
                String city = remainingCities.get(i);
                int currentIndex = getIndex(currentCity, cities);
                int cityIndex = getIndex(city, cities);
                double distance = getDistance(coordinates[currentIndex], coordinates[cityIndex]);

                if (distance < minDistance) {
                    minDistance = distance;
                    minIndex = i;
                }
            }

            // Add the nearest city to the route
            currentCity = remainingCities.get(minIndex);
            route.add(currentCity);
            remainingCities.remove(minIndex);
        }

        return route;
    }

    // Method to get the index of a city in the list
    private static int getIndex(String city, List<String> cities) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).equals(city)) {
                return i;
            }
        }
        return -1;
    }

    // Method to calculate the Euclidean distance between two coordinates
    private static double getDistance(double[] coord1, double[] coord2) {
        double dx = coord1[0] - coord2[0];
        double dy = coord1[1] - coord2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }
}
