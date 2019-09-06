import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream
import java.util.Scanner;

public class myFirstTCPClient {

  public static void main(String[] args) throws IOException {

    if ((args.length < 2) || (args.length > 3))  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

    String server = args[0];       // Server name or IP address

    int servPort = Integer.parseInt(args[1]);

    String userSentence = ""; 

    while (true)
    { 
      System.out.println("Use ctrl + c to exit the application"); 
      System.out.print("Enter a sentence to send to the server: ");

      Scanner scnr = new Scanner(System.in); 
      userSentence = scnr.nextLine(); 

      // Convert input String to bytes using the default character encoding
      byte[] byteBuffer = userSentence.getBytes(); 
  
      // Create socket that is connected to server on specified port
      Socket socket = new Socket(server, servPort);
      System.out.println("Connected to server...sending echo string");
  
      InputStream in = socket.getInputStream();
      OutputStream out = socket.getOutputStream();
  
      // Start timer 
      long startTime = System.nanoTime(); 
      out.write(byteBuffer);  // Send the encoded string to the server
  
      // Receive the same string back from the server
      int totalBytesRcvd = 0;  // Total bytes received so far
      int bytesRcvd;           // Bytes received in last read
      while (totalBytesRcvd < byteBuffer.length) {
        if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd,  
                          byteBuffer.length - totalBytesRcvd)) == -1)
          throw new SocketException("Connection close prematurely");
        totalBytesRcvd += bytesRcvd;
      }
  
      System.out.println("Received: " + new String(byteBuffer));
  
      // end timer 
      long endTime = System.nanoTime(); 
      System.out.println("The response took " + Long.toString(endTime - startTime) + " Nanoseconds"); 
  
      socket.close();  // Close the socket and its streams
    }
  }
}
