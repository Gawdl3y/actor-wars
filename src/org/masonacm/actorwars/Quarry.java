package org.masonacm.actorwars;

import info.gridworld.grid.Location;

import java.awt.*;


public class Quarry extends Destructible {
    public Quarry() {
        setColor(new Color(100, 100, 100));
    }

    @Override
    public void actDestructive() {
        if(Math.random() > .92) {
            if(Math.random() > .2) {
                if(getGrid().getEmptyAdjacentLocations(getLocation()).size() > 0) {
                    Location l = getGrid().getEmptyAdjacentLocations(getLocation()).get((int) (Math.random() * (getGrid().getEmptyAdjacentLocations(getLocation()).size() - 1)));
                    (new Rock()).putSelfInGrid(getGrid(), l);
                }
            } else {
                if(getGrid().getEmptyAdjacentLocations(getLocation()).size() > 0) {
                    Location l = getGrid().getEmptyAdjacentLocations(getLocation()).get((int) (Math.random() * (getGrid().getEmptyAdjacentLocations(getLocation()).size() - 1)));
                    (new IronOre()).putSelfInGrid(getGrid(), l);
                }
            }
        }
    }

}
