package com.gawdl3y.util;

/**
 * A simple wrapper class for a boolean
 * <p>Used so that references to instances are maintained when their value is changed</p>
 * @author Schuyler Cebulskie
 */
public class MutableBoolean extends Mutable<Boolean> {
    /**
     * Default constructor (value: false)
     */
    public MutableBoolean() {
        this.value = false;
    }

    /**
     * Fill constructor
     * @param value The value to use
     */
    public MutableBoolean(Boolean value) {
        this.value = value;
    }

    /**
     * Copy constructor
     * @param value The {@code DynamicValue} to copy from
     */
    public MutableBoolean(DynamicValue<Boolean> value) {
        this.value = value.getValue();
    }

    /**
     * Inverts the boolean value ({@code true} becomes {@code false} and {@code false} becomes {@code true})
     */
    public void invert() {
        value = !value;
    }
}
