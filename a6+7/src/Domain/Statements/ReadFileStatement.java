package Domain.Statements;

import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.Exceptions.MyException;
import Domain.Expressions.Expression;
import Domain.State.ProgramState;
import Domain.Types.IntType;
import Domain.Types.StringType;
import Domain.Types.Type;
import Domain.Values.IntValue;
import Domain.Values.StringValue;
import Domain.Values.Value;

import java.io.BufferedReader;

public class ReadFileStatement implements InterfaceStatement {

    private Expression expression;
    private String variableName;

    public ReadFileStatement(Expression initExpression, String initName) {
        expression = initExpression;
        variableName = initName;
    }

    public String toString() {
        return  "readfile: (" + expression.toString() + ") ";
    }

    public ProgramState execute(ProgramState state) throws MyException {

        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        MyHeapInterface<Integer,Value> heap = state.getHeap();

        Value value = expression.evaluate(symbolTable,heap);

        if (symbolTable.isDefined(variableName))
        {
            if(symbolTable.lookUp(variableName).getType().equals(new IntType())) {
                if (value.getType().equals(new StringType())) {

                    StringValue val = (StringValue) value;
                    if (fileTable.isDefined(val)) {

                        try {
                            BufferedReader buffer = fileTable.lookUp(val);
                            String line = buffer.readLine();
                            IntValue ival;
                            if (line.equals("")) {
                                ival = new IntValue(0);
                            } else {
                                ival = new IntValue(Integer.parseInt(line));
                            }
                            symbolTable.update(variableName, ival);
                            return null;
                        } catch (Exception e) {
                            throw new MyException("Can't read, this error occured: " + e.toString());
                        }
                    } else
                        throw new MyException("file not defined!\n");
                } else
                    throw new MyException("Not a StringValue!\n");
            }
            else
                throw new MyException("Variable is not int type\n");
        } else
            throw new MyException("Variable not is symTable \n");

    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnviornment) throws MyException {

        Type typeExp = expression.typeCheck(typeEnviornment);

        if(typeExp.equals(new StringType()))
            return typeEnviornment;
        else
            throw new MyException("FileName not a string");
    }
}
