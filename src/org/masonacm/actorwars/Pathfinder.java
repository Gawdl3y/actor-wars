package org.masonacm.actorwars;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;


public class Pathfinder {
    private static Grid<Actor> mgrid;

    public static ArrayList<Location> getPath(Location a, Location b, Grid<Actor> mygrid) {
        //Location t = a;
        //System.out.println("Pathfinder.getPath()");
        //a = b;
        //b = t;
        int ofc = mygrid.getNumCols() * mygrid.getNumRows();
        if(!mygrid.isValid(b))
            return null;
        if(mygrid.get(b) != null && !(mygrid.get(b) instanceof Passable))
            return null;
        //	System.out.println("Pathfinder.getPath(Beginning calculations)");
        mgrid = mygrid;
        ArrayList<Spot> p = new ArrayList<Spot>();
        p.add(new Spot(a));
        while(!p.contains(b) && ofc > 0) {
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
        //    System.out.println("Pathfinder.getPath(calculations complete)");
        if(ofc <= 0)
            return null;
        //    System.out.println("Pathfinder.getPath(no timout)");
        ArrayList<Location> al = new ArrayList<Location>();
        al.add(b);
        int ind = (p.get(p.indexOf(b))).parent;
        while((p.get(ind)).parent != -1) {
            al.add(0, new Location(p.get(ind).getRow(), p.get(ind).getCol()));
            ind = (p.get(ind)).parent;
        }
        mgrid = null;
        //     System.out.println("Pathfinder.getPath(complete)");
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
