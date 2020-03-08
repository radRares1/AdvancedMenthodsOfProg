package Domain.Types;

import Domain.Values.RefValue;
import Domain.Values.Value;

public class RefType implements Type{

    Type inner;
    public RefType(Type initInner)
    {
        inner=initInner;
    }
    public Type getInner()
    {
        return this.inner;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RefType)
            return inner.equals(( (RefType) obj).getInner());
            else
                return false;
    }

    public String toString()
    {
        return "Ref(" +inner.toString()+")";
    }

    public Value defaultValue()
    {
        return new RefValue(0,inner);

    }
}
