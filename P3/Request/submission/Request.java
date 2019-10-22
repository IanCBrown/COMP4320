import java.util.Arrays; 

public class Request {

   public byte tml; 
   public byte request_id; 
   public byte op_code; 
   public byte num_of_operands; 
   public short operand_1;
   public short operand_2;
   public short[] operands;
    

  public Request(byte tml, byte request_id, byte op_code, byte num_of_operands, short[] operands)  {
      this.tml = tml; 
      this.request_id = request_id; 
      this.op_code = op_code;
      this.num_of_operands = num_of_operands;
      this.operand_1 = operands[0]; 
      if (num_of_operands == 2){
          this.operand_2 = operands[1];
      }
  }

  public String toString() {
    StringBuilder retValue = new StringBuilder();
    retValue.append(Byte.toString(tml));
    retValue.append(Byte.toString(request_id));
    retValue.append(Byte.toString(op_code));
    retValue.append(Byte.toString(num_of_operands));
    retValue.append(Arrays.toString(operands));
    return retValue.toString(); 
  }
}
