import socket

host = '127.0.0.1'
port = 1233

ClientSocket = socket.socket()
print('Waiting for connection...')
try:
    ClientSocket.connect((host, port))
except socket.error as e:
    print(str(e))

Response = ClientSocket.recv(2048)
print(Response.decode('utf-8'))

starting_city = input('Enter the starting city (A, B, C, D): ')
if starting_city == 'A' or starting_city == 'a':
    starting_city_num = 1
    ClientSocket.send(str.encode(str(starting_city_num)))
elif starting_city.upper() == 'B' or starting_city.upper() == 'b':
    starting_city_num = 2
    ClientSocket.send(str.encode(str(starting_city_num)))
elif starting_city.upper() == 'C' or starting_city.upper() == 'c':
    starting_city_num = 3
    ClientSocket.send(str.encode(str(starting_city_num)))
elif starting_city.upper() == 'D' or starting_city.upper() == 'd':
    starting_city_num = 4
    ClientSocket.send(str.encode(str(starting_city_num)))
else:
    print('Invalid starting city.')
    exit(1)


Response = ClientSocket.recv(2048)
print(Response.decode('utf-8'))


ClientSocket.close()