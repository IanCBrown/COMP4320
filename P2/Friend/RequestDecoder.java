import java.io.*;   // for InputStream and IOException
import java.net.*;  // for DatagramPacket

public interface RequestDecoder {
  Friend decode(InputStream source) throws IOException;
  Friend decode(DatagramPacket packet) throws IOException;
}
