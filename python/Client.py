import socket

def send_data(city_name, city_data):
    server_host = '192.168.1.106'  # IP address of the central server
    server_port = 5000                 # Port number of the central server

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
        # Connect to the server
        client_socket.connect((server_host, server_port))

        # Send the data
        data = f"{city_name}:{city_data}"
        client_socket.sendall(data.encode())

        # Receive the result from the server
        result = client_socket.recv(1024).decode()
        print(f"{city_name} received result:\n", result)

    except ConnectionRefusedError:
        print("Could not connect to the server.")

    finally:
        # Close the client socket
        client_socket.close()

# Test the code
cityD_name = 'City C'
cityD_data = 'A B C D'  # Example city data
send_data(cityD_name, cityD_data)


