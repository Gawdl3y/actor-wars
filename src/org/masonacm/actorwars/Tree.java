package org.masonacm.actorwars;

import info.gridworld.grid.Location;

import java.awt.*;

public class Tree extends Wood {
    public Tree() {
        super(10);
        setColor(new Color(0, (int) (Math.random() * 100) + 100, 0));
    }

    @Override
    public void destructibleAct() {
        super.destructibleAct();
        if(age == 550) {
            if(getGrid().getEmptyAdjacentLocations(getLocation()).size() > 0) {
                Location l = getGrid().getEmptyAdjacentLocations(getLocation()).get((int) (Math.random() * (getGrid().getEmptyAdjacentLocations(getLocation()).size() - 1)));
                (new Tree()).putSelfInGrid(getGrid(), l);
            }
            age = 0;
        }
    }
}
