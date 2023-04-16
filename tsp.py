import socket
import subprocess
from threading import Thread

# Define the IP address and port for each node
node_addresses = {
    1: ('192.168.0.1', 5000),
    2: ('192.168.0.2', 5000),
    3: ('192.168.0.3', 5000),
    4: ('192.168.0.4', 5000)
}

# Define the TSP tour size
tsp_size = 4

def send_sequence(node_index, sequence):
    """Send a sequence to all other nodes on the network"""
    for index, address in node_addresses.items():
        if index != node_index:
            with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
                s.connect(address)
                s.sendall(sequence.encode())

def run_node(node_index):
    """Run a TSP node"""
    # Create a socket and bind it to the node's IP address and port
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind(node_addresses[node_index])
        s.listen()
        # If this is the first node, send its sequence to all other nodes
        if node_index == 1:
            send_sequence(1, "1")
        # Wait for incoming connections
        while True:
            conn, addr = s.accept()
            with conn:
                # Receive the incoming sequence
                data = conn.recv(1024)
                if not data:
                    break
                # Append the node's index to the sequence and send it to all other nodes
                sequence = data.decode() + str(node_index)
                if len(sequence) == tsp_size:
                    print("TSP tour:", sequence)
                else:
                    send_sequence(node_index, sequence)

# Create a thread for each node and start it
threads = []
for node_index in node_addresses:
    thread = Thread(target=run_node, args=(node_index,))
    thread.start()
    threads.append(thread)

# Wait for all threads to finish
for thread in threads:
    thread.join()

# Stop and remove the Docker containers for each node
for node_index in node_addresses:
    subprocess.run(["docker", "stop", f"node{node_index}"])
    subprocess.run(["docker", "rm", f"node{node_index}"])
