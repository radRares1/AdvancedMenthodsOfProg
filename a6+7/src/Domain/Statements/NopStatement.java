package Domain.Statements;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.State.ProgramState;
import Domain.Types.Type;

public class NopStatement implements InterfaceStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        return null;
    }
    @Override
    public String toString() {
        return super.toString();
    }


}
