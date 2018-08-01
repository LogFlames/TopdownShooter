
def parseClientInput(clientIndex, msg, conn):
    msg = msg.strip()

    if ':hb:b:' in msg:
        conn[clientIndex][2] = True

    return conn

def sendToClient(client, msg):
    client.send((msg + '\n').encode('utf-8'))