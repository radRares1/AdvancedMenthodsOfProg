package Domain.Values;
import Domain.Types.Type;

public interface Value<T> {
    Type getType();
    T getValue();
    boolean equals(Object another);
    String toString();
}
