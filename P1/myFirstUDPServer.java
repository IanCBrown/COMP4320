import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException

public class myFirstUDPServer {

  private static final int ECHOMAX = 128;  // Maximum size of echo datagram

  public static void main(String[] args) throws IOException {

    if (args.length != 1)  // Test for correct argument list
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    DatagramSocket socket = new DatagramSocket(servPort);
    DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

    StringBuilder sb = new StringBuilder(); 

    for (;;) {  // Run forever, receiving and echoing datagrams
      socket.receive(packet);     // Receive packet from client

      System.out.println("Handling client at " +
        packet.getAddress().getHostAddress() + " on port " + packet.getPort());

      sb = new StringBuilder(new String(packet.getData()).trim()); 
      String result = sb.reverse().toString(); 
      System.out.println(result); 
      packet.setData(result.getBytes());
      
      socket.send(packet);       // Send the same packet back to client
      packet.setData(new byte[ECHOMAX]);
      packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
    }
    /* NOT REACHED */
  }
}
