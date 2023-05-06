import socket
import time


def main():
    # host = input("Enter webserver hostname: ")
    # port = int(input("Enter webserver port: ") or 8080)
    host = 'localhost'
    port = 8000
    sock = socket.socket()
    sock.connect((host, port))
    start = time.time_ns()
    while True:
        message = sock.recv(1024).decode('utf-8')
        if not message or message == 'The connection was broken\n\r':
            print("Webserver broke the connection")
            return
        print(message, "Command processing time: ", time.time_ns() - start, " ns")
        # print(time.time_ns())
        command = (input("Enter command: ") + '\r\n')
        start = time.time_ns()
        # print(start)
        sock.send(command.encode('utf-8'))


if __name__ == '__main__':
    main()
