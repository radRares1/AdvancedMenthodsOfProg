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


public class CloseFileStatement implements InterfaceStatement {

    private Expression expression;

    public CloseFileStatement(Expression initExp)
    {
        expression=initExp;
    }

    public String toString()
    {
        return  "file " + expression.toString()+ " closed!\n";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        MyHeapInterface<Integer,Value> heap = state.getHeap();

        Value val = expression.evaluate(symbolTable,heap);

        if(val.getType().equals(new StringType()))
        {

            StringValue sVal = (StringValue)val;
            if(fileTable.isDefined(sVal))
            {
                BufferedReader buffer = fileTable.lookUp(sVal);
                try
                {
                    buffer.close();
                    fileTable.remove(sVal);
                    return null;
                }
                catch(Exception e)
                {
                    throw new MyException("file was not closed, error\n");
                }
            }
            else
                throw new MyException("fileName is not defined in the table\n");

        }
        else
            throw new MyException("Not a String Value\n");

    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {

        Type typeExp = expression.typeCheck(typeEnviornment);

        if(typeExp.equals(new StringType()))
            return typeEnviornment;
        else
            throw new MyException("FileName not a string");
    }
}
