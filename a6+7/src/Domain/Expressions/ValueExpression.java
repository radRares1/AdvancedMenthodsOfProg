package Domain.Expressions;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.Type;
import Domain.Values.Value;

public class ValueExpression implements Expression {
    private Value value;

    public ValueExpression(Value initValue)
    {
        value = initValue;
    }

    public Value evaluate(MyIDictionary<String,Value> table, MyHeapInterface<Integer, Value> heap) throws MyException
    {
        return value;
    }

    public String toString()
    {
        return value.toString();
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        return value.getType();
    }
}
