import java.net.*; // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*; // for IOException
import java.util.Scanner;
import java.util.Arrays;

public class SendUDP {

    public static void main(String args[]) throws IOException {

        if (args.length != 2 && args.length != 3) // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Destination>" + " <Port> [<encoding]");

        InetAddress destAddr = InetAddress.getByName(args[0]); // Destination address
        int destPort = Integer.parseInt(args[1]); // Destination port

        long start = System.currentTimeMillis(); 

        DatagramSocket sock = new DatagramSocket(); // UDP socket for sending
        int quit = 0;
        Scanner sin = new Scanner(System.in);
        RequestEncoder encoder = (args.length == 3 ? new RequestEncoderBin(args[2]) : new RequestEncoderBin());
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        byte count = 1;

        while (quit == 0) {
            // Use the encoding scheme given on the command line (args[2])

            byte op_in;
            System.out.println("Enter the operation you'd like to perform\n 1: addition" + "\n 2: subtraction"
                    + "\n 3: multiplication" + "\n 4: division" + "\n 5: shift right" + "\n 6: shift left"
                    + "\n 7: one's complement");
            op_in = sin.nextByte();
            short[] operands = new short[255];
            byte tml = 8;
            byte num_operands = 0; 
            Request req = new Request(tml, count, op_in, num_operands, operands);
            if (op_in < 7) {
                short oper_1;
                short oper_2;
                System.out.print("Enter first operand: ");
                oper_1 = sin.nextShort();
                System.out.print("\nEnter 2nd operand (shift distance): ");
                oper_2 = sin.nextShort();
                operands[0] = oper_1;
                operands[1] = oper_2;
                tml = 8; 
                num_operands = 2;
                req = new Request(tml, count++, op_in, num_operands, operands);
            }
            else if (op_in == 7) {
                short oper_1;
                System.out.print("Enter operand: ");
                oper_1 = sin.nextShort();
                operands[0] = oper_1;
                tml = 6; 
                num_operands = 1; 
                req = new Request(tml, count++, op_in, num_operands, operands);
            } else {
                System.out.println("INVALID INPUT");
                continue;
            }

            try {
                byte[] codedRequest = encoder.encode(req); // Encode friend
                // debugging purposes
                System.out.println(Arrays.toString(codedRequest));

                DatagramPacket message = new DatagramPacket(codedRequest, codedRequest.length, destAddr, destPort);
                sock.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

            sock.receive(packet);
            ByteArrayInputStream payload = new ByteArrayInputStream(packet.getData(), packet.getOffset(),
                    packet.getLength());
            DataInputStream src = new DataInputStream(payload);
            byte new_tml = src.readByte();
            byte request_id = src.readByte();
            byte err = src.readByte(); 
            int answer = src.readInt();
            System.out.println("Request ID: " + request_id);
            System.out.println("Answer: " + answer);

            long end = System.currentTimeMillis(); 
            System.out.println("The request took " + Long.toString(end - start) + " milliseconds.");

            System.out.println("Press 0 to continue and 1 to quit: ");
            if (sin.nextInt() == 1) {
                quit = 1;
            }  
        }
        sock.close();
    }
}
