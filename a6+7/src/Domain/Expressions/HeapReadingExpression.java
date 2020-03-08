package Domain.Expressions;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.RefType;
import Domain.Types.Type;
import Domain.Values.RefValue;
import Domain.Values.Value;

public class HeapReadingExpression implements Expression
{
    private Expression exp;

    public HeapReadingExpression(Expression initExp)
    {
        exp = initExp;
    }

    public String toString()
    {
        return "Heap read: (" + exp.toString()  +")";
    }

    public Value evaluate(MyIDictionary<String,Value> table, MyHeapInterface<Integer,Value> heap) throws MyException
    {
        Value eval = exp.evaluate(table, heap);
        if(!(eval.getType() instanceof RefType))
        {
            throw new MyException("not a ref val\n");
        }

        RefValue refVal = (RefValue) eval;

        int addr = refVal.getAddr();

        if(!(heap.isDefined(addr)))
            throw new MyException("not in heap\n");

        return heap.lookUp(addr);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        Type t = exp.typeCheck(typeEnviornment);

        if(t instanceof RefType)
        {
            RefType reft = (RefType) t;
            return reft.getInner();
        }
        else
            throw new MyException("the rH arg is not a Reference Type");
    }
}
