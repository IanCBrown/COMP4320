import java.io.*;   // for Input/OutputStream
import java.net.*;  // for Socket and ServerSocket

public class ServerTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 1 && args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

    int port = Integer.parseInt(args[0]);   // Receiving Port
	
    ServerSocket servSock = new ServerSocket(port);
    Socket clntSock = servSock.accept();
	while (true){
		// Receive binary-encoded request
		RequestDecoder decoder = (args.length == 2 ? // Which encoding
						new RequestDecoderBin(args[1]) : new RequestDecoderBin());
		Request receivedRequest = decoder.decode(clntSock.getInputStream());
		byte tml = 7;
		byte err = 0;
		byte[] reply;
		int answer = 0;
		System.out.println("Received Binary-Encoded Request");
		// 6 min length
		// 7 max length 
		if (receivedRequest.tml >= 7 || receivedRequest.tml <= 8) {				
			if (receivedRequest.op_code == 1) {
				answer = receivedRequest.operand_1 + receivedRequest.operand_2;
			}
			if (receivedRequest.op_code == 2) {
				answer = receivedRequest.operand_1 - receivedRequest.operand_2;
			}
			if (receivedRequest.op_code == 3) {
				answer = receivedRequest.operand_1 * receivedRequest.operand_2;
			}
			if (receivedRequest.op_code == 4) {
				answer = receivedRequest.operand_1 / receivedRequest.operand_2;
			}
			if (receivedRequest.op_code == 5) {
				answer = receivedRequest.operand_1 >> receivedRequest.operand_2;
			}
			if (receivedRequest.op_code == 6) {
				answer = receivedRequest.operand_1 << receivedRequest.operand_2;
			}
			if (receivedRequest.op_code == 7) {
				answer = ~receivedRequest.operand_1;
			}
		} else {
			err = 127; 
		}
		
		byte request_id = receivedRequest.request_id;
		// create exit packet
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(buf);
		out.writeByte(tml);
		out.writeByte(request_id);
		out.writeByte(err);
		out.writeInt(answer);
		out.flush();
		reply = buf.toByteArray();
		OutputStream out_reply = clntSock.getOutputStream(); // Get a handle onto Output Stream
		out_reply.write(reply); // send
	}
  }
}
