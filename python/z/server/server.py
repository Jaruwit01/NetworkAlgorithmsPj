import socket

def receive_cities():
    # Create a TCP/IP socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Bind the socket to a specific address and port
    server_address = ('', 5000)  # Listen on all available interfaces
    server_socket.bind(server_address)

    # Listen for incoming connections
    server_socket.listen(1)

    print('Waiting for a connection...')

    while True:
        # Accept a connection
        client_socket, client_address = server_socket.accept()
        print(f'Accepted connection from {client_address}')

        # Receive the data from the client
        data = client_socket.recv(1024).decode()
        print(f'Received cities: {data}')

        # Close the connection
        client_socket.close()

# Start receiving cities
receive_cities()
