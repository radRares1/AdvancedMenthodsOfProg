package Domain.Values;

import Domain.Types.StringType;
import Domain.Types.Type;

public class StringValue implements Value<String> {

    private String value;

    public StringValue(String initValue)
    {
        value=initValue;
    }

    public String getValue()
    {
        return value;
    }

    public String toString()
    {
        return "StringValue: " +  value;
    }

    public Type getType()
    {
        return new StringType();
    }

    public boolean equals(Object another)
    {
        return (another instanceof StringValue);
    }
}
