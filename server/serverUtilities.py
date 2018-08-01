
def parseClientInput(clientIndex, msg, conn):
	msg = msg.strip()

	commands = list(map(str.strip, msg.split('#')))

	for command in commands:
		if 'hbb' in command:
			conn[clientIndex][1] = True
			break

		if command.startswith('GU'):

			command = command[2:]
			headers = command.split(';')
			if ['pos_x', 'pos_y', 'health', 'vel_x', 'vel_y'] not in headers:
				conn[clientIndex][0].clientSocket.send('Invalid game update! Kicking.')
				conn[clientIndex][0].clientSocket.close()
				return

			for h in headers:
				key = h.split(':')[0]
				value = h.split(':')[1]

				try:
					conn[clientIndex][0].key = value
					print('Set {} to {}'.format(key, value))
				except:
					print('Incorrect game update from from {}'.format(conn[clientIndex][0].addr))

	return conn