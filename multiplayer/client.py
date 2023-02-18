from util import Client
import socket

HOST= socket.gethostbyname(socket.gethostname())

c = Client(HOST,6723)