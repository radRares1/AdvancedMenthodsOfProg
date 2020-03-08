package Domain.Statements;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.State.ProgramState;
import Domain.Types.Type;

public interface InterfaceStatement {
     ProgramState execute(ProgramState state) throws MyException;
     String toString();
     MyIDictionary<String,Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException;
}
