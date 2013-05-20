package org.masonacm.actorwars;

import com.gawdl3y.util.DynamicValue;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

/**
 * Provides convenience methods for finding Locations
 * @author Christopher Krueger
 * @author Schuyler Cebulskie
 */
public class LocationFinder {
    /**
     * Finds the Location of the nearest Actor that is an instance of a specified class
     * @param location The Location to find the closest to
     * @param clazz    The class to check the instance of
     * @param grid     The grid to search on
     * @return The Location of the nearest Actor to the Location
     */
    public static Location findClosestInstanceLocation(final Location location, final Class<?> clazz, final Grid<Actor> grid) {
        if(location == null) throw new IllegalArgumentException("The location cannot be null");
        if(clazz == null) throw new IllegalArgumentException("The class cannot be null");
        if(grid == null) throw new IllegalArgumentException("The grid cannot be null");

        ArrayList<Location> locations = new ArrayList<Location>();

        // Find all instances
        for(int row = 0; row < grid.getNumRows(); row++) {
            for(int col = 0; col < grid.getNumCols(); col++) {
                if(clazz.isInstance(grid.get(new Location(row, col)))) {
                    locations.add(new Location(row, col));
                }
            }
        }
        if(locations.size() == 0) return null;

        // Find the one with the lowest distance
        int min = grid.getNumCols() + grid.getNumRows(), index = 0;
        for(Location l : locations) {
            if(Math.abs(l.getRow() - location.getRow()) + Math.abs(l.getCol() - location.getCol()) < min) {
                index = locations.indexOf(l);
                min = Math.abs(l.getRow() - location.getRow()) + Math.abs(l.getCol() - location.getCol());
            }
        }
        return locations.get(index);
    }

    /**
     * Finds the Location of the nearest Actor that is an instance of a specified class
     * @param location The Location to find the closest to
     * @param clazz    The class to check the instance of
     * @param grid     The grid to search on
     * @return The Location of the nearest Actor to the Location
     */
    public static Location findClosestInstanceLocation(final DynamicValue<Location> location, final Class<?> clazz, final Grid<Actor> grid) {
        if(location == null) throw new IllegalArgumentException("The location cannot be null");
        return findClosestInstanceLocation(location.getValue(), clazz, grid);
    }

    /**
     * Finds the Location of the nearest Actor that is an instance of a specified class
     * @param location The Location to find the closest to
     * @param clazz    The class to check the instance of
     * @param grid     The grid to search on
     * @return The Location of the nearest Actor to the Location, as a DynamicValue&lt;Location&gt;
     */
    public static DynamicValue<Location> findClosestInstanceDynamicLocation(final Location location, final Class<?> clazz, final Grid<Actor> grid) {
        if(location == null) throw new IllegalArgumentException("The location cannot be null");
        if(clazz == null) throw new IllegalArgumentException("The class cannot be null");
        if(grid == null) throw new IllegalArgumentException("The grid cannot be null");

        return new DynamicValue<Location>() {
            @Override
            public Location getValue() {
                return findClosestInstanceLocation(location, clazz, grid);
            }
        };
    }

    /**
     * Finds the Location of the nearest Actor that is an instance of a specified class
     * @param location The Location to find the closest to
     * @param clazz    The class to check the instance of
     * @param grid     The grid to search on
     * @return The Location of the nearest Actor to the Location, as a DynamicValue&lt;Location&gt;
     */
    public static DynamicValue<Location> findClosestInstanceDynamicLocation(final DynamicValue<Location> location, final Class<?> clazz, final Grid<Actor> grid) {
        if(location == null) throw new IllegalArgumentException("The location cannot be null");
        if(clazz == null) throw new IllegalArgumentException("The class cannot be null");
        if(grid == null) throw new IllegalArgumentException("The grid cannot be null");

        return new DynamicValue<Location>() {
            @Override
            public Location getValue() {
                return findClosestInstanceLocation(location.getValue(), clazz, grid);
            }
        };
    }

    /**
     * Find the empty location that is adjacent to the second location and is closest to the first
     * @param location1 The location to get the closest to
     * @param location2 The location to get the empty adjacent from
     * @param grid      The grid to search on
     * @return The empty location that is adjacent to the second location and is closest to the first
     */
    public static Location findClosestEmptyAdjacentLocation(final Location location1, final Location location2, final Grid<Actor> grid) {
        if(location1 == null || location2 == null) throw new IllegalArgumentException("The locations cannot be null");
        if(grid == null) throw new IllegalArgumentException("The grid cannot be null");

        // Get all of the empty adjacent locations
        ArrayList<Location> locations = grid.getEmptyAdjacentLocations(location2);
        if(locations == null) return null;

        // Find the closest
        Location closest = null;
        int min = grid.getNumCols() + grid.getNumRows(), current;
        for(Location l : locations) {
            current = Math.abs(l.getRow() - location1.getRow()) + Math.abs(l.getCol() - location1.getCol());
            if(current < min) {
                closest = l;
                min = current;
            }
        }
        return closest;
    }

