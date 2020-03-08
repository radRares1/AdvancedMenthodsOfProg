package Domain.CustomDataTypes;
import Domain.Exceptions.MyException;

import java.util.List;

public interface MyInterfaceStack<T> {
    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    int size();
    String toString();
    List<T> getValues() throws MyException;
}
