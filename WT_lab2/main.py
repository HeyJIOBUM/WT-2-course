from http.server import BaseHTTPRequestHandler
from http.server import HTTPServer
from io import BytesIO
import mimetypes
import os


class HttpHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        server_files = os.listdir("server")
        req = self.requestline.split(' ')[1][1:]
        if not req:
            req = 'index.html'
        if req in server_files:
            self.send_response(200)
            # for tpl in self.headers.items():
            #     self.send_header(tpl[0], tpl[1])
            #     print(tpl[0], tpl[1])
            self.send_header("Content-length", f'{os.path.getsize(os.path.join("server", req))}')
            self.send_header("Content-type", f'{mimetypes.types_map["."+(req.split(".")[-1])]}')
            self.end_headers()
            file = open(os.path.join("server", req), 'rb')
            self.wfile.write(file.read())
            file.close()
        else:
            self.log_err(404)

    def do_POST(self):
        server_files = os.listdir("server")
        req = self.requestline.split(' ')[1][1:]
        if not req:
            req = 'index.html'
        if req in server_files:
            content_length = int(self.headers['Content-Length'])
            content_type = self.headers['Content-Type']
            body = self.rfile.read(content_length)
            self.send_response(200)
            self.send_header('Content-length', f'{content_length + 32}')
            self.send_header('Content-type', content_type)
            self.end_headers()
            response = BytesIO()
            response.write(b'This is POST request. ')
            response.write(b'Received: ')
            response.write(body)
            self.wfile.write(response.getvalue())
        else:
            self.log_err(404)

    def do_HEAD(self):
        server_files = os.listdir("server")
        req = self.requestline.split(' ')[1][1:]
        if not req:
            req = 'index.html'
        if req in server_files:
            self.send_response(200)
            self.send_header("Content-length", f'{os.path.getsize(os.path.join("server", req))}')
            self.send_header("Content-type", f'{mimetypes.types_map["." + (req.split(".")[-1])]}')
            self.end_headers()
        else:
            self.log_err(404)

    def log_err(self, err_code):
        file = open('log.txt', 'a')
        file.write(f'Method - {self.command}, error - {err_code}, client - {self.client_address}\n')
        file.close()
        self.send_error(404)


def run(server_class=HTTPServer, handler_class=BaseHTTPRequestHandler):
    server_address = ('', 8000)
    httpd = server_class(server_address, handler_class)
    httpd.serve_forever()


if __name__ == '__main__':
    mimetypes.init()
    run(handler_class=HttpHandler)
