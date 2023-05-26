import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

            sc = serverSocket.accept();
            System.out.println("Client connected");

            input = new DataInputStream(sc.getInputStream());
            output = new DataOutputStream(sc.getOutputStream());

            // Read the number of cities from the client
            int numCities = input.readInt();

            // Read the city names from the client
            List<String> cities = new ArrayList<>();
            for (int i = 0; i < numCities; i++) {
                String cityName = input.readUTF();
                if (!cityName.isEmpty()) {
                    cities.add(cityName);
                }
            }

            // Read the city coordinates from the client
            double[][] coordinates = new double[numCities][2];
            for (int i = 0; i < numCities; i++) {
                double x = input.readDouble();
                double y = input.readDouble();
                coordinates[i][0] = x;
                coordinates[i][1] = y;
            }

            // Generate all possible permutations of the cities
            List<List<String>> permutations = generatePermutations(cities);

            // Calculate the total distance for each permutation
            double minDistance = Double.MAX_VALUE;
            List<String> optimalRoute = new ArrayList<>();

            for (List<String> route : permutations) {
                double distance = calculateTotalDistance(route, coordinates);
                if (distance < minDistance) {
                    minDistance = distance;
                    optimalRoute = route;
                }
            }

            // Send the optimal route back to the client
            StringBuilder routeBuilder = new StringBuilder();
            for (String city : optimalRoute) {
                routeBuilder.append(city).append(" -> ");
            }
            routeBuilder.setLength(routeBuilder.length() - 4); // Remove the trailing "->"
            output.writeUTF(routeBuilder.toString());

            sc.close();
            System.out.println("Client disconnected");

        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method to generate all possible permutations of a list of cities
    private static List<List<String>> generatePermutations(List<String> cities) {
        List<List<String>> permutations = new ArrayList<>();
        generatePermutationsHelper(cities.size(), cities, permutations);
        return permutations;
    }

    private static void generatePermutationsHelper(int n, List<String> cities, List<List<String>> permutations) {
        if (n == 1) {
            permutations.add(new ArrayList<>(cities));
        } else {
            for (int i = 0; i < n - 1; i++) {
                generatePermutationsHelper(n - 1, cities, permutations);
                if (n % 2 == 0) {
                    swap(cities, i, n - 1);
                } else {
                    swap(cities, 0, n - 1);
                }
            }
            generatePermutationsHelper(n - 1, cities, permutations);
        }
    }

    private static void swap(List<String> cities, int i, int j) {
        String temp = cities.get(i);
        cities.set(i, cities.get(j));
        cities.set(j, temp);
    }

    // Method to calculate the total distance of a route
    private static double calculateTotalDistance(List<String> route, double[][] coordinates) {
        double totalDistance = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            String cityA = route.get(i);
            String cityB = route.get(i + 1);
            int indexA = getIndex(cityA, route);
            int indexB = getIndex(cityB, route);
            double distance = getDistance(coordinates[indexA], coordinates[indexB]);
            totalDistance += distance;
        }
        return totalDistance;
    }

    // Method to get the index of a city in the route list
    private static int getIndex(String city, List<String> route) {
        for (int i = 0; i < route.size(); i++) {
            if (route.get(i).equals(city)) {
                return i;
            }
        }
        return -1;
    }

    // Method to calculate the Euclidean distance between two cities
    private static double getDistance(double[] cityA, double[] cityB) {
        double x1 = cityA[0];
        double y1 = cityA[1];
        double x2 = cityB[0];
        double y2 = cityB[1];
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
