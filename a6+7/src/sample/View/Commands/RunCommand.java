package sample.View.Commands;

import Controller.Controller;

public class RunCommand extends Command {

    private Controller controller;

    public RunCommand(String key,String description,Controller controller)
    {
        super(key,description);
        this.controller=controller;
    }


    @Override
    public void execute()
    {
        try {
            controller.allStep();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
