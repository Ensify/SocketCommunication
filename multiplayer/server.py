from util import Server
import socket

HOST= socket.gethostbyname(socket.gethostname())

server = Server(HOST,6723)
server.run()