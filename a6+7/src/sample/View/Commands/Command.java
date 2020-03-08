package sample.View.Commands;

public abstract class Command {
    private String key,description;
    public Command(String initKey, String initDescription)
    {
        key=initKey;
        description=initDescription;
    }
    public abstract void execute();
    public String getKey(){return key;}
    public String getDesc(){return description;}
}
