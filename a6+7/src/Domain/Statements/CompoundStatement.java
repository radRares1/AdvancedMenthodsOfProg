package Domain.Statements;

import Domain.CustomDataTypes.MyIDictionary;
import Domain.CustomDataTypes.MyInterfaceStack;
import Domain.Exceptions.MyException;
import Domain.State.ProgramState;
import Domain.Types.Type;

public class CompoundStatement implements InterfaceStatement {
    InterfaceStatement first;
    InterfaceStatement second;

    public CompoundStatement(InterfaceStatement initFirst, InterfaceStatement initSecond)
    {
        first=initFirst;
        second=initSecond;
    }

    public String toString() {
        return  "(" + first.toString() + ";" + second.toString() + ")";

    }

    public ProgramState execute(ProgramState state) throws MyException {
        MyInterfaceStack<InterfaceStatement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnviornment));
    }
}

