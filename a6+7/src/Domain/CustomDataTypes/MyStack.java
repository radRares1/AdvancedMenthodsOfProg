package Domain.CustomDataTypes;

import Domain.Exceptions.MyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class MyStack<T> implements  MyInterfaceStack<T>{
    private Stack<T> stack;

    public MyStack()
    {
        stack = new Stack<T>();
    }

    public List<T> getValues() throws MyException
    {
       List<T> values = new ArrayList<>();
       values = stack.stream().collect(Collectors.toList());
       return values;
    }

    public T pop() throws MyException
    {
        try
        {
            return stack.pop();
        }
        catch(Exception e)
        {
            throw new MyException("Empty stack");
        }
    }

    public void push(T v)
    {
        stack.push(v);
    }

    public boolean isEmpty()
    {
        if(stack.isEmpty())
            return true;
        else
            return false;
    }

    public int size()
    {
        return stack.size();
    }

    public String toString()
    {
        StringBuilder toPrint = new StringBuilder();
        for(T element :stack)
            toPrint.append((element.toString()+"\n"));

        return toPrint.toString();
    }
}
