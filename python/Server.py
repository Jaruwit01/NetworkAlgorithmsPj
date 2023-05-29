import socket

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

    shortest_route = cities[::-1]  # Reversed route for demonstration purposes
    shortest_distance = calculate_distance(shortest_route[::-1], distance_matrix)

    result = {
        'route': shortest_route,
        'distance': shortest_distance
    }

    return result

def calculate_distance(route, distance_matrix):
    distance = 0
    for i in range(len(route) - 1):
        current_city = route[i]
        next_city = route[i + 1]
        distance += distance_matrix[current_city][next_city]
    return distance

def start_server():
    host = '0.0.0.0'   # Listen on all available network interfaces
    port = 5000        # Port number to listen on

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

            # Receive data from the client
            data = client_socket.recv(1024).decode()
            print("Received data:", data)

            # Extract the city name from the received data
            city_name, city_data = data.split(':')
            print(city_name, "is", city_data)

            # Solve the TSPTW problem
            result = solve_tsptw(city_data)

            # Send the result back to the client
            client_socket.sendall(str(result).encode())

            # Close the client connection
            client_socket.close()
            print("Connection closed with", client_address)

    except OSError:
        print("Port is already in use.")
    finally:
        # Close the server socket
        server_socket.close()

# Start the central server
start_server()
