package Domain.Values;

import Domain.Types.IntType;
import Domain.Types.Type;

public class IntValue implements Value<Integer> {
    int value;

    public IntValue(int initValue)
    {
        value = initValue;
    }

    public Integer getValue()
    {
        return value;
    }
    public String toString()
    {
        return "Integer Value:" + Integer.toString(value);
    }
    public Type getType()
    {
        return new IntType();
    }

    public boolean equals(Object another)
    {
        return (another instanceof IntValue);
    }
}

