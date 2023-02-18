import threading
import socket

class Server:
    def __init__(self,host,port):
        self.s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        self.s.bind((host,port))
        self.s.listen()
        print(f"Server created on {host}:{port}")
        self.connections = []
    def handler(self,c,a):
        try:
            while True:
                data = c.recv(1024).decode('ascii')
                if not data:
                    raise socket.error
                print(f"Client({a[0]}:{a[1]}) sent {data}")
        except socket.error:
            print(f"Client({a[0]}:{a[1]}) disconnected.")
            self.connections.remove(c)
            c.close()
    def run(self):
        while True:
            conn,addr = self.s.accept()
            self.connections.append(conn)
            print(f"Connected to client of {addr[0]}:{addr[1]}")
            thread = threading.Thread(target=self.handler,args=(conn,addr),daemon=True)
            thread.start()

class Client:
    def __init__(self,host,port):
        self.s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        self.s.connect((host,port))
        print(f"Connected to server.")
        self.sendthread = threading.Thread(target = self.send)
        self.sendthread.start()

    def send(self):
        try:
            while True:
                self.s.send(input().encode('ascii'))
        except socket.error:
            print("Disconnected..")


if __name__ == "__main__":
    """
    Debug and test
    """