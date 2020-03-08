package Domain.Statements;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.CustomDataTypes.MyInterfaceStack;
import Domain.Exceptions.MyException;
import Domain.Expressions.Expression;
import Domain.State.ProgramState;
import Domain.Types.Type;
import Domain.Values.Value;

public class AssignmentStatement implements InterfaceStatement {
    String id;
    Expression expression;

    public AssignmentStatement(String initId, Expression initExpression)
    {
        id=initId;
        expression = initExpression;
    }

    public String toString()
    {
        return  this.id +"=" + this.expression.toString();
    }
    public ProgramState execute(ProgramState state) throws MyException {

        MyInterfaceStack<InterfaceStatement> stack = state.getStack();
        MyIDictionary<String,Value> symbolTable = state.getSymbolTable();

        MyHeapInterface<Integer,Value> heap = state.getHeap();

        if(symbolTable.isDefined(id))
        {
            Value value = this.expression.evaluate(symbolTable,heap);
            Type typeId = (symbolTable.lookUp(id)).getType();
            if(value.getType().equals(typeId))
                symbolTable.update(this.id,value);
            else
                throw new MyException(" declared variable"+id+" and type of the assigned expression do not match");

        }
        else throw new MyException("the used variable" +id+ " was not declared before");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        Type typeVar = typeEnviornment.lookUp(id);
        Type typeExp= expression.typeCheck(typeEnviornment);

        if(typeVar.equals(typeExp))
            return typeEnviornment;
        else
            throw new MyException("Assignment: right side and left side have different types\n");

    }
}
