
class ClientClass:
	def __init__(self, clientID, connection, addr):
		self.clientID = clientID

		self.x = 0
		self.y = 0
		self.vel_x = 0
		self.vel_y = 0

		self.health = 100
		self.rotation = 0

		self.clientSocket = connection
		self.addr = addr

	def getValues(self):
		return 'id:{};pos_x:{};pos_y:{};health:{};vel_x:{};vel_y:{};rot:{};'.format(self.clientID, self.x, self.y, self.health, self.vel_x, self.vel_y, self.rotation)

	def sendToClient(self, msg):
		
		try:
			self.clientSocket.sendall((msg + '\n').encode('utf-8'))
			print('Sent {} to {}'.format(msg, self.addr))
		except:
			print('Failed to send {} to {}'.format(msg, self.addr))