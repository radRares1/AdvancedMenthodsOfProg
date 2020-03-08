package Domain.Statements;

import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.State.ProgramState;
import Domain.Types.Type;
import Domain.Values.Value;

public class VarDeclarationStatement implements InterfaceStatement {
    String name;
    Type type;

    public VarDeclarationStatement(String initName,Type initType)
    {
        name=initName;
        type=initType;
    }

    public ProgramState execute(ProgramState state) throws MyException
    {
        MyIDictionary<String,Value> symbolTable = state.getSymbolTable();
        if(symbolTable.isDefined(name))
        {
            throw new MyException(("already exists"));
        }
        symbolTable.add(name,type.defaultValue());
        return null;
    }

    @Override
    public String toString() {
        return  name +" " + type.toString();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        typeEnviornment.add(name,type);
        return typeEnviornment;
    }
}
