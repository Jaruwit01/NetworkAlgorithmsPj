import socket
import itertools

# List of cities
cities = ['City1', 'City2', 'City3', 'City4']

# Generate all possible permutations of cities
sequences = list(itertools.permutations(cities))

# IP addresses of the Docker containers
node_ips = ['172.20.0.2', '172.20.0.3', '172.20.0.4']

# TCP/IP port
port = 5001  # Replace with the port number used for communication

# Iterate over sequences and send to each node
for sequence in sequences:
    for node_ip in node_ips:
        # Create a socket object
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        
        try:
            # Connect to the node
            sock.connect((node_ip, port))
            
            # Convert the sequence to a string
            message = ','.join(sequence)
            
            # Send the message
            sock.sendall(message.encode())
            
            # Wait for a response (optional)
            response = sock.recv(1024)
            print(f"Received response from node {node_ip}: {response.decode()}")
            
        finally:
            # Close the socket
            sock.close()
