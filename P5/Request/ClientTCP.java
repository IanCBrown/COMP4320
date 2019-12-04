import java.io.*; // for Input/OutputStream
import java.net.*; // for Socket
import java.util.*; 

// client
public class ClientTCP {
    public static void main(String args[]) throws Exception {

        if (args.length != 2) // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Destination> <Port>");

        InetAddress destAddr = InetAddress.getByName(args[0]); // Destination address
        int destPort = Integer.parseInt(args[1]); // Destination port

        Socket sock = new Socket(destAddr, destPort);

        Scanner sin = new Scanner(System.in);
        RequestEncoder encoder = (args.length == 2 ? new RequestEncoderBin(args[1]) : new RequestEncoderBin());
        int quit = 0; 
        byte count = 1;

        long start = System.currentTimeMillis();
       

        // Business logic
        while (quit == 0) {
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

            // encode response
            try {
                byte[] codedRequest = encoder.encode(req); // Encode friend
                // debugging purposes
                System.out.println(bytesToHex(codedRequest));

                OutputStream out = sock.getOutputStream();
                out.write(encoder.encode(req));
            } catch (Exception e) {
                e.printStackTrace();
            }

            DataInputStream in = new DataInputStream(sock.getInputStream()); 
            long end = System.currentTimeMillis();
            byte new_tml = in.readByte();
            byte request_id = in.readByte();
            byte err = in.readByte(); 
            int answer = in.readInt();

            System.out.println("Request ID: " + request_id);
            System.out.println("Answer: " + answer);

            System.out.println("The request took " + Long.toString(end - start) + " milliseconds.");

            System.out.println("Press 0 to continue and 1 to quit: ");
            if (sin.nextInt() == 1) {
                quit = 1;
            } 
        }
        sock.close();
    }


        // https://www.mkyong.com/java/java-how-to-convert-bytes-to-hex/
        // helper function 
        private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        sb.append("["); 
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            if (i == bytes.length - 1) {
                break; 
            }
            sb.append(", ");
        }
        sb.append("]"); 
        return sb.toString();
    }
}
