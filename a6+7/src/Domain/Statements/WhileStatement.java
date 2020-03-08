package Domain.Statements;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.CustomDataTypes.MyInterfaceStack;
import Domain.Exceptions.MyException;
import Domain.Expressions.Expression;
import Domain.State.ProgramState;
import Domain.Types.BoolType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.Value;

public class WhileStatement implements InterfaceStatement {

    private Expression exp;
    private InterfaceStatement statement;

    public WhileStatement(Expression initExp, InterfaceStatement initStatement)
    {
        exp = initExp;
        statement = initStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyHeapInterface<Integer,Value> heap = state.getHeap();
        MyInterfaceStack<InterfaceStatement> stack = state.getStack();

        Value value = exp.evaluate(symbolTable,heap);

        if(value.getType() instanceof BoolType)
        {
            BoolValue boolValue = (BoolValue) value;

            if(boolValue.getValue() == true)
            {

                stack.push(this);
                stack.push(statement);
                return null;
            }
        }
        else
            throw new MyException("Not a Bool Expression\n");

        return null;
    }

    public String toString()
    {
        return "while( " + exp.toString() + " ) " + statement.toString();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException
    {
        Type typeExp = exp.typeCheck(typeEnviornment);

        if(typeExp.equals(new BoolType()))
        {

           statement.typeCheck(typeEnviornment);

            return typeEnviornment;
        }
        else
            throw new MyException("The condition in while is not a boolean type\n");
    }
}
