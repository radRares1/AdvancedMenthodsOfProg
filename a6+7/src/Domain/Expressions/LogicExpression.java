package Domain.Expressions;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.BoolType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.Value;

public class LogicExpression implements Expression {
    private Expression e1,e2;
    private String operator;

    public LogicExpression(Expression init1, Expression init2, String operator)
    {
        e1=init1;
        e2=init2;
        this.operator=operator;
    }

    @Override
    public String toString() {
        return e1.toString() + operator + e2.toString();
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyHeapInterface<Integer, Value> heap) throws MyException {
        BoolValue first = new BoolValue(false);
        BoolValue second = new BoolValue(false);

        first = (BoolValue) e1.evaluate(table,heap);
        second = (BoolValue) e2.evaluate(table, heap);
        boolean result;

        switch(operator)
        {
            case "AND":
                result = first.getValue() && second.getValue();
                break;
            case "OR":
                result = first.getValue() || second.getValue();
                break;
            default:
                throw new MyException("Invalid operator!\n");
        }

        return new BoolValue(result);

    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {

        Type t1,t2;
        t1 = e1.typeCheck(typeEnviornment);
        t2 = e2.typeCheck(typeEnviornment);

        if(t1.equals(new BoolType()))
            if(t2.equals((new BoolType())))
                return new BoolType();
            else
                throw new MyException("second operand not an boolean");
        else
            throw new MyException("first operand not an boolean");
    }
}
