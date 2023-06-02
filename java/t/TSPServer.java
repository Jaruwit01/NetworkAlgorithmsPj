import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TSPServer {
    private static final int port = 1233;
    private static int threadCount = 0;

    public static void main(String[] args) {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                startClientThread(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startClientThread(Socket clientSocket) {
        Thread thread = new Thread(() -> {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write("You are now connected to the TSP server...".getBytes());

                int startingCity = Integer.parseInt(readMessage(inputStream));
                int[][] distances = {
                        {0, 10, 15, 12},   // Distance between city 1 and cities 1, 2, 3, 4
                        {10, 0, 8, 11},    // Distance between city 2 and cities 1, 2, 3, 4
                        {15, 8, 0, 9},     // Distance between city 3 and cities 1, 2, 3, 4
                        {12, 11, 9, 0}     // Distance between city 4 and cities 1, 2, 3, 4
                };

                // Convert city order by starting from the city provided by the client
                List<Integer> allCities = new ArrayList<>();
                for (int i = 0; i < distances.length; i++) {
                    if (i != startingCity - 1) {
                        allCities.add(i);
                    }
                }
                List<List<Integer>> routeWithoutStart = permutations(allCities);

                List<Integer> optimalRoute = new ArrayList<>();
                int minDistance = Integer.MAX_VALUE;

                for (List<Integer> route : routeWithoutStart) {
                    route.add(0, startingCity - 1);
                    int distance = calculateTotalDistance(distances, route);
                    distance += distances[route.get(route.size() - 1)][route.get(0)];  // Add distance to return to starting city
                    if (distance < minDistance) {
                        minDistance = distance;
                        optimalRoute = route;
                    }
                }

                String reply = "Optimal Route: " + optimalRoute + "\nTotal Distance: " + minDistance;
                outputStream.write(reply.getBytes());

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private static String readMessage(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[2048];
        int bytesRead = inputStream.read(buffer);
        return new String(buffer, 0, bytesRead);
    }

    private static int calculateTotalDistance(int[][] distances, List<Integer> route) {
        int totalDistance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            int city = route.get(i);
            int nextCity = route.get(i + 1);
            totalDistance += distances[city][nextCity];
        }
        return totalDistance;
    }

    private static List<List<Integer>> permutations(List<Integer> elements) {
        List<List<Integer>> result = new ArrayList<>();
        permute(elements, 0, result);
        return result;
    }

    private static void permute(List<Integer> elements, int start, List<List<Integer>> result) {
        if (start >= elements.size()) {
            result.add(new ArrayList<>(elements));
        } else {
            for (int i = start; i < elements.size(); i++) {
                swap(elements, start, i);
                permute(elements, start + 1, result);
                swap(elements, start, i);
            }
        }
    }

    private static void swap(List<Integer> elements, int i, int j) {
        Integer temp = elements.get(i);
        elements.set(i, elements.get(j));
        elements.set(j, temp);
    }
}
