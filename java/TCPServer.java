import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TCPServer {
    public static void main(String[] args) {
        int serverPort = 5000;
        int expectedNumCities = 2; // Update with the desired number of cities

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server started");

            List<String> cities = new ArrayList<>();
            Map<String, Map<String, Integer>> distances = new HashMap<>();

            while (cities.size() < expectedNumCities) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

                // Receive the city information from the client
                String city = input.readUTF();
                int distanceToCityA = input.readInt();
                int distanceToCityB = input.readInt();

                // Store the city and distances in the server
                cities.add(city);
                Map<String, Integer> cityDistances = new HashMap<>();
                cityDistances.put("CityA", distanceToCityA);
                cityDistances.put("CityB", distanceToCityB);
                distances.put(city, cityDistances);

                // Send acknowledgment to the client
                output.writeBoolean(true);

                clientSocket.close();
                System.out.println("Client disconnected");
            }

            // Process the cities and distances
            String connectedCity = getConnectedCity(cities, distances);
            System.out.println("Connected city: " + connectedCity);
            System.out.println("Total Distance: " + getTotalDistance(cities, distances, connectedCity));
            System.out.println("Route: " + generateRoute(cities, distances, connectedCity));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String getConnectedCity(List<String> cities, Map<String, Map<String, Integer>> distances) {
        String connectedCity = "";
        int minTotalDistance = Integer.MAX_VALUE;

        for (String city : cities) {
            int totalDistance = getTotalDistance(cities, distances, city);
            if (totalDistance < minTotalDistance) {
                minTotalDistance = totalDistance;
                connectedCity = city;
            }
        }

        return connectedCity;
    }

    private static int getTotalDistance(List<String> cities, Map<String, Map<String, Integer>> distances, String startingCity) {
        int totalDistance = 0;
        String currentCity = startingCity;

        for (int i = 0; i < cities.size() - 1; i++) {
            String nextCity = getNextCity(cities, distances, currentCity);
            int distance = distances.get(currentCity).get(nextCity);
            totalDistance += distance;
            currentCity = nextCity;
        }

        totalDistance += distances.get(currentCity).get(startingCity);

        return totalDistance;
    }

    private static String getNextCity(List<String> cities, Map<String, Map<String, Integer>> distances, String currentCity) {
        String nextCity = "";
        int minDistance = Integer.MAX_VALUE;

        for (String city : cities) {
            if (!city.equals(currentCity)) {
                int distance = distances.get(currentCity).get(city);
                if (distance < minDistance) {
                    minDistance = distance;
                    nextCity = city;
                }
            }
        }

        return nextCity;
    }

    private static String generateRoute(List<String> cities, Map<String, Map<String, Integer>> distances, String startingCity) {
        StringBuilder routeBuilder = new StringBuilder();
        String currentCity = startingCity;
        routeBuilder.append(currentCity).append(" -> ");

        while (true) {
            String nextCity = getNextCity(cities, distances, currentCity);
            if (nextCity.equals(startingCity)) {
                routeBuilder.append(nextCity);
                break;
            }
            routeBuilder.append(nextCity).append(" -> ");
            currentCity = nextCity;
        }

        return routeBuilder.toString();
    }
}
