import itertools
import socket

def calculate_distance(city1, city2):
    # Calculate the distance between two cities
    # Replace this function with your own implementation
    # You can use distance matrices, GPS coordinates, or any other method
    distances = {
        ('A', 'B'): 10,
        ('A', 'C'): 15,
        ('A', 'D'): 20,
        ('B', 'C'): 35,
        ('B', 'D'): 25,
        ('C', 'D'): 30
    }
    return distances[(city1, city2)]

def solve_tsp(cities):
    # Generate all possible permutations of cities
    permutations = list(itertools.permutations(cities))

    # Initialize variables
    min_distance = float('inf')
    best_path = None

    # Iterate through each permutation
    for path in permutations:
        # Calculate the total distance for the current permutation
        total_distance = 0
        for i in range(len(path) - 1):
            total_distance += calculate_distance(path[i], path[i+1])

        # Check if the current permutation has a smaller distance
        if total_distance < min_distance:
            min_distance = total_distance
            best_path = path

    return best_path, min_distance

def send_cities_to_nodes(cities):
    # Connect to each node and send the sequence of cities
    nodes = ['node1', 'node2', 'node3', 'node4']
    port = 5000

    for node in nodes:
        try:
            # Create a socket and connect to the node
            client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            container_name = f"{node}_container"  # Assuming containers are named as node1_container, node2_container, etc.
            container_ip = socket.gethostbyname(container_name)
            client_socket.connect((container_ip, port))

            # Send the cities to the node
            cities_str = ' '.join(cities)
            client_socket.send(cities_str.encode())

            # Close the socket
            client_socket.close()

            print(f'Sent cities to node {node}')
        except ConnectionRefusedError:
            print(f'Failed to connect to node {node}')

# Example usage
cities = ['A', 'B', 'C', 'D']
send_cities_to_nodes(cities)
best_path, min_distance = solve_tsp(cities)
print(f'Best path: {best_path}')
print(f'Minimum distance: {min_distance}')
