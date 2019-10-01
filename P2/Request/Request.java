import java.util.Arrays; 

public class Request {

   public int tml; 
   public int request_id; 
   public int op_code; 
   public int num_of_operands; 
   public byte[] operands; 
    

  public Request(int tml, int request_id, int op_code, int num_of_operands, byte[] operands)  {
      this.tml = tml; 
      this.request_id = request_id; 
      this.op_code = op_code;
      this.num_of_operands = num_of_operands;
      this.operands = operands; 
  }

  public String toString() {
    StringBuilder retValue = new StringBuilder();
    retValue.append(Integer.toString(tml));
    retValue.append(Integer.toString(request_id));
    retValue.append(Integer.toString(op_code));
    retValue.append(Integer.toString(num_of_operands));
    retValue.append(Arrays.toString(operands));
    return retValue.toString(); 
  }
}
