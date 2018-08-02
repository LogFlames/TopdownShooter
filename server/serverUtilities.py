
def parseClientInput(clientIndex, msg, conn):
	msg = msg.strip()

	print()

	if msg == '':
		return

	commands = list(map(str.strip, msg.split('#')))

	for command in commands:
		print(command)
		if 'hbb' in command:
			conn[clientIndex][1] = True

		if command.startswith('GU'):

			command = command[2:]
			headers = command.split(';')[:-1]
			print(headers)

			for h in headers:
				if '#' in h:
					break
				splitted = h.split(':')
				attribute = splitted[0]
				value = splitted[1]
				#connections[clientIndex][0], attribute
				print((attribute, value))

			if ['pos_x', 'pos_y', 'health', 'vel_x', 'vel_y', 'rot', 'shoot'] not in command:
				conn[clientIndex][0].clientSocket.send('Invalid game update! Kicking.\n'.encode('utf-8'))
				conn[clientIndex][0].clientSocket.close()
				break

				try:
					conn[clientIndex][0].key = value
					print('Set {} to {}'.format(key, value))
				except:
					print('Incorrect game update from from {}'.format(conn[clientIndex][0].addr))

	return conn