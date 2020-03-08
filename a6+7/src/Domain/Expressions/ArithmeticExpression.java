package Domain.Expressions;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.IntValue;
import Domain.Values.Value;


public class ArithmeticExpression implements Expression {
    Expression e1,e2;
    int operation;


    public String toString()
    {
        String op = "";
        StringBuilder toPrint = new StringBuilder();
        if(operation == 1)
            op="+";
        else if(operation == 2)
            op = "-";
        else if(operation ==3)
            op="*";
        else
            op="/";
        toPrint.append(e1.toString() + op + e2.toString());

        return toPrint.toString();
    }

    public ArithmeticExpression(int initOp, Expression initFirst, Expression initSecond)
    {
        operation=initOp;
        e1=initFirst;
        e2=initSecond;
    }

    public Value evaluate(MyIDictionary<String,Value> table, MyHeapInterface<Integer, Value> heap) throws  MyException {
        Value v1,v2;
        v1 = e1.evaluate(table,heap);
        if(v1.getType().equals(new IntType()))
        {
            v2 = e2.evaluate(table,heap);
            if(v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if (operation == 1) return new IntValue(n1 + n2);
                if (operation == 2) return new IntValue(n1 - n2);
                if (operation == 3) return new IntValue(n1 * n2);
                if (operation == 4)
                    if (n2 == 0)
                        throw new MyException("division by zero");
                    else
                        return new IntValue(n1 / n2);
            }
            else
                throw new MyException("first operand is not an expresion");
            }
        return new IntValue(0);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {
        Type t1,t2;
        t1 = e1.typeCheck(typeEnviornment);
        t2 = e2.typeCheck(typeEnviornment);

        if(t1.equals(new IntType()))
            if(t2.equals((new IntType())))
            return new IntType();
            else
                throw new MyException("second operand not an integer");
        else
            throw new MyException("first operand not an integer ");
    }
}
