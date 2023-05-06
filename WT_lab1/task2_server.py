import socket
from threading import Thread
import enum


class Modes(enum.Enum):
    ready = 1
    paused = 0


def main():
    sock = socket.socket()
    sock.bind(('localhost', 8000))
    sock.listen(999)
    while True:
        client_connection, client_address = sock.accept()
        print(client_address)
        Thread(target=newClient, args=(client_connection, )).start()


def newClient(conn):
    mode = Modes.ready
    conn.send('Welcome to the server\n\r List of commands: resume, pause, exit\n\r'.encode('utf-8'))
    while True:
        command = ''
        while '\n' not in command:
            char = conn.recv(1024).decode('utf-8')
            if not char:
                conn.close()
                break
            command += char
        command = command.replace('\r\n', '')
        if mode == Modes.paused and command != 'resume':
            conn.send('The server is paused, to enable it, enter "resume"\n\r'.encode('utf-8'))
            continue
        elif command == 'resume':
            mode = Modes.ready
            conn.send('The server is started\n\r'.encode('utf-8'))
        elif command == 'pause':
            mode = Modes.paused
            conn.send('The server is paused\n\r'.encode('utf-8'))
        elif command == 'exit':
            conn.send('The connection was broken\n\r'.encode('utf-8'))
            conn.close()
            break
        else:
            conn.send('Wrong command\n\r'.encode('utf-8'))


if __name__ == '__main__':
    main()

