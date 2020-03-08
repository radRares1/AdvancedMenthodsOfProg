package sample;

import Controller.Controller;
import Domain.CustomDataTypes.MyHeapInterface;
import Domain.CustomDataTypes.MyIDictionary;
import Domain.CustomDataTypes.MyInterfaceStack;
import Domain.Exceptions.MyException;
import Domain.State.ProgramState;
import Domain.Statements.InterfaceStatement;
import Domain.Values.StringValue;
import Domain.Values.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class GUIControllerMain implements Initializable {

    private Controller ctrl;

    @FXML
    private TextField programStatesNumber;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> heapAddressCol;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Value> heapValueCol;

    @FXML
    private ListView<String> fileTableList;

    @FXML
    private ListView<String> out;

    @FXML
    private ListView<Integer> progId;

    @FXML
    private TableView<Map.Entry<String,Value>> symTableOfState;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symTableVarName;

    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> symTableVal;

    @FXML
    private ListView<String> exeStackOfId;

    @FXML
    private Button runOneStepButton;

    void setController(Controller initController)
    {
        this.ctrl=initController;
        populateIds();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        heapAddressCol.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueCol.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        symTableVarName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        symTableVal.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        progId.setOnMouseClicked(mouseEvent -> {
            try {
                changeProgramState(getCurrent());
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

        runOneStepButton.setOnAction(actionEvent -> {
            try {
                runOneStepForAll(actionEvent);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

    }

    private void populateIds()
    {
        List<ProgramState> states = ctrl.getStates();
        progId.setItems(FXCollections.observableList(states.stream().map(ProgramState::getId).collect(Collectors.toList())));
        progId.refresh();

        programStatesNumber.setText(Integer.toString(states.size()));

    }



    public void runOneStepForAll(ActionEvent actionEvent) throws MyException {

        if(ctrl==null)
        {
            Alert error = new Alert(Alert.AlertType.ERROR,"Select the program");
            error.showAndWait();
            return;
        }

        if(this.ctrl.getStates().isEmpty())
        {
            Alert error = new Alert(Alert.AlertType.ERROR,"Execution done");
            error.showAndWait();
            return;
        }

        try
        {
            this.ctrl.oneStepForOneProgram();

        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "some message", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        changeProgramState(getCurrent());
        ctrl.getRepo().setProgramList(ctrl.removeCompletedProgram(ctrl.getRepo().getProgramList()));
        populateIds();
    }

    private void changeProgramState(ProgramState currentProg) throws MyException {
        if(currentProg == null)
            return;
        else {
            populateExeStack(currentProg);
            populateSymTable(currentProg);
            populateOut(currentProg);
            populateFileTable(currentProg);
            populateHeap(currentProg);

        }
    }

    public void populateHeap(ProgramState prog)
    {
        MyHeapInterface<Integer,Value> localHeapTable = prog.getHeap();

        List<Map.Entry<Integer,Value>> heapList = new ArrayList<>(localHeapTable.getContent().entrySet());
        heapTableView.setItems(FXCollections.observableList(heapList));
        heapTableView.refresh();
    }

    public void populateFileTable(ProgramState prog)
    {
        MyIDictionary<StringValue, BufferedReader> fileTable = prog.getFileTable();
        List<String> fileList = new ArrayList<>();
        for (Map.Entry<StringValue, BufferedReader> entry : fileTable.getContent().entrySet())
            fileList.add(entry.getKey().toString());
        ObservableList<String> files = FXCollections.observableArrayList(fileList);
        fileTableList.setItems(files);
        fileTableList.refresh();
    }

    public void populateOut(ProgramState prog)
    {
        List<Value> values = prog.getOut().getValues();
        List<String>  valuesToString = values.stream().map(Value::toString).collect(Collectors.toList());
        out.setItems(FXCollections.observableList(valuesToString));
        out.refresh();
    }

    private void populateExeStack(ProgramState prog) throws MyException {
        MyInterfaceStack<InterfaceStatement> exeStack = prog.getStack();

        List<String> exeStackList = new ArrayList<String>();

        for(InterfaceStatement statement : exeStack.getValues())
        {

            exeStackList.add(0,statement.toString());
        }

        exeStackOfId.setItems(FXCollections.observableList(exeStackList));
        exeStackOfId.refresh();
    }

    private void populateSymTable(ProgramState prog)
    {
        MyIDictionary<String,Value> symbolTable = prog.getSymbolTable();

        List<Map.Entry<String,Value>> list = new ArrayList<>(symbolTable.getContent().entrySet());
        symTableOfState.setItems(FXCollections.observableList(list));
        symTableOfState.refresh();

    }

    private ProgramState getCurrent()
    {
        if(progId.getSelectionModel().getSelectedIndex() == -1)
            return null;

        int currentId = progId.getSelectionModel().getSelectedItem();

        return ctrl.getProg(currentId);

    }
}
