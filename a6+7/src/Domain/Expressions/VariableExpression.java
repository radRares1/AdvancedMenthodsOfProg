package Domain.Expressions;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.Type;
import Domain.Values.Value;

public class VariableExpression implements Expression {
    String id;
    public VariableExpression(String initId)
    {
        id=initId;
    }

    public Value evaluate(MyIDictionary<String, Value> table, MyHeapInterface<Integer, Value> heap) throws MyException, MyException {
        return table.lookUp(id);
    }
    public String toString()
    {
        return id.toString();
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        return typeEnviornment.lookUp(id);
    }
}
