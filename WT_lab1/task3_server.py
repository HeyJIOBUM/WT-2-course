import socket
import enum


class Modes(enum.Enum):
    ready = 1
    paused = 0


def main():
    sock = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
    sock.bind(('localhost', 8080))
    modes_list = {}
    while True:
        mes_addr = sock.recvfrom(1024)
        command = mes_addr[0].decode('utf-8')
        address = mes_addr[1]
        if f'{address}' not in modes_list:
            sock.sendto('Welcome to the server\n List of commands: resume, pause, exit'.encode('utf-8'), address)
            modes_list[f'{address}'] = Modes.ready
            continue
        if modes_list[f'{address}'] == Modes.paused and command != 'resume':
            sock.sendto('The server is paused, to enable it, enter "resume"'.encode('utf-8'), address)
            continue
        elif command == 'resume':
            modes_list[f'{address}'] = Modes.ready
            sock.sendto('The server is started'.encode('utf-8'), address)
        elif command == 'pause':
            modes_list[f'{address}'] = Modes.paused
            sock.sendto('The server is paused'.encode('utf-8'), address)
        elif command == 'exit':
            sock.sendto('The connection was broken'.encode('utf-8'), address)
            break
        else:
            sock.sendto('Wrong command, available - resume, pause, exit'.encode('utf-8'), address)


if __name__ == '__main__':
    main()

