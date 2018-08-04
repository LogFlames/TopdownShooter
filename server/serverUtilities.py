
# -*- coding: utf-8 -*-

def parseClientInput(clientIndex, msg, conn):

	commands = list(map(str.strip, msg.split('#')))

	for command in commands:
		if command == '':
			continue

		if 'hbb' in command:
			conn[clientIndex][1] = True

		if command.startswith('GU'):

			command = command[2:]
			headers = command.split(';')[:-1]

			for h in headers:
				if '#' in h:
					continue

				splitted = h.split(':')
				attribute = splitted[0]
				value = splitted[1]

				if attribute not in ['pos_x', 'pos_y', 'health', 'vel_x', 'vel_y', 'rot', 'shoot', 'power']:
					# conn[clientIndex][0].clientSocket.send('Invalid game update! Kicking.\n'.encode('utf-8'))
					# conn[clientIndex][0].clientSocket.close()
					print('Invalid header from {}'.format(conn[clientIndex][0].addr))
					continue

				conn[clientIndex][0].setClientValue(attribute.strip(), value.strip())

	return conn

def broadcastGameUpdate(conn):

	gu = ''

	# First, we must get every active instance of the Client class stored in connections
	for c in conn:
		gu += 'GUidentifier:{};pos_x:{};pos_y:{};health:{};vel_x:{};vel_y:{};rot:{};shoot:{};power:{}#'.format(
			c[0].clientID, c[0].pos_x, c[0].pos_y, c[0].health, c[0].vel_x,
			c[0].vel_y, c[0].rotation, c[0].shoot, c[0].power
		)
	
	# Now, when the string is ready, we can send it to everyone
	for client in conn:
		try:
			client[0].clientSocket.send((gu + '\n').encode('utf-8'))
		except:
			pass