package Domain;

public class FreePosition {
    private int position;
    public FreePosition() {
    position=0;
    }
    public int getPosition()
    {
        position++;
        return position;
    }
}
