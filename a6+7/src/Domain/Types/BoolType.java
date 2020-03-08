package Domain.Types;

import Domain.Values.BoolValue;
import Domain.Values.Value;

public class BoolType implements Type {

    public BoolType(){};

    public boolean equals(Object another)
    {
        if(another instanceof BoolType)
            return true;
        else
            return false;
    }

    public Value defaultValue()
    {
        return new BoolValue(false);
    }

    public String toString()
    {
        return "bool";
    }
}
