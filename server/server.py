
# -*- coding: utf-8 -*-

import socket
import sys
import select
import time

from serverUtilities import *
from ClientClass import *

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
clientID = 1

serverSocket.listen(5)
serverSocket.settimeout(0.07)

heartbeat_time = 3

last_hearbeat = time.time()

while True:

	broadcastGameUpdate(connections)

	try:
		s, addr = serverSocket.accept()
	except:
		s = False
	
	if s:
		connections.append([ClientClass(clientID, s, addr), True])
		s.send(('?' + str(clientID) + '#\n').encode('utf-8'))
		clientID += 1
		
		s.settimeout(0.3)
		s.setblocking(0)

		print('Incoming connection from {}'.format(addr))
		print(connections)

	index = 0

	for Client, hbt in connections:

		if Client.clientSocket == None or Client.clientSocket.fileno() == -1:
			continue

		try:
			incoming = Client.clientSocket.recv(4096)

			if len(incoming) == 0:
				print('Kicked {} from the server!'.format(Client.addr))
				Client.clientSocket.close()
				connections.remove([Client, hbt])

			incoming = incoming.strip()

			connections = parseClientInput(index, incoming, connections)
			if connections == None:
				connections = []

			if incoming:
				print('From {}: {}'.format(Client.addr, incoming))
		except:
			pass

		index += 1

	if time.time() - last_hearbeat > heartbeat_time:

		for n in range(len(connections) -1, -1, -1):

			if not connections[n][1]:
				connections[n][0].sendToClient('kicked#')
				connections[n][0].clientSocket.close()
				print('{} failed to answer the heartbeat and was kicked from the server'.format(connections[n][0].addr))
				del connections[n]

			else:
				connections[n][1] = False
				connections[n][0].sendToClient('hb#')
		last_hearbeat = time.time()

serverSocket.close()
sys.exit()
