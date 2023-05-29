import socket

def send_data_to_all_cities(server_ip, cities, city_name, distance):
    # Iterate through all cities and send the data
    for city in cities:
        if city != city_name:
            send_data(server_ip, city, city_name, distance)

def send_data(server_ip, city, city_name, distance):
    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
        # Connect to the server
        client_socket.connect((server_ip, 5400))

        # Prepare the data to send
        data = city_name + ':' + distance

        # Send the data to the server
        client_socket.sendall(data.encode())

        # Receive the result from the server
        result = client_socket.recv(1024).decode()
        print("Received result from", city, ":", result)

    except ConnectionRefusedError:
        print("Connection refused by", city)

    finally:
        # Close the client socket
        client_socket.close()

def main():
    # Define the IP address of the server
    server_ip = '192.168.1.181'

    # Define the list of cities
    cities = ['CityA', 'CityB', 'CityC', 'CityD']

    # Send the data from each city to all other cities
    for i, city in enumerate(cities):
        send_data_to_all_cities(server_ip, cities, city, str(i))

if __name__ == '__main__':
    main()
