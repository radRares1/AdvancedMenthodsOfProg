package Domain.State;

import Domain.CustomDataTypes.*;
import Domain.Exceptions.MyException;
import Domain.Statements.InterfaceStatement;
import Domain.Values.StringValue;
import Domain.Values.Value;

import java.io.BufferedReader;

public class ProgramState {
    private MyInterfaceStack<InterfaceStatement> executionStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> out;
    private InterfaceStatement program;
    private MyFileTable<StringValue, BufferedReader> fileTable;
    private MyHeapInterface<Integer,Value> heap;
    private static int id=0;
    private int currentId;



    public ProgramState(MyInterfaceStack<InterfaceStatement> executionStack, MyIDictionary<String, Value> symbolTable, MyIList<Value> out, InterfaceStatement program, MyFileTable<StringValue,BufferedReader> initFileTable, MyHeapInterface<Integer, Value> initHeap) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.program = program;
        this.executionStack.push(program);
        this.fileTable = initFileTable;
        this.heap = initHeap;
        currentId = incId();
    }

    public ProgramState(InterfaceStatement initialState)
    {
        this.executionStack = new MyStack<InterfaceStatement>();
        this.symbolTable = new MyDictionary<String,Value>();
        this.out = new MyList<Value>();
        this.fileTable = new MyFileTable<StringValue,BufferedReader>();
        this.heap = new MyHeap<Integer,Value>();
        this.program = initialState;
        this.executionStack.push(program);
        this.currentId = incId();
    }



    public static synchronized int incId()
    {
        id++;
        return id-1;
    }

    public int getId()
    {
        return currentId;
    }

    @Override
    public String toString()
    {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("Execution Stack: \n" );
        toPrint.append(executionStack.toString() + "\n");
        toPrint.append("Symbol Table: \n" );
        toPrint.append(symbolTable.toString() + "\n");
        toPrint.append("Out: \n" );
        toPrint.append(out.toString() + "\n");
        toPrint.append("FileTable: \n" );
        toPrint.append(fileTable.toString() + "\n");
        toPrint.append("Heap: \n" );
        toPrint.append(heap.toString() + "\n");


        return toPrint.toString();


        //return repoToString;
    }

    public MyInterfaceStack<InterfaceStatement> getStack()
    {
        return this.executionStack;
    }

    public MyIDictionary<String,Value> getSymbolTable()
    {
        return this.symbolTable;
    }

    public MyIList<Value> getOut()
    {
        return this.out;
    }

    public MyFileTable<StringValue, BufferedReader> getFileTable()
    {
        return this.fileTable;
    }

    public void addOut(Value value) {
        out.add(value);
    }

    public MyHeapInterface<Integer,Value> getHeap()
    {
        return this.heap;
    }

    public Boolean isNotCompleted()
    {
       if(!executionStack.isEmpty())
           return true;
       else
           return false;
    }

    public ProgramState oneStep() throws MyException
    {
        if(executionStack.isEmpty())
            throw new MyException("Program Stack is empty");

        InterfaceStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);

    }




}

