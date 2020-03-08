package Domain.CustomDataTypes;

import Domain.Exceptions.MyException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyHeap<T1,T2> implements MyHeapInterface<T1,T2> {
    private HashMap<T1,T2> heap;
    public MyHeap()
    {
        heap = new  HashMap<T1,T2>();
    }

    public void add(T1 key, T2 value) throws MyException
    {
        try
        {
            heap.put(key,value);
        }
        catch(Exception e)
        {
            throw new MyException("Error, addition of null value/s");
        }
    }

    public void update(T1 key, T2 value) throws MyException
    {
        try
        {
            heap.replace(key,value);
        }
        catch(Exception e)
        {
            throw new MyException("Key does not exist");
        }
    }

    public T2 remove(T1 key) throws MyException
    {
        try
        {
            return heap.remove(key);
        }
        catch(Exception e)
        {
            throw new MyException("Key does not exist");
        }
    }

    public boolean isEmpty()
    {
        return heap.isEmpty();
    }

    public String toString()
    {
        StringBuilder toPrint = new StringBuilder();
        Iterator it = heap.entrySet().iterator();

        while(it.hasNext())
        {
            Map.Entry touple =(Map.Entry)it.next();
            toPrint.append(("address: ")+ touple.getKey() +" value:" + touple.getValue() + "\n");
        }
        return  toPrint.toString();
    }

    public T2 lookUp(T1 key) throws MyException
    {
        try
        {
            return heap.get(key);
        }
        catch(Exception e)
        {
            throw new MyException("Key does not exist");
        }
    }

    public boolean isDefined(T1 key)
    {
        if(heap.containsKey(key))
            return true;
        else
            return false;
    }

    public  Map<T1,T2> getContent()
    {
        return this.heap;
    }

    public void setContent(Map<T1,T2> map)
    {
        this.heap=(HashMap<T1,T2>) map;
    }
}
