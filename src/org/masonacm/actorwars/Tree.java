package org.masonacm.actorwars;

import info.gridworld.grid.Location;

import java.awt.*;

public class Tree extends Wood {
    public int age = 0;

    public Tree() {
        super(10);//tree has 10 hp (destructable super constructor)
        setColor(new Color(0, (int) (Math.random() * 100) + 100, 0));
    }

    @Override
    public void actDestructive() {
        // TODO Auto-generated method stub
        if(age <= 550)
            age++;
        if(age == 550) {
            if(getGrid().getEmptyAdjacentLocations(getLocation()).size() > 0) {
                Location l = getGrid().getEmptyAdjacentLocations(getLocation()).get((int) (Math.random() * (getGrid().getEmptyAdjacentLocations(getLocation()).size() - 1)));
                (new Tree()).putSelfInGrid(getGrid(), l);
            }
            age = 0;
        }
    }

}
