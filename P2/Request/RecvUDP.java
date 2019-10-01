import java.net.*; // for DatagramSocket and DatagramPacket
import java.io.*; // for IOException

public class RecvUDP {

	public static void main(String[] args) throws Exception {

		if (args.length != 1 && args.length != 2) // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

		int port = 10012; // Receiving Port
		// changed this to our group number

		DatagramSocket sock = new DatagramSocket(port); // UDP socket for receiving
		DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
		while (true) {
			sock.receive(packet);

			// Receive binary-encoded friend
			// FriendDecoder decoder = new FriendDecoderBin();
			RequestDecoder decoder = (args.length == 2 ? // Which encoding
					new RequestDecoderBin(args[1]) : new RequestDecoderBin());

			int answer = 0;
			Request receivedRequest = decoder.decode(packet);
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
			// create exit packet
			byte tml = 7;
			byte request_id = receivedRequest.request_id;
			byte err = 0;
			byte[] reply;
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(buf);
			out.writeByte(tml);
			out.writeByte(request_id);
			out.writeByte(err);
			out.writeInt(answer);
			out.flush();
			reply = buf.toByteArray();
			DatagramPacket next_answer = new DatagramPacket(reply, reply.length, packet.getAddress(), packet.getPort());
			sock.send(next_answer);

			System.out.println("Received Binary-Encoded Request");
			System.out.println(receivedRequest);

		}
	}
}
