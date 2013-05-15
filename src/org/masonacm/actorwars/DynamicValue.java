package org.masonacm.actorwars;

/**
 * A value that may change at any time (needs to be recalculated each time the value is obtained)
 * @param <E> The type of the value
 * @author Schuyler Cebulskie
 */
public abstract class DynamicValue<E> {
    /**
     * Default constructor
     */
    public DynamicValue() {}

    /**
     * Get the value of the {@code DynamicValue}
     * @return The value of the {@code DynamicValue}
     */
    public abstract E getValue();

    @Override
    public String toString() {
        return getValue().toString();
    }
}
