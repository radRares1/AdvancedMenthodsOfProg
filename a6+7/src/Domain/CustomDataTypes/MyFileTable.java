package Domain.CustomDataTypes;

import Domain.Exceptions.MyException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyFileTable<T1,T2> implements MyIDictionary<T1,T2> {

    private HashMap<T1,T2> fileTable;

        public MyFileTable()
        {
            fileTable = new HashMap<T1,T2>();
        }

        public void add(T1 key,T2 value) throws MyException
        {
            try{
                fileTable.put(key,value);
            }
            catch(Exception e) {
                throw new MyException(e.toString());
            }
        }

    @Override
    public void update(T1 key, T2 value) throws MyException {
        try{
            fileTable.replace(key,value);
        }
        catch(Exception e)
        {
            throw new MyException("key does not exist");
        }
    }

    @Override
    public T2 remove(T1 key) throws MyException {
        try
        {
            return fileTable.remove(key);
        }
        catch(Exception e)
        {
            throw new MyException("Key does not exist\n");
        }
    }

    @Override
    public boolean isEmpty() {
        return fileTable.isEmpty();
    }

    @Override
    public T2 lookUp(T1 key) throws MyException {
        try
        {
            return fileTable.get(key);
        }
        catch(Exception e)
        {
            throw new MyException("Key does not exist");
        }
    }

    @Override
    public boolean isDefined(T1 key) {
        return fileTable.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        Iterator it = fileTable.entrySet().iterator();


        while(it.hasNext())
        {
            Map.Entry touple =(Map.Entry)it.next();
            toPrint.append(("key: ")+ touple.getKey() +" value:" + touple.getValue() + "\n");
        }
        return  toPrint.toString();
    }


    public Map<T1, T2> getContent() {
        return fileTable;
    }

    public void setContent(Map<T1, T2> map) {
        fileTable= (HashMap<T1, T2>) map;
    }
}