    /**
     * Find the empty location that is adjacent to the second location and is closest to the first
     * @param location1 The location to get the closest to
     * @param location2 The location to get the empty adjacent from
     * @param grid      The grid to search on
     * @return The empty location that is adjacent to the second location and is closest to the first
     */
    public static Location findClosestEmptyAdjacentLocation(final DynamicValue<Location> location1, final DynamicValue<Location> location2, final Grid<Actor> grid) {
        if(location1 == null || location2 == null) throw new IllegalArgumentException("The locations cannot be null");
        return findClosestEmptyAdjacentLocation(location1.getValue(), location2.getValue(), grid);
    }

    /**
     * Find the empty location that is adjacent to the second location and is closest to the first
     * @param location1 The location to get the closest to
     * @param location2 The location to get the empty adjacent from
     * @param grid      The grid to search on
     * @return The empty location that is adjacent to the second location and is closest to the first, as a DynamicValue&lt;Location&gt;
     */
    public static DynamicValue<Location> findClosestEmptyAdjacentDynamicLocation(final Location location1, final Location location2, final Grid<Actor> grid) {
        if(location1 == null || location2 == null) throw new IllegalArgumentException("The locations cannot be null");
        if(grid == null) throw new IllegalArgumentException("The grid cannot be null");
        return new DynamicValue<Location>() {
            @Override
            public Location getValue() {
                return findClosestEmptyAdjacentLocation(location1, location2, grid);
            }
        };
    }

    /**
     * Find the empty location that is adjacent to the second location and is closest to the first
     * @param location1 The location to get the closest to
     * @param location2 The location to get the empty adjacent from
     * @param grid      The grid to search on
     * @return The empty location that is adjacent to the second location and is closest to the first, as a DynamicValue&lt;Location&gt;
     */
    public static DynamicValue<Location> findClosestEmptyAdjacentDynamicLocation(final DynamicValue<Location> location1, final DynamicValue<Location> location2, final Grid<Actor> grid) {
        if(location1 == null || location2 == null) throw new IllegalArgumentException("The locations cannot be null");
        if(grid == null) throw new IllegalArgumentException("The grid cannot be null");
        return new DynamicValue<Location>() {
            @Override
            public Location getValue() {
                return findClosestEmptyAdjacentLocation(location1.getValue(), location2.getValue(), grid);
            }
        };
    }

    /**
     * Gets the direction from a Location to another Location
     * @param location1 The first Location
     * @param location2 The second Location
     * @return The direction from the Actor to the Location
     */
    public static int directionTo(final Location location1, final Location location2) {
        if(location1 == null || location2 == null) throw new IllegalArgumentException("The locations cannot be null");
        return location1.getDirectionToward(location2);
    }

    /**
     * Gets the direction from a Location to another Location
     * @param location1 The first Location
     * @param location2 The second Location
     * @return The direction from the Actor to the Location
     */
    public static int directionTo(final DynamicValue<Location> location1, final DynamicValue<Location> location2) {
        if(location1 == null || location2 == null) throw new IllegalArgumentException("The locations cannot be null");
        return location1.getValue().getDirectionToward(location2.getValue());
    }

    /**
     * Gets the direction from a Location to another Location
     * @param location1 The first Location
     * @param location2 The second Location
     * @return The direction from the Actor to the Location, as a DynamicValue&lt;Integer&gt;
     */
    public static DynamicValue<Integer> dynamicDirectionTo(final Location location1, final Location location2) {
        if(location1 == null || location2 == null) throw new IllegalArgumentException("The locations cannot be null");
        return new DynamicValue<Integer>() {
            @Override
            public Integer getValue() {
                return location1.getDirectionToward(location2);
            }
        };
    }

    /**
     * Gets the direction from a Location to another Location
     * @param location1 The first Location
     * @param location2 The second Location
     * @return The direction from the Actor to the Location, as a DynamicValue&lt;Integer&gt;
     */
    public static DynamicValue<Integer> dynamicDirectionTo(final DynamicValue<Location> location1, final DynamicValue<Location> location2) {
        if(location1 == null || location2 == null) throw new IllegalArgumentException("The locations cannot be null");
        return new DynamicValue<Integer>() {
            @Override
            public Integer getValue() {
                return location1.getValue().getDirectionToward(location2.getValue());
            }
        };
    }
}
