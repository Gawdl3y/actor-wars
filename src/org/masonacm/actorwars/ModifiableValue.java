package org.masonacm.actorwars;

/**
 * A value that can be modified but still be the same instance
 * @param <E> The type of the value
 * @author Schuyler Cebulskie
 */
public class ModifiableValue<E> extends DynamicValue<E> {
    /**
     * The value of the {@code ModifiableValue}
     */
    protected E value;

    /**
     * Default constructor
     */
    public ModifiableValue() {}

    /**
     * Fill constructor
     * @param value The value for the {@code DynamicValue}
     */
    public ModifiableValue(E value) {
        this.value = value;
    }

    /**
     * Copy constructor
     * @param value The {@code DynamicValue} to copy from
     */
    public ModifiableValue(DynamicValue<E> value) {
        this.value = value.getValue();
    }

    /**
     * Sets the value
     * @param value The value to use
     */
    public void setValue(E value) {
        this.value = value;
    }

    /**
     * Copies the value from a {@code DynamicValue}
     * @param value The {@code DynamicValue} to copy from
     */
    public void setValue(DynamicValue<E> value) {
        this.value = value.getValue();
    }
    
    @Override
    public E getValue() {
        return value;
    }
}
