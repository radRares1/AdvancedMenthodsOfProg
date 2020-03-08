package Domain.Exceptions;

public class MyException extends Exception {
    public MyException(){}
    public MyException(String error)
    {

        super(error);
    }
}
