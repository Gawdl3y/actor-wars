package org.masonacm.actorwars;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

public class LocationFinder {
    static Grid<Actor> sgrid;

    //returns the location of the closest instance of [e] to active [a]
    public static Location findClosest(Active a, Class<?> e) {
        //System.out.println("locationfinder.findClosest("+e.getName()+")");
        ArrayList<Location> b = new ArrayList<Location>();
        int i = 0;
        int j = 0;
        while(i < a.getGrid().getNumRows()) {
            //System.out.println("locationfinder.findClosest():Scanning Rows");
            j = 0;
            while(j < a.getGrid().getNumCols()) {
                if(e.isInstance(a.getGrid().get(new Location(i, j)))) {
                    //System.out.println("locationfinder.findClosest():Instance found");
                    b.add(new Location(i, j));
                }
                j++;
            }
            i++;
        }
        int min = a.getGrid().getNumCols() + a.getGrid().getNumRows();
        int index = 0;
        for(Location l : b) {
            if(Math.abs(l.getRow() - a.getLocation().getRow()) + Math.abs(l.getCol() - a.getLocation().getCol()) < min) {
                index = b.indexOf(l);
                //System.out.println("locationfinder.findClosest():Instance "+index+" is closest");
                min = Math.abs(l.getRow() - a.getLocation().getRow()) + Math.abs(l.getCol() - a.getLocation().getCol());
            }
        }
        if(b == null || b.size() == 0)
            return null;
        //System.out.println("locationfinder.findClosest():Complete");
        return b.get(index);
    }
}
