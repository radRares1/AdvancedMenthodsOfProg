package Controller;
import Domain.Exceptions.MyException;

import java.io.IOException;

public interface IController {
    //ProgramState oneStep(ProgramState state) throws MyException;
    void allStep() throws MyException, IOException, InterruptedException;
}
