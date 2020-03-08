package Domain.Values;

import Domain.Types.BoolType;
import Domain.Types.Type;

public class BoolValue implements Value<Boolean> {
    private boolean value;

    public BoolValue(boolean initValue)
    {
        value = initValue;
    }
    public Boolean getValue()
    {
        return value;
    }

    public String toString()
    {
        return "BoolValue: " +  Boolean.toString(value);
    }

    public Type getType()
    {
        return new BoolType();
    }

    public boolean equals(Object another)
    {
        return (another instanceof BoolValue);
    }

}
