package Domain.CustomDataTypes;

import Domain.Exceptions.MyException;

import java.util.Map;

public interface MyHeapInterface<T1,T2> {
    void add(T1 key, T2 value) throws MyException;
    void update(T1 key, T2 value) throws MyException;
    T2 remove(T1 key) throws MyException;
    boolean isEmpty();
    T2 lookUp(T1 key) throws MyException;
    boolean isDefined(T1 key);
    Map<T1,T2> getContent();
    void setContent(Map<T1, T2> map);
    String toString();
}
