import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class RequestEncoderBin implements RequestEncoder, RequestBinConst {

  private String encoding;  // Character encoding

  public RequestEncoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public RequestEncoderBin(String encoding) {
    this.encoding = encoding;
  }

  public byte[] encode(Request req) throws Exception {

    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);
    out.writeByte(req.tml);
    out.writeByte(req.request_id);
    out.writeByte(req.op_code);
    out.writeByte(req.num_of_operands);
    out.writeShort(req.operand_1);
    if (req.num_of_operands == 2){
        out.writeShort(req.operand_2);
    }

    out.flush();
    return buf.toByteArray();
  }
}
