import socket

# TCP/IP configuration
host = '0.0.0.0'  # Listen on all available network interfaces
port = 5001  # Replace with the desired port number for communication

# Create a socket object
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Bind the socket to the host and port
sock.bind((host, port))

# Listen for incoming connections
sock.listen()

while True:
    # Accept a connection
    conn, addr = sock.accept()
    print(f"Received connection from: {addr}")

    # Receive the data
    data = conn.recv(1024).decode()
    print(f"Received data: {data}")

    # Process the data
    cities = data.split(',')  # Split the received string into a list of cities
    reversed_cities = cities[::-1]  # Reverse the order of cities
    
    # Perform any other processing or computations as needed
    
    # Create a response
    response = "Processed sequence: " + ', '.join(reversed_cities)

    # Send the response back to the sender
    conn.sendall(response.encode())

    # Close the connection
    conn.close()
