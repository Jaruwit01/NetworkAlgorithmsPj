import socket
import itertools

# Define the list of cities
cities = ['A', 'B', 'C', 'D']

# Function to calculate the total distance for a given sequence of cities
def calculate_distance(sequence):
    # Your distance calculation logic goes here
    return 0  # Replace with the actual distance calculation

# Generate all possible permutations of cities
permutations = list(itertools.permutations(cities))

# Establish TCP/IP connections with each node
nodes = ['192.168.0.1', '192.168.0.2', '192.168.0.3', '192.168.0.4']
node_count = len(nodes)
connections = []

for node_ip in nodes:
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect((node_ip, 9999))
    connections.append(sock)

# Iterate through permutations and send to all nodes
for sequence in permutations:
    # Convert the sequence to a string
    sequence_str = ' '.join(sequence)

    # Send the sequence to all nodes
    for connection in connections:
        connection.sendall(sequence_str.encode())

    # Receive distances from all nodes
    distances = []
    for connection in connections:
        data = connection.recv(1024).decode()
        distance = float(data)
        distances.append(distance)

    # Find the shortest distance and corresponding sequence
    min_distance = min(distances)
    min_index = distances.index(min_distance)
    shortest_sequence = permutations[min_index]

    # Display the shortest path and its total distance
    print(f"Shortest path: {shortest_sequence}")
    print(f"Total distance: {min_distance}")

# Close the connections
for connection in connections:
    connection.close()