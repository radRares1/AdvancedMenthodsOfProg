package Domain.Statements;
import Domain.CustomDataTypes.MyDictionary;
import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.CustomDataTypes.MyInterfaceStack;
import Domain.Exceptions.MyException;
import Domain.Expressions.Expression;
import Domain.State.ProgramState;
import Domain.Types.BoolType;
import Domain.Types.Type;
import Domain.Values.Value;

public class IfStatement implements InterfaceStatement {
    Expression exp;
    InterfaceStatement thenS;
    InterfaceStatement elseS;

    public IfStatement(Expression expInit, InterfaceStatement thenInit, InterfaceStatement elseInit)
    {
        exp = expInit;
        thenS = thenInit;
        elseS = elseInit;
    }
    public String toString()
    {
        return "IF("+ exp.toString() +") THEN(" +thenS.toString() +")ELSE(" + elseS.toString()+ "))";
    }

    public ProgramState execute(ProgramState state) throws MyException
    {
        MyInterfaceStack<InterfaceStatement> stack = state.getStack();
        MyIDictionary<String,Value> symbolTable = state.getSymbolTable();

        MyHeapInterface<Integer,Value> heap = state.getHeap();
        Value result =  exp.evaluate(symbolTable,heap);


        try {

            if (result.getValue().equals(false)) {
                stack.push(elseS);
            } else {
                stack.push(thenS);
            }
            return null;
        }
        catch(Exception e)
        {
            throw new MyException("Statements are not bool type");
        }
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        Type typeExp = exp.typeCheck(typeEnviornment);

        if(typeExp.equals(new BoolType()))
        {
            MyIDictionary<String,Type> clone = new MyDictionary<String, Type>();
            clone.setContent(typeEnviornment.getContent());
            thenS.typeCheck(clone);
            elseS.typeCheck(clone);

            return typeEnviornment;
        }
        else
            throw new MyException("The condition of IF does not have bool type\n");

    }
}

