
# -*- coding: utf-8 -*-

class ClientClass:
	def __init__(self, clientID, connection, addr):
		self.clientID = clientID

		self.pos_x = 0
		self.pos_y = 0
		self.vel_x = 0
		self.vel_y = 0

		self.health = 100
		self.rotation = 0

		self.clientSocket = connection
		self.addr = addr

		self.shoot = False

	def getValues(self):
		return 'id:{};pos_x:{};pos_y:{};health:{};vel_x:{};vel_y:{};rot:{};'.format(self.clientID, self.x, self.y, self.health, self.vel_x, self.vel_y, self.rotation)

	def sendToClient(self, msg):
		
		try:
			self.clientSocket.sendall((msg + '\n').encode('utf-8'))
			print('Sent {} to {}'.format(msg, self.addr))
		except:
			print('Failed to send {} to {}'.format(msg, self.addr))

	def setClientValue(self, attr, val):
		
		# I really, really hate this code but I can't figure out how to do it dynamically!

		if attr == 'pos_x':
			self.pos_x = val

		elif attr == 'pos_y':
			self.pos_y = val

		elif attr == 'vel_x':
			self.vel_x = val

		elif attr == 'vel_y':
			self.vel_y = val

		elif attr == 'health':
			self.health = val

		elif attr == 'rot':
			self.rotation = val

		elif attr == 'shoot':
			self.shoot = val

		# Probably the ugliest code I've ever written