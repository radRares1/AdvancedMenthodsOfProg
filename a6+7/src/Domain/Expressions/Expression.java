package Domain.Expressions;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.Type;
import Domain.Values.Value;

public interface Expression {
    public Value evaluate(MyIDictionary<String, Value> table, MyHeapInterface<Integer, Value> heap) throws MyException;
    public String toString();
    Type typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException;

}
