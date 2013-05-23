package org.masonacm.actorwars;

import com.gawdl3y.util.DynamicValue;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

/**
 * General utility methods for ActorWars
 * @author Christopher Krueger
 * @author Schuyler Cebulskie
 */
public final class Utils {
    /**
     * Tests if an Actor is at the DynamicValue&lt;Location&gt; specified
     * @param actor    The Actor to test
     * @param location The Location to test at
     * @return Whether or not the Actor specified is at the DynamicValue&lt;Location&gt;
     */
    public static DynamicValue<Boolean> atLocation(final Actor actor, final DynamicValue<Location> location) {
        return new DynamicValue<Boolean>() {
            @Override
            public Boolean getValue() {
                return actor.getLocation().equals(location.getValue());
            }
        };
    }

    /**
     * Tests if an Actor is not at the DynamicValue&lt;Location&gt; specified
     * @param actor    The Actor to test
     * @param location The Location to test at
     * @return Whether or not the Actor specified is at the DynamicValue&lt;Location&gt;
     */
    public static DynamicValue<Boolean> notAtLocation(final Actor actor, final DynamicValue<Location> location) {
        return new DynamicValue<Boolean>() {
            @Override
            public Boolean getValue() {
                return !actor.getLocation().equals(location.getValue());
            }
        };
    }

    /**
     * Tests if an object implements a class
     * @param e The object to check for the implementation
     * @param i The class to look for
     * @return True if the object implements the class, false if it doesn't
     */
    public static boolean isImplemented(Class<?> e, Class<?> i) {
        for(Class<?> c : e.getInterfaces()) {
            if(c.equals(i)) return true;
        }
        return false;
    }
}
