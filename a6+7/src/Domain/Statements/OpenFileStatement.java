package Domain.Statements;
import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Expressions.Expression;
import Domain.State.ProgramState;
import Domain.Types.StringType;
import Domain.Types.Type;
import Domain.Values.StringValue;
import Domain.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenFileStatement implements InterfaceStatement {

    private Expression exp;

    public OpenFileStatement(Expression initExp)
    {
        exp=initExp;
    }

    public String toString()
    {
        return  "The file is opened.\n";
    }

    public ProgramState execute(ProgramState state) throws MyException
    {
        MyIDictionary<StringValue,BufferedReader> fileTable = state.getFileTable();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        MyHeapInterface<Integer,Value> heap = state.getHeap();
        Value value = this.exp.evaluate(symbolTable,heap);

        if(value.getType().equals(new StringType()))
        {
            StringValue val = (StringValue) value;

            if(fileTable.isDefined(val))
                throw new MyException("File already exists\n");
            else
            {
                try
                {
                    BufferedReader buffer = new BufferedReader(new FileReader(val.getValue()));
                    fileTable.add(val,buffer);
                    return null;
                }
                catch(Exception e) {
                    throw new MyException("Error when trying to open the file: " + e.toString());
                }
            }

        }
        else
            throw new MyException("The expression is not a StringType\n");
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {

        Type typeExp = exp.typeCheck(typeEnviornment);

        if(typeExp.equals(new StringType()))
            return typeEnviornment;
        else
            throw new MyException("FileName not a string");
    }
}
