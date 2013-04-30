package org.masonacm.actorwars;

import info.gridworld.grid.Location;

/**
 * General utility methods for ActorWars
 * @author Christopher Krueger
 * @author Schuyler Cebulskie
 */
public final class Utils {
    /**
     * Gets the direction from an Active to a Location
     * @param a The Active
     * @param l The Location
     * @return The direction from the Active to the Location
     */
    public static ModifiableBoolean atLocation(final Active a, final Location l) {
        return new ModifiableBoolean() {
            @Override
            public boolean getValue() {
                if(l instanceof DynamicLocation) return a.getLocation().equals(((DynamicLocation) l).getLocation(a));
                else return a.getLocation().equals(l);
            }
        };
    }

    public static ModifiableBoolean notAtLocation(final Active a, final Location l) {
        return new ModifiableBoolean() {
            @Override
            public boolean getValue() {
                if(l instanceof DynamicLocation) return !a.getLocation().equals(((DynamicLocation) l).getLocation(a));
                else return !a.getLocation().equals(l);
            }
        };
    }

    public static ModifiableInteger directionTo(final Active a, final Location l) {
        return new ModifiableInteger() {
            @Override
            public int getValue() {
                if(a == null || a.getLocation() == null)
                    return 0;
                if(l == null || ((l instanceof DynamicLocation) ? (((DynamicLocation) l).getLocation(a) == null) : (false)))
                    return 0;

                if(l instanceof DynamicLocation)
                    return a.getLocation().getDirectionToward(((DynamicLocation) l).getLocation(a));
                else return a.getLocation().getDirectionToward(l);
            }
        };
    }

    /**
     * Checks to see if an object implements a class
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
