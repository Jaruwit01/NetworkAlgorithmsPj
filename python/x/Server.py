import socket
import threading

def solve_tsptw(data):
    # TODO: Implement the TSPTW solving algorithm based on the received data
    # Return the result (shortest route and distance)

    # Example code
    cities = data.split()
    distance_matrix = {
        'A': {'A': 0, 'B': 10, 'C': 15, 'D': 20},
        'B': {'A': 10, 'B': 0, 'C': 25, 'D': 30},
        'C': {'A': 15, 'B': 25, 'C': 0, 'D': 35},
        'D': {'A': 20, 'B': 30, 'C': 35, 'D': 0}
    }

    shortest_route = []
    shortest_distance = [float('inf')]  # Mutable object to store shortest distance
    start_city = cities[0]
    visited = [False] * len(cities)

    find_shortest_route(start_city, cities, distance_matrix, shortest_route, visited, 0, shortest_distance)

    result = {
        'route': shortest_route,
        'distance': shortest_distance[0]
    }

    return result

def find_shortest_route(city, cities, distance_matrix, route, visited, current_distance, shortest_distance):
    route.append(city)
    visited[cities.index(city)] = True

    if all(visited):
        route.append(cities[0])  # Add the starting city to complete the route
        current_distance += distance_matrix[city][cities[0]]
        shortest_distance[0] = min(shortest_distance[0], current_distance)
        return

    for next_city in cities:
        if not visited[cities.index(next_city)]:
            new_distance = current_distance + distance_matrix[city][next_city]
            if new_distance < shortest_distance[0]:
                find_shortest_route(next_city, cities, distance_matrix, route, visited, new_distance, shortest_distance)

    visited[cities.index(city)] = False
    route.pop()

def handle_client(client_socket, client_address):
    # Receive data from the client
    data = client_socket.recv(1024).decode()
    print("Received data:", data)

    # Split the received data into city name and distance
    city_data = data.split()
    city_name = city_data[0]
    distance = city_data[1]

    print(city_name, "is", distance)

    # Solve the TSPTW problem
    result = solve_tsptw(data)

    # Send the result back to the client
    client_socket.sendall(str(result).encode())

    # Close the client connection
    client_socket.close()
    print("Connection closed with", client_address)

def start_server():
    host = '0.0.0.0'   # Listen on all available network interfaces
    port = 5400        # Port number to listen on

    # Create a socket object
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
        # Bind the socket to the host and port
        server_socket.bind((host, port))

        # Listen for incoming connections
        server_socket.listen()

        print("Server started. Waiting for connections...")

        while True:
            # Accept a connection from a client
            client_socket, client_address = server_socket.accept()
            print("Connection established with", client_address)

            # Create a thread to handle the client
            client_thread = threading.Thread(target=handle_client, args=(client_socket, client_address))
            client_thread.start()

    except OSError:
        print("Port is already in use.")
    finally:
        # Close the server socket
        server_socket.close()

# Start the central server
start_server()
