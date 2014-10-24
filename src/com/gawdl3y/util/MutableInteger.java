package com.gawdl3y.util;

/**
 * A simple wrapper class for an integer
 * <p>Used so that references to instances are maintained when their value is changed</p>
 * @author Schuyler Cebulskie
 */
public class MutableInteger extends Mutable<Integer> {
    /**
     * Default constructor (value: 0)
     */
    public MutableInteger() {
        this.value = 0;
    }

    /**
     * Fill constructor
     * @param value The value to use
     */
    public MutableInteger(int value) {
        this.value = value;
    }

    /**
     * Copy constructor
     * @param value The MutableInteger to copy from
     */
    public MutableInteger(MutableInteger value) {
        this.value = value.getValue();
    }

    /**
     * Increments the integer value
     */
    public void increment() {
        value++;
    }

    /**
     * Decrements the integer value
     */
    public void decrement() {
        value--;
    }

    /**
     * Performs addition on this value
     * @param addend The value to add to this value
     */
    public void add(int addend) {
        this.value += addend;
    }

    /**
     * Performs addition on this value
     * @param addend The value to add to this value
     */
    public void add(MutableInteger addend) {
        this.value += addend.getValue();
    }

    /**
     * Performs subtraction on this value
     * @param subtrahend The value to subtract from this value
     */
    public void subtract(int subtrahend) {
        this.value -= subtrahend;
    }

    /**
     * Performs subtraction on this value
     * @param subtrahend The value to subtract from this value
     */
    public void subtract(MutableInteger subtrahend) {
        this.value -= subtrahend.getValue();
    }

    /**
     * Performs multiplication on this value
     * @param factor The value to multiply this value by
     */
    public void multiply(int factor) {
        this.value *= factor;
    }

    /**
     * Performs multiplication on this value
     * @param factor The value to multiply this value by
     */
    public void multiply(MutableInteger factor) {
        this.value *= factor.getValue();
    }

    /**
     * Performs division on this value
     * @param divisor The value to divide this value by
     */
    public void divide(int divisor) {
        this.value /= divisor;
    }

    /**
     * Performs division on this value
     * @param divisor The value to divide this value by
     */
    public void divide(MutableInteger divisor) {
        this.value /= divisor.getValue();
    }


    /**
     * Performs addition with two {@code DynamicValue&lt;Integer&gt;} objects
     * @param addend1 The first value
     * @param addend2 The second value
     * @return The result of the addition
     */
    public static MutableInteger add(DynamicValue<Integer> addend1, DynamicValue<Integer> addend2) {
        return new MutableInteger(addend1.getValue() + addend2.getValue());
    }

    /**
     * Performs subtraction with two {@code DynamicValue&lt;Integer&gt;} objects
     * @param minuend    The value to subtract from
     * @param subtrahend The value to subtract by
     * @return The result of the subtraction
     */
    public static MutableInteger subtract(DynamicValue<Integer> minuend, DynamicValue<Integer> subtrahend) {
        return new MutableInteger(minuend.getValue() - subtrahend.getValue());
    }

    /**
     * Performs multiplication with two {@code DynamicValue&lt;Integer&gt;} objects
     * @param factor1 The value to multiply
     * @param factor2 The value to multiply by
     * @return The result of the multiplication
     */
    public static MutableInteger multiply(DynamicValue<Integer> factor1, DynamicValue<Integer> factor2) {
        return new MutableInteger(factor1.getValue() * factor2.getValue());
    }

    /**
     * Performs division with two {@code DynamicValue&lt;Integer&gt;} objects
     * @param dividend The value to divide
     * @param divisor  The value to divide by
     * @return The result of the division
     */
    public static MutableInteger divide(DynamicValue<Integer> dividend, DynamicValue<Integer> divisor) {
        return new MutableInteger(dividend.getValue() / divisor.getValue());
    }
}
