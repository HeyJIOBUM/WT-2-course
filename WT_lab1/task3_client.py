import socket
import time


def main():
    # host = input("Enter webserver hostname: ")
    # port = int(input("Enter webserver port: ") or 8080)
    host = 'localhost'
    port = 8080
    sock = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
    sock.sendto('Hello server!!!'.encode('utf-8'), (host, port))
    start = time.time_ns()
    while True:
        msg_addr = sock.recvfrom(1024)
        message = msg_addr[0].decode('utf-8')
        print(message)
        print("Command processing time: ", time.time_ns() - start, " ns")
        command = input("Enter command: ")
        start = time.time_ns()
        sock.sendto(command.encode('utf-8'), (host, port))


if __name__ == '__main__':
    main()
