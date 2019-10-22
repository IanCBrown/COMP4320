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
    byte tml             = src.readByte();
    byte request_id      = src.readByte();
    byte op_code         = src.readByte();
    byte num_of_operands = src.readByte(); 
    short operand_1       = src.readShort();
    short[] operands = new short[255];
    operands[0] = operand_1;
    if (num_of_operands == 2){
        short operand_2 = src.readShort();
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
