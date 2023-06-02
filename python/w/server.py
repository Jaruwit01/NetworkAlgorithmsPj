import socket
from _thread import *
import itertools

host = '0.0.0.0'
port = 1233
ThreadCount = 0

def calculate_total_distance(distances, route):
    total_distance = 0
    num_cities = len(route)
    for i in range(num_cities - 1):
        city = route[i]
        next_city = route[i + 1]
        total_distance += distances[city][next_city]
    return total_distance

def client_handler(connection):
    connection.send(str.encode('You are now connected to the TSP server...'))
    starting_city = int(connection.recv(2048).decode('utf-8'))

    
    distances = [
        [0, 10, 15, 12],  # ระยะห่างระหว่างเมือง 1 กับเมือง 1, 2, 3, 4
        [10, 0, 8, 11],   # ระยะห่างระหว่างเมือง 2 กับเมือง 1, 2, 3, 4
        [15, 8, 0, 9],    # ระยะห่างระหว่างเมือง 3 กับเมือง 1, 2, 3, 4
        [12, 11, 9, 0]    # ระยะห่างระหว่างเมือง 4 กับเมือง 1, 2, 3, 4
    ]
    # แปลงลำดับเมืองใหม่โดยให้เริ่มต้นที่เมืองที่ถูกส่งมาจากผู้ใช้
    all_cities = [i for i in range(len(distances))]
    all_cities.remove(starting_city - 1)
    route_without_start = itertools.permutations(all_cities)
    optimal_route = []

    min_distance = float('inf')
    for route in route_without_start:
        route = [starting_city - 1] + list(route)
        distance = 0
        for i in range(len(route) - 1):
            city = route[i]
            next_city = route[i + 1]
            distance += distances[city][next_city]
        distance += distances[route[-1]][route[0]]  # เพิ่มระยะทางกลับมายังจุดเริ่มต้น
        if distance < min_distance:
            min_distance = distance
            optimal_route = route
            
    city_names = ['A', 'B', 'C', 'D']  # กำหนดชื่อเมืองตามลำดับ
    optimal_route = [city_names[city] for city in optimal_route]
    reply = f'Optimal Route: {" -> ".join(optimal_route)}\nTotal Distance: {min_distance}'
    connection.sendall(str.encode(reply))
    connection.close()



def accept_connections(ServerSocket):
    Client, address = ServerSocket.accept()
    print('Connected to: ' + address[0] + ':' + str(address[1]))
    start_new_thread(client_handler, (Client, ))

def start_server(host, port):
    ServerSocket = socket.socket()
    try:
        ServerSocket.bind((host, port))
    except socket.error as e:
        print(str(e))
    print(f'Server is listening on port {port}...')
    ServerSocket.listen()

    while True:
        accept_connections(ServerSocket)

def tsp(distances):
    num_cities = len(distances)
    all_cities = range(num_cities)
    min_distance = float('inf')
    optimal_route = []

    for route in itertools.permutations(all_cities):
        distance = 0
        for i in range(num_cities - 1):
            city = route[i]
            next_city = route[i + 1]
            distance += distances[city][next_city]
            distance += next_city * 10**(num_cities - i - 2)
        
        if distance < min_distance:
            min_distance = distance
            optimal_route = route
    
    return optimal_route

start_server(host, port)
