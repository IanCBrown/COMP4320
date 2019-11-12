import socket

UDP_IP = "127.0.0.1"
UDP_PORT = 10012

quit = 0 
count = 0

class Request:
    def __init__(self, length=0, request_id=0, op_code=0, number_of_operands=0, operand_1=0, operand_2=0):
        self.length = length 
        self.request_id = request_id
        self.op_code = op_code  
        self.num_of_operands = number_of_operands 
        self.operand_1 = operand_1
        self.operand_2 = operand_2

while quit == 0:
    print("Enter the operation you\'d like to perform")
    print("1: addition")
    print("2: subtraction")
    print("3: multiplication")
    print("4: division")
    print("5: shift right")
    print("6: shift left")
    print("7: one's complement")

    op_code = int(input())
    
    if op_code < 7:
        oper_1 = int(input("Enter first operand: "))
        oper_2 = int(input("Enter second operand: "))
        tml = 8
        num_operands = 2
        count += 1
        req = Request(tml, count, op_code, num_operands, oper_1, oper_2)
    elif op_code == 7:
        oper_1 = int(input("Enter operand: "))
        tml = 6
        num_operands = 1
        count += 1
        req = Request(tml, count, op_code, num_operands, oper_1)
    else:
        print("INVALID INPUT")
        continue

    sock = socket.socket(socket.AF_INET, # Internet
                        socket.SOCK_DGRAM) # UDP
    transport = bytearray([req.length, req.request_id, req.op_code, req.num_of_operands, req.operand_1, req.operand_2])
    sock.sendto(transport, (UDP_IP, UDP_PORT))

    print("Press 0 to continue and 1 to quit: ")
    quit = int(input())