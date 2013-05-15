package com.gawdl3y.util;

import info.gridworld.grid.Location;

/**
 * A simple wrapper class for an integer
 * <p>Used so that references to instances are maintained when their value is changed</p>
 * @author Schuyler Cebulskie
 */
public class ModifiableLocation extends ModifiableValue<Location> {
    /**
     * Default constructor (value: (0, 0))
     */
    public ModifiableLocation() {
        this.value = new Location(0, 0);
    }

    /**
     * Fill constructor
     * @param value The Location to use
     */
    public ModifiableLocation(Location value) {
        this.value = value;
    }

    /**
     * Fill constructor
     * @param row The row of the Location to use
     * @param col The column of the Location to use
     */
    public ModifiableLocation(int row, int col) {
        this.value = new Location(row, col);
    }

    /**
     * Copy constructor
     * @param value The Location to use
     */
    public ModifiableLocation(DynamicValue<Location> value) {
        this.value = new Location(value.getValue().getRow(), value.getValue().getCol());
    }

    /**
     * Sets the Location value
     * @param row The row of the Location to use
     * @param col The column of the Location to use
     */
    public void setValue(int row, int col) {
        this.value = new Location(row, col);
    }

    /**
     * Gets the row of the Location
     * @return The row of the Location
     */
    public int getRow() {
        return value.getRow();
    }

    /**
     * Gets the column of the Location
     * @return The column of the Location
     */
    public int getCol() {
        return value.getCol();
    }

    /**
     * Gets the direction to another Location
     * @param location The Location to get the direction to
     * @return The direction from this Location to the specified Location
     */
    public int directionTo(DynamicValue<Location> location) {
        return value.getDirectionToward(location.getValue());
    }

    /**
     * Gets the direction to another Location
     * @param location The Location to get the direction to
     * @return The direction from this Location to the specified Location
     */
    public int directionTo(Location location) {
        return value.getDirectionToward(location);
    }
}
