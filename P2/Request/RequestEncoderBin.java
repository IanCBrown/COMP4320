import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class RequestEncoderBin implements RequestEncoder, RequestBinConst {

  private String encoding;  // Character encoding

  public RequestBinConst() {
    encoding = DEFAULT_ENCODING;
  }

  public RequestBinConst(String encoding) {
    this.encoding = encoding;
  }

  public byte[] encode(Request req) throws Exception {

    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);
    out.writeInt(req.tml);
    out.writeInt(req.request_id);
    out.writeInt(req.op_code);
    out.writeInt(req.num_of_operands);
    out.write(req.operands);

    out.flush();
    return buf.toByteArray();
  }
}
