import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream
import java.util.Arrays;

public class myFirstTCPServer {

  private static final int BUFSIZE = 32;   // Size of receive buffer

  public static void main(String[] args) throws IOException {

    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    int recvMsgSize;   // Size of received message
    byte[] byteBuffer = new byte[BUFSIZE];  // Receive buffer

    for (;;) { // Run forever, accepting and servicing connections
      Socket clntSock = servSock.accept();     // Get client connection

      System.out.println("Handling client at " +
        clntSock.getInetAddress().getHostAddress() + " on port " +
             clntSock.getPort());

      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      // Receive until client closes connection, indicated by -1 return
      while ((recvMsgSize = in.read(byteBuffer)) != -1) {
        StringBuilder sb = new StringBuilder(new String(byteBuffer));
        String reversed = sb.reverse().toString().trim(); 
        byteBuffer = reversed.getBytes(); 
        System.out.println(reversed);
        out.write(byteBuffer,0, recvMsgSize); 
      }
      
      byteBuffer = new byte[BUFSIZE]; 
      clntSock.close();  // Close the socket.  We are done with this client!
    }
    /* NOT REACHED */
  }
}
