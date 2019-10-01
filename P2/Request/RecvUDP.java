import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class RecvUDP {

  public static void main(String[] args) throws Exception {

      if (args.length != 1 && args.length != 2)  // Test for correct # of args        
	  throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");
      
      int port = Integer.parseInt(args[0]);   // Receiving Port
      
      DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving      
      DatagramPacket packet = new DatagramPacket(new byte[1024],1024);
      sock.receive(packet);
      
      // Receive binary-encoded friend                              
      // FriendDecoder decoder = new FriendDecoderBin();
      RequestDecoder decoder = (args.length == 2 ?   // Which encoding              
				  new RequestDecoderBin(args[1]) :
				  new RequestDecoderBin() );


      Request receivedRequest = decoder.decode(packet);

      System.out.println("Received Binary-Encoded Request");
      System.out.println(receivedRequest);
      
      
      
      sock.close();
  }
}
