package Domain.Statements;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Expressions.Expression;
import Domain.State.ProgramState;
import Domain.Types.RefType;
import Domain.Types.Type;
import Domain.Values.RefValue;
import Domain.Values.Value;


public class HeapWritingStatement implements InterfaceStatement {

    private String varName;
    private Expression exp;

    public HeapWritingStatement(String InitVarName, Expression initExp) {
        varName = InitVarName;
        exp = initExp;
    }

    public String toString()
    {
        return  "In heap: (" + varName +" , "+ exp.toString() + ")";

    }

    public ProgramState execute(ProgramState state) throws MyException
    {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyHeapInterface<Integer,Value> heap = state.getHeap();

        if(!(symbolTable.isDefined(varName)))
        {
            throw new MyException("variable not defined in symtable\n");
        }

        Value value = symbolTable.lookUp(varName);

        if(!(value.getType() instanceof RefType))
            throw new MyException("not a reference type\n");

        RefValue refValue = (RefValue) value;

        if(!(heap.isDefined(refValue.getAddr())))
            throw new MyException("invalid address in heap\n");

        Value eval = exp.evaluate(symbolTable,heap);
        RefType refType=  (RefType) refValue.getType();

        if(!(refType.getInner().equals(eval.getType())))
        {
            throw new MyException("Types not equal\n");
        }

        heap.update(refValue.getAddr(),eval);
        return null;



    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        Type typeVar = typeEnviornment.lookUp(varName);
        Type typeExp = exp.typeCheck(typeEnviornment);

        if(typeVar.equals(new RefType(typeExp)))
            return typeEnviornment;
        else
            throw new MyException("Heap Writing Statement: right side and left side have different types\n");

    }
}
