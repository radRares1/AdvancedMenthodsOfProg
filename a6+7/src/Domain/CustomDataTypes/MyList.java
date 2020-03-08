package Domain.CustomDataTypes;

import Domain.Exceptions.MyException;
import Domain.Values.Value;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private LinkedList<T> list;

    public MyList() {
        list = new LinkedList<T>();
    }

    public void add(T element) {
        list.add(element);
    }

    public void add(int pos, T element) throws MyException {
        try {
            list.add(pos, element);
        } catch (Exception e) {
            throw new MyException("Position does not exist");
        }
    }

    public void remove(int pos) throws MyException {
        try {
            list.remove(pos);
        } catch (Exception e) {
            throw new MyException("Element is not in the list");
        }
    }

    public void remove(T element) throws MyException
    {
        try
        {
            list.remove(element);
        }
        catch(Exception e)
        {
            throw new MyException("Element does not exist");
        }
    }

    public T get(int pos) throws MyException
    {
        try
        {
            return this.list.get(pos);
        }
        catch(Exception e)
        {
            throw new MyException("Position does not exist");
        }
    }

    public int size()
    {
        return list.size();
    }

    public boolean isEmpty()
    {
        return (list.size()==0);
    }

    public String toString()
    {
        StringBuilder toPrint = new StringBuilder();

        for(T element :list)
            toPrint.append(element.toString() + "\n");

        return toPrint.toString();
    }


    @Override
    public ArrayList<T> getValues() {
        ArrayList<T> values = new ArrayList<>();
        for(T a :list)
        {
            values.add(a);
        }
        return values;
    }
}
