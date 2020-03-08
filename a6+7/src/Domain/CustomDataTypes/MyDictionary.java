package Domain.CustomDataTypes;

import Domain.Exceptions.MyException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyDictionary<T1,T2> implements MyIDictionary<T1,T2> {

    private HashMap<T1,T2> dictionary;

    public MyDictionary()
    {
        dictionary = new  HashMap<T1,T2>();
    }

    public void add(T1 key, T2 value) throws MyException
    {
        try
        {
            dictionary.put(key,value);
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
            dictionary.replace(key,value);
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
            return dictionary.remove(key);
        }
        catch(Exception e)
        {
            throw new MyException("Key does not exist");
        }
    }

    public boolean isEmpty()
    {
        return dictionary.isEmpty();
    }

    public String toString()
    {
        StringBuilder toPrint = new StringBuilder();
        Iterator it = dictionary.entrySet().iterator();

        while(it.hasNext())
        {
            Map.Entry touple =(Map.Entry)it.next();
            toPrint.append(("key: ")+ touple.getKey() +" value:" + touple.getValue() + "\n");
        }
        return  toPrint.toString();
    }

    public T2 lookUp(T1 key) throws MyException
    {
        try
        {
            return dictionary.get(key);
        }
        catch(Exception e)
        {
            throw new MyException("Key does not exist");
        }
    }

    public boolean isDefined(T1 key)
    {
        if(dictionary.containsKey(key))
            return true;
        else
            return false;
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }
    @Override
    public void setContent(Map<T1, T2> map) {
        dictionary= (HashMap<T1, T2>) map;
    }

}
