import socket
import time
import sys
import struct

# Resources:
# https://pymotw.com/2/socket/tcp.html

TCP_IP = "127.0.0.1"

# default port number
if sys.argv[1] == "":
    TCP_PORT = 10012
else:
    TCP_PORT = int(sys.argv[1])


class Request:
    def __init__(self, length=0, request_id=0, op_code=0, number_of_operands=0, operand_1=0, operand_2=0):
        self.length = length 
        self.request_id = request_id
        self.op_code = op_code  
        self.num_of_operands = number_of_operands 
        self.operand_1 = operand_1
        self.operand_2 = operand_2


sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.bind((TCP_IP, TCP_PORT))
sock.listen(1)
while True:
    connection, client_address = sock.accept()
    try:
        print("client connected: ", client_address)
        while True:
            data = connection.recv(1024)
            data = list(data)
    
            new_len = data[0]
            req_id = struct.unpack("b", data[1])[0]
            err = data[2]
            ans = data[3:]
            ans = b''.join(ans)
            ans = struct.unpack(">i", ans)[0]
            
            req = Request(new_len, req_id, er)

            if data:
                connection.sendall(data)
                print(data)
    
    finally:
        connection.close()
