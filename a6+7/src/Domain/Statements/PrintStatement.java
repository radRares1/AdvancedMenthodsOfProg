package Domain.Statements;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Expressions.Expression;
import Domain.State.ProgramState;
import Domain.Types.Type;
import Domain.Values.Value;

public class PrintStatement implements InterfaceStatement {
    Expression expression;

    public PrintStatement(Expression initExpression)
    {
        expression = initExpression;
    }

    public String toString()
    {
        return  "print(" + expression.toString() + ")";
    }

     public ProgramState execute(ProgramState state) throws MyException
    {

        MyHeapInterface<Integer,Value> heap = state.getHeap();
        Value i = expression.evaluate(state.getSymbolTable(),heap);
        state.addOut(i);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        expression.typeCheck(typeEnviornment);
        return typeEnviornment;
    }
}
