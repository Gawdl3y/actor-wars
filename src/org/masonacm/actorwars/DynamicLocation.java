package org.masonacm.actorwars;

import info.gridworld.grid.Location;

import java.util.ArrayList;

public abstract class DynamicLocation extends Location {
    public abstract Location getLocation(Active a);

    public DynamicLocation() {
        super(0, 0);
    }

    public static DynamicLocation getClosestInstance(final Class<?> e, final Active a) {
        return new DynamicLocation() {
            public Location getLocation(Active a) {
                return LocationFinder.findClosest(a, e);
            }
        };
    }

    public static DynamicLocation getClosestEmptyAdjacentLocation(final Location f) {
        return new DynamicLocation() {
            public Location getLocation(Active a) {
                //System.out.println("sudoloc.slemptyadj.getloc(Target): "+f);
                //System.out.println("sudoloc.slemptyadj.getloc(b): "+a);
                if(f == null)
                    return null;
                ArrayList<Location> arrloc;
                if(f instanceof DynamicLocation) {
                    try {
                        arrloc = a.getGrid().getEmptyAdjacentLocations(((DynamicLocation) f).getLocation(a));
                    } catch(NullPointerException n) {
                        return null;
                    }
                } else {
                    arrloc = a.getGrid().getEmptyAdjacentLocations(f);
                }
                int min = a.getGrid().getNumCols() + a.getGrid().getNumRows();
                int index = 0;
                if(arrloc == null || arrloc.size() == 0)
                    return null;
                for(Location l : arrloc) {
                    if(Math.abs(l.getRow() - a.getLocation().getRow()) + Math.abs(l.getCol() - a.getLocation().getCol()) < min) {
                        index = arrloc.indexOf(l);
                        min = Math.abs(l.getRow() - a.getLocation().getRow()) + Math.abs(l.getCol() - a.getLocation().getCol());
                    }
                }
                //System.out.println("sudoloc.slemptyadj.getloc(Returned): "+arrloc.get(index));
                return arrloc.get(index);
            }
        };
    }
}
