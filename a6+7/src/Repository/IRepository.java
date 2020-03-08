package Repository;

import Domain.Exceptions.MyException;
import Domain.State.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void addProgram(ProgramState program);
    void logProgramStateExecute(ProgramState state) throws MyException, IOException;
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> states);
    ProgramState getProg(int id);
}
