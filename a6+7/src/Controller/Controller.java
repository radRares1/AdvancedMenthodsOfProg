package Controller;

import Domain.Exceptions.MyException;
import Domain.State.ProgramState;
import Domain.Values.RefValue;
import Domain.Values.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {

    private IRepository repo;
    private ExecutorService executor;

    public IRepository getRepo()
    {
        return this.repo;
    }

    public Controller(IRepository initRepo) {
        repo = initRepo;
    }

    public List<ProgramState> getStates()
    {
        return this.repo.getProgramList();
    }


    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddrs, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddrs.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddr();
                })
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {
        Map<Integer, Value> newHeap = new HashMap<>();

        for (Integer k : heap.keySet()) {
            if (symbolTableAddresses.contains(k))
                newHeap.put(k, heap.get(k));
        }

        Map<Integer, Value> map = heap.entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<Integer, Value> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Value value = entry.getValue();
            while (value instanceof RefValue) {
                RefValue refVal = (RefValue) value;
                int address = refVal.getAddr();
                Value value2 = heap.get(address);
                newHeap.put(address, value2);
                value = value2;
            }
        }

        return newHeap;
    }


    public List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public ProgramState getProg(int id)
    {
        return this.repo.getProg(id);
    }

    public void oneStepForOneProgram() throws MyException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> states = removeCompletedProgram(repo.getProgramList());
        if(!states.isEmpty())
        {
            //garbage collector
            states.forEach(program->program.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable
                    (program.getSymbolTable().getContent().values()), program.getHeap().getContent())));

            oneStepForAllPrograms(states);
        }
        executor.shutdownNow();
        repo.setProgramList(states);
    }


    public void oneStepForAllPrograms(List<ProgramState> list) throws InterruptedException {


        List<Callable<ProgramState>> callList = list.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .collect(Collectors.toList());


        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        list.addAll(newProgramList);

        list.forEach(prog -> {
            try {
                repo.logProgramStateExecute(prog);
            } catch (MyException | IOException e) {
                e.printStackTrace();
            }
        });

        repo.setProgramList(list);


    }

    @Override
    public void allStep() throws MyException, IOException, InterruptedException {


        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programList = removeCompletedProgram(repo.getProgramList());



        while (programList.size() > 0) {
            oneStepForAllPrograms(programList);

            //garbage collector
            programList.forEach(program->program.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable
                    (program.getSymbolTable().getContent().values()), program.getHeap().getContent())));



            programList = removeCompletedProgram(repo.getProgramList());

        }

        executor.shutdownNow();

        //update repo state

        repo.setProgramList(programList);


    }


}
