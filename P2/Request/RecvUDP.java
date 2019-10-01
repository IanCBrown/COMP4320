import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class RecvUDP {

  public static void main(String[] args) throws Exception {

      if (args.length != 1 && args.length != 2)  // Test for correct # of args        
	  throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");
      
      int port = Integer.parseInt(args[0]);   // Receiving Port
	  //need to change this to our group number 
      
      DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving      
      DatagramPacket packet = new DatagramPacket(new byte[1024],1024);
      sock.receive(packet);
      
      // Receive binary-encoded friend                              
      // FriendDecoder decoder = new FriendDecoderBin();
      RequestDecoder decoder = (args.length == 2 ?   // Which encoding              
				  new RequestDecoderBin(args[1]) :
				  new RequestDecoderBin() );

	int answer;
      Request receivedRequest = decoder.decode(packet);
	  if (recievedRequest.op_code == 1){
		answer = recievedRequest.operand_1 + receivedRequest.operand_2;
	  }
	  if (recievedRequest.op_code == 2){
		answer = recievedRequest.operand_1 - receivedRequest.operand_2;
	  }
	  if (recievedRequest.op_code == 3){
		answer = recievedRequest.operand_1 * receivedRequest.operand_2;
	  }
	  if (recievedRequest.op_code == 4){
		answer = recievedRequest.operand_1 / receivedRequest.operand_2;
	  }
	  if (recievedRequest.op_code == 5){
		answer = recievedRequest.operand_1 >> receivedRequest.operand_2;
	  }
	  if (recievedRequest.op_code == 6){
		answer = recievedRequest.operand_1 << receivedRequest.operand_2;
	  }
	  if (recievedRequest.op_code == 7){
		answer = ~recievedRequest.operand_1;
	  }
	  //create exit packet
	  int tml = 6;
	  int request_id = recievedrequest.request_id;

      System.out.println("Received Binary-Encoded Request");
      System.out.println(receivedRequest);
      
      
      
      sock.close();
  }
}
