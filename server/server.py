
# -*- coding: utf-8 -*-

import socket
import sys
import select
import time

from serverUtilities import *

try:
	serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
except socket.error:
	print('Failed to create socket')
	sys.exit()

def getIp():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.connect(('8.8.8.8', 80))
    return s.getsockname()[0]

host = getIp()

for port in range(1024, 1049):
    try:
        serverSocket.bind((host, port))
        print('Server running on {}:{}'.format(host, port))
        break
    except:
        print('Port {} already in use on this host'.format(port))
    
    if not port:
        print('Found no available port on {} host. Exiting.'.format(host))

# We should have a good way to keep track of active connections

connections = []

serverSocket.listen(5)
serverSocket.settimeout(0.05)

heartbeat_time = 3

last_hearbeat = time.time()

while True:

    try:
        s, addr = serverSocket.accept()

        connections.append([s, addr, True])

        s.settimeout(0.3)
        s.setblocking(0)

        print('Incoming connection from {}'.format(addr))
    except:
        pass

    index = 0
    for client, addr, hbt in connections:
        try:
            incoming = client.recv(4096)
            connections = parseClientInput(index, incoming, connections)
            print('From {}: {}'.format(addr, incoming))

        except:
            pass

        index += 1

    if time.time() - last_hearbeat > heartbeat_time:
        for n in range(len(connections) - 1, -1, -1):
            if not connections[n][2]:
                sendToClient(connections[n][0], ':kicked:')
                connections[n][0].close()
                print('{} didn\'t answer the heartbeat and was kicked from the server.'.format(connections[n][1]))
                del connections[n]
            else:
                connections[n][2] = False
                sendToClient(connections[n][0], ':hb:')
        last_hearbeat = time.time()

serverSocket.close()
sys.exit()
