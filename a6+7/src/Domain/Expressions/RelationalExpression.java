package Domain.Expressions;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.Value;

public class RelationalExpression implements Expression {
    private Expression e1, e2;
    private String operation;

    public RelationalExpression(Expression initE1, Expression initE2, String initOp) {
        e1 = initE1;
        e2 = initE2;
        operation = initOp;
    }

    public String toString()
    {
        return e1.toString() + operation + e2.toString();
    }

    public Value evaluate(MyIDictionary<String, Value> table, MyHeapInterface<Integer, Value> heap) throws MyException {
        IntValue first = new IntValue(0);
        IntValue second = new IntValue(0);
        first = (IntValue) e1.evaluate(table,heap);
        second = (IntValue) e2.evaluate(table,heap);
        boolean result;

        switch(operation)
        {
            case "<" :
                result = first.getValue() < second.getValue();
                break;
            case "<=" :
                result = first.getValue() <= second.getValue();
                break;
            case ">" :
                result = first.getValue() > second.getValue();
                break;
            case ">=" :
                result = first.getValue() >= second.getValue();
                break;
            case "==" :
                result = first.getValue() == second.getValue();
                break;
            case "!=" :
                result = first.getValue() != second.getValue();
                break;
            default:
                throw new MyException("Invalid comparator\n");
        }

            return new BoolValue(result);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        Type t1,t2;
        t1 = e1.typeCheck(typeEnviornment);
        t2 = e2.typeCheck(typeEnviornment);

        if(t1.equals(new IntType()))
            if(t2.equals((new IntType())))
                return new BoolType();
            else
                throw new MyException("second operand not an integer");
        else
            throw new MyException("first operand not an integer ");
    }

}


