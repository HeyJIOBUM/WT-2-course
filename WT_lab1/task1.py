import socket
import telnetlib
import paramiko


def main():
    host = input('Enter website host: ') or 'batmud.bat.org'
    try:
        host = socket.gethostbyname(host)
    except socket.error:
        print('Wrong host name')
        exit()
    port = 0
    while port not in [22, 23, 80, 443]:
        port = int(input('Enter website port (22-SSH, 23-Telnet, 80-http, 443-https): ') or 23)
    if port == 22:
        client = paramiko.SSHClient()
        client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        client.connect(host, port=port)
        ssh_session = client.get_transport().open_session()
        if ssh_session.active:
            ssh_session.send(bytes("HEAD / HTTP/1.1\r\n" + "Host: " + host + "\r\n" + "Connection: close\r\n\r\n", encoding='utf-8'))
        print(ssh_session.recv(1024).decode())
    # elif port == 23:
    #     telnet = telnetlib.Telnet(host)
    #     telnet.write(b'hello world!')
    #     print((telnet.read_until(b'at').decode('utf-8')))
    elif port in [80, 443, 23]:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            sock.connect((host, port))
        except socket.timeout:
            print('Timeout error')
        except socket.error:
            print('This site or port does not available')
            exit()
        sock.send((bytes("HEAD / HTTP/1.1\r\n" + "Host: " + host + "\r\n" + "Connection: close\r\n\r\n", encoding='utf-8')))
        data = sock.recv(1024).decode('utf-8')
        sock.close()
        print(data)


if __name__ == '__main__':
    main()
