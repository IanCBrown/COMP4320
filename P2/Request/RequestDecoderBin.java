import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class RequestDecoderBin implements RequestDecoder, RequestBinConst {

  private String encoding;  // Character encoding

  public RequestDecoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public RequestDecoderBin(String encoding) {
    this.encoding = encoding;
  }

  public Request decode(InputStream wire) throws IOException {
    DataInputStream src = new DataInputStream(wire);
    int tml             = src.readInt();
    int request_id      = src.readInt();
    int op_code         = src.readInt();
    int num_of_operands = src.readInt(); 
    int operand_1       = src.readInt();
    int[] operands;
    operands[0] = operand_1;
    if (num_of_operands == 2){
        int operand_2 = src.readInt();
        operands[1] = operand_2;
    }
   
    return new Request(tml, request_id, op_code, num_of_operands, operands);
  }

  public Request decode(DatagramPacket p) throws IOException {
    ByteArrayInputStream payload =
      new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    return decode(payload);
  }
}
