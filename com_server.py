import socket

serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serversocket.bind(("172.16.1.151", 2049))
serversocket.listen(1)

clients = []

serversocket.settimeout(0.5)

while True:
    try:
        client, addr = serversocket.accept()
        clients.append(client)
        client.settimeout(0.3)
    except:
        pass

    for a in clients:
        try:
            mes = a.recv(4096)
            for b in clients:
                if a != b:
                    b.send((mes + "\n").encode("utf-8"))
        except:
            pass
