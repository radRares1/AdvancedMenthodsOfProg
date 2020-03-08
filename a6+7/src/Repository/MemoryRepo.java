package Repository;

import Domain.Exceptions.MyException;
import Domain.State.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MemoryRepo implements IRepository {
    private List<ProgramState> allStates;
    private String filePath;

    public MemoryRepo()
    {
        filePath = "";
        allStates = new ArrayList<ProgramState>();
    }

    public MemoryRepo(String initPath)
    {
        filePath = initPath;
        allStates = new ArrayList<ProgramState>();
    }

    public List<ProgramState> getProgramList()
    {
        return allStates;
    }

    public void addProgram(ProgramState program)
    {
        this.allStates.add(program);
    }

    public void logProgramStateExecute(ProgramState state) throws MyException, IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(filePath,true)));
        logFile.write(state.toString());
        logFile.close();
    }

    @Override
    public void setProgramList(List<ProgramState> states) {
        allStates = states;
    }


    @Override
    public ProgramState getProg(int id)
    {
        for(ProgramState pr : allStates)
            if(pr.getId() == id)
                return pr;
        return null;
    }

}
