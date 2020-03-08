package sample;

import Controller.Controller;
import Domain.CustomDataTypes.*;
import Domain.Exceptions.MyException;
import Domain.Expressions.*;
import Domain.FreePosition;
import Domain.State.ProgramState;
import Domain.Statements.*;
import Domain.Types.*;
import Domain.Values.*;
import Repository.IRepository;
import Repository.MemoryRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.View.Commands.ExitCommand;
import sample.View.Commands.RunCommand;
import sample.View.TextMenu;

import java.util.Scanner;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{


        FXMLLoader main = new FXMLLoader();

        main.setLocation(getClass().getResource("sample2.fxml"));
        Parent mainWindow = main.load();

        GUIControllerMain mainController = main.getController();

        primaryStage.setTitle("Main");
        primaryStage.setScene(new Scene(mainWindow, 800, 700));
        primaryStage.show();


        FXMLLoader secondaryLoader = new FXMLLoader();
        secondaryLoader.setLocation(getClass().getResource("sample.fxml"));
        Parent secondaryWindow = secondaryLoader.load();

        GUIControllerSelect selectWindowController = secondaryLoader.getController();
        selectWindowController.setMainWindowController(mainController);

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Select Window");
        secondaryStage.setScene(new Scene(secondaryWindow, 600, 400));
        secondaryStage.show();

    }

    public static void main(String[] args) throws MyException
    {
        launch(args);
    }


}
