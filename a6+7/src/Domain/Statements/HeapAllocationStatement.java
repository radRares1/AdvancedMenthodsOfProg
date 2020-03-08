package Domain.Statements;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Expressions.Expression;
import Domain.FreePosition;
import Domain.State.ProgramState;
import Domain.Types.RefType;
import Domain.Types.Type;
import Domain.Values.RefValue;
import Domain.Values.Value;

public class HeapAllocationStatement implements InterfaceStatement {
    private String varName;
    private Expression exp;
    private FreePosition firstFree;

    public HeapAllocationStatement(String initName, Expression initExp,FreePosition initPosition)
    {
        varName = initName;
        exp = initExp;
        firstFree = initPosition;
    }

    public String toString()
    {
        return  "Allocated : ( " + varName + ", " + exp.toString() +" )\n";

    }
    public ProgramState execute(ProgramState state) throws MyException
    {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyHeapInterface<Integer,Value> heap = state.getHeap();
        if(symbolTable.isDefined(varName))
        {
            Value value = symbolTable.lookUp((varName));
            if(value.getType()instanceof RefType)
            {
                RefValue refVal = (RefValue) value;
                RefType refType = (RefType) refVal.getType();
                Value eval = exp.evaluate(symbolTable,heap);
                if(refType.getInner().equals(eval.getType()))
                {
                    int free = firstFree.getPosition();
                    heap.add(free,eval);
                    symbolTable.update(varName,new RefValue(free,refType.getInner()));
                    return null;
                }
                else
                    throw new  MyException("types don't match\n");
            }
            else
                throw new MyException("not a ref type\n");
        }

        else
            throw new MyException("not defined in the symTable\n");

    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException
    {
        Type typeVar = typeEnviornment.lookUp(varName);
        Type typeExp = exp.typeCheck(typeEnviornment);

        if(typeVar.equals(new RefType(typeExp)))
            return typeEnviornment;
        else
            throw new MyException("Heap allocation Statement: right side and left side have different types\n");


    }
}
