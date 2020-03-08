package Domain.Types;

import Domain.Values.StringValue;
import Domain.Values.Value;

public class StringType implements Type {

    public StringType(){}

    public Value defaultValue()
    {
        return new StringValue("");
    }

    public boolean equals(Object another)
    {
        return(another instanceof StringType);
    }

    public String toString()
    {
        return "string";
    }
}
