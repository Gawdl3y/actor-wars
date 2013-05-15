package org.masonacm.actorwars;

/**
 * A value that can be modified but still be the same instance
 * @param <T> The type of the value
 * @author Schuyler Cebulskie
 */
public class ModifiableValue<T> extends DynamicValue<T> {
    /**
     * The value of the {@code ModifiableValue}
     */
    protected T value;

    /**
     * Default constructor
     */
    public ModifiableValue() {}

    /**
     * Fill constructor
     * @param value The value for the {@code DynamicValue}
     */
    public ModifiableValue(T value) {
        this.value = value;
    }

    /**
     * Copy constructor
     * @param value The {@code DynamicValue} to copy from
     */
    public ModifiableValue(DynamicValue<T> value) {
        this.value = value.getValue();
    }

    /**
     * Sets the value
     * @param value The value to use
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Copies the value from a {@code DynamicValue}
     * @param value The {@code DynamicValue} to copy from
     */
    public void setValue(DynamicValue<T> value) {
        this.value = value.getValue();
    }
    
    @Override
    public T getValue() {
        return value;
    }
}
