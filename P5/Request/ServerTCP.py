import socket
import time
import sys
import struct

# Run with python 2.7

TCP_IP = "127.0.0.1"

# default port number
if len(sys.argv) < 2:
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
            if data:
                data = list(data)
                new_len = data[0]
                req_id = data[1]
                op_code = struct.unpack("b", data[2])[0]
                op_num = struct.unpack("b", data[3])[0]
                arg_1 = data[4:6]
                arg_1 = b''.join(arg_1)
                arg_1 = struct.unpack(">h", arg_1)[0]
                if (op_num == 2):
                    arg_2 = data[6:]                                                  
                    arg_2 = b''.join(arg_2)
                    arg_2 = struct.unpack(">h", arg_2)[0]           
                ans = 0
                
                err = 0 
                if new_len >= 7 or new_len <= 8:
                    # do math here
                    if op_code == 1:
                        ans = arg_1 + arg_2
                    if op_code == 2:
                        ans = arg_1 - arg_2
                    if op_code == 3:
                        ans = arg_1 * arg_2
                    if op_code == 4:
                        ans = arg_1 / arg_2
                    if op_code == 5:
                        ans = arg_1 >> arg_2
                    if op_code == 6:
                        ans = arg_1 << arg_2
                    if op_code == 7:
                        ans = ~arg_1
                else:
                    err = 127

                # encode and send response here
                ans = struct.pack(">i", ans)
                # ans = bytes(ans)
                transport = bytearray([new_len, req_id, err])
                transport.extend(ans)
                print 'Response to client'
                print(list(map(hex, list(transport))))

                connection.sendall(transport)
    
    finally:
        connection.close()
