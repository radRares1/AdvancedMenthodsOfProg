package Domain.Values;

import Domain.Types.RefType;
import Domain.Types.Type;

public class RefValue implements Value{
    int address;
    Type locationType;
    public RefValue(int initAddr, Type initLocation)
    {
        address=initAddr;
        locationType=initLocation;
    }

    public int getAddr()
    {
        return address;
    }

    @Override
    public Value getValue() {
       return null;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof RefValue);
    }

    @Override
    public String toString() {
        return "RefValue( "+ address + ","+ locationType.toString()+")";
    }
}
