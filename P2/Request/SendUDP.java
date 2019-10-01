import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner;
import java.util.Arrays; 


public class SendUDP {

  public static void main(String args[]) throws IOException {

      if (args.length != 2 && args.length != 3)  // Test for correct # of args        
	  throw new IllegalArgumentException("Parameter(s): <Destination>" +
					     " <Port> [<encoding]");
      
      
      InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
      int destPort = Integer.parseInt(args[1]);               // Destination port
      
      
      
      DatagramSocket sock = new DatagramSocket(); // UDP socket for sending
      int quit = 0;
      Scanner sin = new Scanner(System.in);
      RequestEncoder encoder = (args.length == 3 ?
                                    new RequestEncoderBin(args[2]) :
                                    new RequestEncoderBin());
	  DatagramPacket packet = new DatagramPacket(new byte[1024],1024);
      int count = 1;
      while(quit == 0){
        // Use the encoding scheme given on the command line (args[2])
        
        int op_in;
        System.out.println("Enter the operation you'd like to perform\n 1: addition"
                + "\n 2: subtraction"
                + "\n 3: multiplication"
                + "\n 4: division"
                + "\n 5: shift right"
                + "\n 6: shift left"
                + "\n 7: one's complement");
        op_in = sin.nextInt();
        int[] operands;
        if (op_in < 7){
            int oper_1;
            int oper_2;
            System.out.print("Enter first operand: ");
            oper_1 = sin.nextInt();
            System.out.print("\nEnter 2nd operand (shift distance): ");
            oper_2 = sin.nextInt();
            operands[0] = oper_1;
            operands[1] = oper_2;
            Request req = new Request(8,count++,op_in,2,operands);
        }
        if (op_in == 7){
            int oper_1;
            System.out.print("Enter operand: ");
            oper_1 = sin.nextInt();
            Request req = new Request(6,count++,op_in,1,operands);
        }
        else{
            throw new IOException();
        }
       
        
        byte[] codedRequest = encoder.encode(req); // Encode friend
        //debugging purposes
        System.out.println(Arrays.toString(codedRequest));
        
        DatagramPacket message = new DatagramPacket(codedRequest, codedRequest.length, 
                                                    destAddr, destPort);
        sock.send(message);
        
		sock.receive(packet);
		ByteArrayInputStream payload =
		new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
		DataInputStream src = new DataInputStream(payload);
		int tml             = src.readInt();
		int request_id		= src.readInt();
		int answer	        = src.readInt();
		System.out.println("Request ID: " + request_id);
		System.out.println("Answer: " + answer);

		

        System.out.println("Press 0 to continue and 1 to quit: ");
        if (sin.nextInt() == 1){
			quit = 1;
		}
      }
        sock.close();
  
}
