package sample;

import Controller.Controller;
import Domain.CustomDataTypes.*;
import Domain.Expressions.*;
import Domain.FreePosition;
import Domain.State.ProgramState;
import Domain.Statements.*;
import Domain.Types.*;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.StringValue;
import Repository.IRepository;
import Repository.MemoryRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class GUIControllerSelect implements Initializable {

    private List<InterfaceStatement> commands;
    private GUIControllerMain window;

    public void setMainWindowController(GUIControllerMain initRunWindow)
    {
        this.window=initRunWindow;
    }

    @FXML
    private ListView<String> commandListView;


    @FXML
    private Button executeButton;



    public void computeExamples()
    {
        MyIDictionary<String, Type> typeEnviornmentTest = new MyDictionary<>();
        MyIDictionary<String,Type> typeEnviornment1 = new MyDictionary<>();
        MyIDictionary<String,Type> typeEnviornment2 = new MyDictionary<>();
        MyIDictionary<String,Type> typeEnviornment3 = new MyDictionary<>();
        MyIDictionary<String,Type> typeEnviornment4 = new MyDictionary<>();
        MyIDictionary<String,Type> typeEnviornment5 = new MyDictionary<>();
        MyIDictionary<String,Type> typeEnviornment6 = new MyDictionary<>();
        MyIDictionary<String,Type> typeEnviornment7 = new MyDictionary<>();
        MyIDictionary<String,Type> typeEnviornment8 = new MyDictionary<>();
        MyIDictionary<String,Type> typeEnviornment9 = new MyDictionary<>();
        MyIDictionary<String,Type> typeEnviornment10 = new MyDictionary<>();



        try {

            //example1 : int a; a=2; print(a)
            InterfaceStatement example1 = new CompoundStatement(new VarDeclarationStatement("a", new IntType()),
                    new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(2))),
                            new PrintStatement(new VariableExpression("a"))));

            example1.typeCheck(typeEnviornment1);

            //example2: int a; int b;a=2+3*5;b=a+1;print(b)
            InterfaceStatement example2 = new CompoundStatement(new VarDeclarationStatement("a", new IntType()),
                    new CompoundStatement(new VarDeclarationStatement("b", new IntType()), new CompoundStatement(new AssignmentStatement
                            ("a", new ArithmeticExpression(1, new ValueExpression(new IntValue(2)), new ArithmeticExpression
                                    (3, new ValueExpression(new IntValue(3)),
                                            new ValueExpression(new IntValue(5))))), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(1,
                            new VariableExpression("a"), new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

            example2.typeCheck(typeEnviornment2);

            //example3: bool a; int v; a=true;(if a then v=2 else v=3);print(v)
            InterfaceStatement example3 = new CompoundStatement(new VarDeclarationStatement("a", new BoolType()),
                    new CompoundStatement(new VarDeclarationStatement("v", new IntType()),
                            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                    new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v",
                                            new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                            new PrintStatement(new VariableExpression("v"))))));

            example3.typeCheck(typeEnviornment3);

            //String varf; varf="test.in"; openRFile(varf); int varc, readFile(varf,varc); print(varc)
            InterfaceStatement testFile = new CompoundStatement(new VarDeclarationStatement("varf", new StringType()), new CompoundStatement(
                    new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))), new CompoundStatement(new OpenFileStatement(
                    new VariableExpression("varf")), new CompoundStatement(new VarDeclarationStatement("varc",
                    new IntType()), new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                    new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                            new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                    new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new
                                            CloseFileStatement(new VariableExpression("varf"))))))))));

            testFile.typeCheck(typeEnviornment4);


            // Ref int v; new(v, 20), Ref Ref int a; new (a, v); print(v), print(a);
            FreePosition f1 = new FreePosition();
            InterfaceStatement testAlloc = new CompoundStatement(new VarDeclarationStatement("v", new RefType(new IntType())),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)), f1),
                            new CompoundStatement(new VarDeclarationStatement("a", new RefType(
                                    new RefType(new IntType()))), new CompoundStatement(new HeapAllocationStatement("a",
                                    new VariableExpression("v"), f1), new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                    new PrintStatement(new VariableExpression("a")))))));

            testAlloc.typeCheck(typeEnviornment5);


            //Ref int v; new(v, 20); Ref Ref int a; new (a, v); print(rH(v)); print(rH(rH(a))+5)
            FreePosition f2 = new FreePosition();
            InterfaceStatement testReadHeap = new CompoundStatement(new VarDeclarationStatement("v", new RefType(new IntType())),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)), f2),
                            new CompoundStatement(new VarDeclarationStatement("a", new RefType(
                                    new RefType(new IntType()))), new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v"),
                                    f2), new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                    new PrintStatement(new ArithmeticExpression(1, new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))),
                                            new ValueExpression(new IntValue(5)))))))));

            testReadHeap.typeCheck(typeEnviornment6);

            //Ref int v; new(v, 20); print(readHeap(v)); writeHeap(v,30);print(readHeap(v)+5)
            FreePosition f3 = new FreePosition();
            InterfaceStatement testWriteHeap = new CompoundStatement(new VarDeclarationStatement("v", new RefType(new IntType())),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)), f3),
                            new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), new CompoundStatement(
                                    new HeapWritingStatement("v", new ValueExpression(new IntValue(30))), new PrintStatement(
                                    new ArithmeticExpression(1, new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(
                                            new IntValue(5))))))));

            testWriteHeap.typeCheck(typeEnviornment7);


            //Ref int v; new(v, 20), Ref ref int a; new(a, v); new(v, 30); print(readHeap(readHeap(a))
            FreePosition inc9 = new FreePosition();
            InterfaceStatement testGarbageCollector = new CompoundStatement(new VarDeclarationStatement("v", new RefType(new IntType())),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)), inc9), new CompoundStatement(
                            new VarDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v"), inc9), new CompoundStatement(
                                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(30)), inc9), new PrintStatement(new HeapReadingExpression(new
                                    HeapReadingExpression(new VariableExpression("a")))))))));

            testGarbageCollector.typeCheck(typeEnviornment8);


            //int v; v=4; (while (v>0) print(v); v=v-1);print(v)
            InterfaceStatement testWhile = new CompoundStatement(new VarDeclarationStatement("v", new IntType()), new CompoundStatement(
                    new AssignmentStatement("v", new ValueExpression(new IntValue(4))), new CompoundStatement(new WhileStatement(new RelationalExpression(
                    new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"), new CompoundStatement(new PrintStatement(
                    new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression(2, new VariableExpression("v"),
                    new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v")))));

            testWhile.typeCheck(typeEnviornment9);

            FreePosition f4 = new FreePosition();
            InterfaceStatement testFork = new CompoundStatement(new VarDeclarationStatement("v", new IntType()),
                    new CompoundStatement(new VarDeclarationStatement("a", new RefType(new IntType())),
                            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                    new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22)), f4),
                                            new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                    new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                            new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                    new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))),
                                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                            new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))))))));

            testFork.typeCheck(typeEnviornment10);


            commands = new ArrayList<>(Arrays.asList(example1,example2,example3,testFile,testAlloc,testWriteHeap,testReadHeap,testGarbageCollector,testFork,testWhile));

        }
        catch (Exception e)
        {
            System.exit(0);
        }


        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        computeExamples();
        List<String> examplesToString = commands.stream().map(InterfaceStatement::toString).collect(Collectors.toList());
        ObservableList<String> obsComms = FXCollections.observableList(examplesToString);

        commandListView.setItems(obsComms);

        executeButton.setOnAction(actionEvent -> {
            int index = commandListView.getSelectionModel().getSelectedIndex();

            if(index < 0)
                return;

            try {
                ProgramState initialProgramState = new ProgramState(commands.get(index));
                IRepository repository = new MemoryRepo("log" + Integer.toString(index) + ".txt");
                repository.addProgram(initialProgramState);
                Controller ctrl = new Controller(repository);
                window.setController(ctrl);
            }
            catch (Exception e)
            {
                Alert error = new Alert(Alert.AlertType.ERROR,e.getMessage(), ButtonType.OK);
                error.showAndWait();
                return;
            }
        });

    }
}
