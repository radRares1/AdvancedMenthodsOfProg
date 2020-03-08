package Domain.Statements;

import Domain.CustomDataTypes.*;
import Domain.Exceptions.MyException;
import Domain.State.ProgramState;
import Domain.Types.Type;
import Domain.Values.StringValue;
import Domain.Values.Value;

import java.io.BufferedReader;
import java.util.Map;

public class ForkStatement implements InterfaceStatement {
    InterfaceStatement statement;

    public ForkStatement(InterfaceStatement initStatement)
    {
        statement = initStatement;
    }

    @Override
    public String toString() {
        return "fork( " + statement.toString() + " )\n";
    }

    public InterfaceStatement deepCopy()
    {
        return new ForkStatement(statement);
    }

    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyHeapInterface<Integer,Value> heap = state.getHeap();
        MyIList<Value> out = state.getOut();
        MyFileTable<StringValue, BufferedReader> fileTable = (MyFileTable<StringValue, BufferedReader>) state.getFileTable();
        MyDictionary<String,Value> newSymTable = new MyDictionary<String,Value>();



        for(Map.Entry<String,Value> entry: symbolTable.getContent().entrySet())
        {
            newSymTable.add(entry.getKey(),entry.getValue());
        }

        MyInterfaceStack<InterfaceStatement> newStack = new MyStack<InterfaceStatement>();
       //newStack.push(statement);

        ProgramState newState = new ProgramState(newStack,newSymTable,out,statement, fileTable,heap);

        return newState;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException
    {
        MyIDictionary<String,Type> clone = new MyDictionary<String, Type>();
        clone.setContent(typeEnviornment.getContent());

        statement.typeCheck(clone);

        return typeEnviornment;
    }
}
