package org.masonacm.actorwars;

/**
 * A value that may change at any time (needs to be recalculated each time the value is obtained)
 * @param <T> The type of the value
 * @author Schuyler Cebulskie
 */
public abstract class DynamicValue<T> {
    /**
     * Default constructor
     */
    public DynamicValue() {}

    /**
     * Get the value of the {@code DynamicValue}
     * @return The value of the {@code DynamicValue}
     */
    public abstract T getValue();

    @Override
    public String toString() {
        return "D:" + getValue().toString();
    }
}
