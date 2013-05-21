package org.masonacm.actorwars;

import com.gawdl3y.util.ModifiableLocation;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

/**
 * Class to find paths to Locations
 * <p>Uses the A* pathfinding algorithm</p>
 * @author Chris Krueger
 */
public class Pathfinder {
    private static Grid<Actor> mgrid;

    /**
     * Find a path to the specified Location using the A* algorithm
     * @param a      The starting location
     * @param b      The end location
     * @param mygrid The grid
     * @return An ArrayList of Locations that form the path
     */
    public static ArrayList<Location> findPath(Location a, Location b, Grid<Actor> mygrid) {
        try {
            //Location t = a;
            //     System.out.println("Pathfinder.nonmodif("+b+")");
            mgrid = mygrid;
            //a = b;
            //b = t;
            if(b == null) return null;
            //    System.out.println("Target not null");
            if(a == null) return null;
            //     System.out.println("Source not null");
            if(mgrid == null) return null;
            //     System.out.println("Grid not null");
            //     System.out.println("All Parameters not null");
            int ofc = mygrid.getNumCols() * mygrid.getNumRows();
            if(!mygrid.isValid(b)) return null;
            //    System.out.println("valid target");
            while(ofc > 0 && (mygrid.get(b) != null && !(mygrid.get(b) instanceof Passable))) {
                //         System.out.println("need to recalculate target");
                b = LocationFinder.findClosestEmptyAdjacentDynamicLocation(new ModifiableLocation(mgrid.get(a).getLocation()), new ModifiableLocation(b), mgrid).getValue();
                if(b == null)
                    b = b.getAdjacentLocation(b.getDirectionToward(a));
                ofc--;
            }
            if(ofc <= 0) return null;
            ofc = mygrid.getNumCols() * mygrid.getNumRows();

            if(b == null) return null;
            //   System.out.println("Target Finalized");
            //   	System.out.println("Pathfinder.findPath(Beginning calculations)");
            ArrayList<Spot> p = new ArrayList<Spot>();
            p.add(new Spot(a));
            while(!p.contains(b) && ofc > 0) {
                //   System.out.println("Thinkingnonmodif:"+ofc);
                ofc--;
                int temp = p.indexOf(getLowest(p));
                for(Location q : mygrid.getValidAdjacentLocations(getLowest(p))) {
                    Spot l = new Spot(q);
                    if(!p.contains(l)) {
                        p.add(new Spot(l, p.indexOf(getLowest(p)), b.getRow(), b.getCol()));
                        if(!(p.get(p.size() - 1)).isNull()) {
                            p.remove(p.size() - 1);
                        }
                    } else {
                        if((p.get(temp)).disttohere(p) + 1 < p.get(p.indexOf(l)).disttohere(p)) {
                            p.get(p.indexOf(l)).parent = temp;
                        }
                    }
                }
                (p.get(temp)).checked = true;
            }
            //    System.out.println("Pathfinder.findPath(calculations complete)");
            if(ofc <= 0)
                return null;
            //     System.out.println("Pathfinder.findPath(no timout)");
            ArrayList<Location> al = new ArrayList<Location>();
            al.add(b);
            int ind = (p.get(p.indexOf(b))).parent;
            while((p.get(ind)).parent != -1) {
                al.add(0, new Location(p.get(ind).getRow(), p.get(ind).getCol()));
                ind = (p.get(ind)).parent;
            }
            mgrid = null;
            //    System.out.println("Pathfinder.findPathNonmodif(complete)");
            return al;
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Find a path to the specified Location using the A* algorithm
     * @param a      The starting location
     * @param b      The end location
     * @param mygrid The grid
     * @return An ArrayList of Locations that form the path
     */
    public static ArrayList<Location> findPath(Location a, ModifiableLocation b, Grid<Actor> mygrid) {
        //Location t = a;
        int ofc = 0;
        //System.out.println("Pathfinder.findPathmodifiable("+b.getValue()+")");
        try {
            mgrid = mygrid;
            //a = b;
            //b = t;
            if(b == null) return null;
            // System.out.println("Target not null");
            if(a == null) return null;
            // System.out.println("Source not null");
            if(mgrid == null) return null;
            //  System.out.println("Grid not null");
            //   System.out.println("All Parameters not null");
            ofc = mygrid.getNumCols() * mygrid.getNumRows();
            if(!mygrid.isValid(b.getValue())) return null;
            //    System.out.println("valid target");
            // System.out.println("b:"+mygrid.get(b.getValue()));
        } catch(Exception e) {
            return null;
        }

        try {
            while(ofc > 0 && (mygrid.get(b.getValue()) != null && !(mygrid.get(b.getValue()) instanceof Passable))) {
                //         System.out.println("need to recalculate target");
                b.setValue(LocationFinder.findClosestEmptyAdjacentLocation(new ModifiableLocation(mgrid.get(a).getLocation()), b, mgrid));
                if(b == null)
                    b.setValue(b.getValue().getAdjacentLocation(b.getValue().getDirectionToward(a)));
                ofc--;
            }
            if(ofc <= 0) return null;
        } catch(Exception e) {
            return null;
        }
        ofc = mygrid.getNumCols() * mygrid.getNumRows();
        if(b == null) return null;
        //  System.out.println("Target Finalized");
        // 	System.out.println("Pathfinder.findPath(Beginning calculations)");
        ArrayList<Spot> p = new ArrayList<Spot>();
        p.add(new Spot(a));
        while(!p.contains(b.getValue()) && ofc > 0) {
            //       System.out.println("Thinkingmodif:"+ofc);
            ofc--;
            int temp = p.indexOf(getLowest(p));
            for(Location q : mygrid.getValidAdjacentLocations(getLowest(p))) {
                Spot l = new Spot(q);
                if(!p.contains(l)) {
                    p.add(new Spot(l, p.indexOf(getLowest(p)), b.getRow(), b.getCol()));
                    if(!(p.get(p.size() - 1)).isNull()) {
                        p.remove(p.size() - 1);
                    }
                } else {
                    if((p.get(temp)).disttohere(p) + 1 < p.get(p.indexOf(l)).disttohere(p)) {
                        p.get(p.indexOf(l)).parent = temp;
                    }
                }
            }
            (p.get(temp)).checked = true;
        }
        //    System.out.println("Pathfinder.findPath(calculations complete)");
        if(ofc <= 0) {

            //      System.out.println("Pathfinder.findPath(I timed out)");
            //      System.out.println(" p = "+p);
            return null;
        }
        //     System.out.println("Pathfinder.findPath(no timout)");
        ArrayList<Location> al = new ArrayList<Location>();
        al.add(b.getValue());
        int ind = (p.get(p.indexOf(b.getValue()))).parent;
        while((p.get(ind)).parent != -1) {
            al.add(0, new Location(p.get(ind).getRow(), p.get(ind).getCol()));
            ind = (p.get(ind)).parent;
        }
        mgrid = null;
        //         System.out.println("Pathfinder.findPath(complete)");
        return al;
    }

    private static Location getLowest(ArrayList<Spot> p) {
        double min = 1000;
        int index = 0;
        int i = 0;
        while(index < p.size()) {
            if((p.get(index)).getCost(p) < min && !(p.get(index)).checked) {
                i = index;
                min = (p.get(index)).getCost(p);
            }
            index++;
        }
        return p.get(i);
    }

    private static class Spot extends Location {
        public int parent;
        public boolean checked = false;

        public int disttohere(ArrayList<Spot> p) {
            int i = 0;
            if(parent != -1) {
                i = 1;
                int ind = parent;
                while((p.get(ind)).parent != -1) {
                    ind = (p.get(ind)).parent;
                    i++;
                }
            }
            return i;
        }

        int tr;
        int tc;

        public Spot(Location l) {
            super(l.getRow(), l.getCol());
            parent = -1;
        }

        public Spot(Location l, int anc, int termr, int termc) {
            super(l.getRow(), l.getCol());
            tr = termr;
            tc = termc;
            parent = anc;
        }

        public boolean isNull() {
            if(Pathfinder.mgrid.get(this) == null) {
                return true;
            } else {
                if(Pathfinder.mgrid.get(this) instanceof Passable) {
                    return ((Passable) Pathfinder.mgrid.get(this)).canPass(0);
                }
            }
            return false;
        }

        public double getCost(ArrayList<Spot> p) {
            return disttohere(p) + Math.abs(getRow() - tr) + Math.abs(getCol() - tc);
        }
    }
}
