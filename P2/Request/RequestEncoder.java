public interface RequestEncoder {
    byte[] encode(Request req) throws Exception;
  }
  