package Domain.CustomDataTypes;
import Domain.Exceptions.MyException;
import Domain.Values.Value;

import java.util.ArrayList;
import java.util.List;

public interface MyIList<T> {
    void add(T element);;
    void add(int pos, T elem) throws MyException;
    void remove(T elem) throws MyException;
    void remove(int pos) throws MyException;
    T get(int pos) throws MyException;
    int size();
    boolean isEmpty();
    String toString();
    ArrayList<T> getValues();
}
