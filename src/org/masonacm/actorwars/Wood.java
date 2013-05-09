package org.masonacm.actorwars;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;


public class Wood extends AgingDestructibleActor implements Resource, Placeable {
    public Wood() {
        super(15);//wood planks have 15 hp (destructable super constructor)
        setColor(new Color(109, 36, 0));
    }

    public Wood(int hp) {
        super(hp);
        setColor(new Color(109, 36, 0));
    }

    @Override
    public void place(Grid<Actor> g, Location l) {
        if(getGrid() == null) putSelfInGrid(g, l);
    }

    @Override
    public void destructibleAct() {
        super.destructibleAct();
    }

    @Override
    void damage(int d, ActiveActor a) {
        super.damage(d, a);
        if(getHealth() <= 0) {
            if(getGrid() != null) removeSelfFromGrid();
            a.addItem(Wood.class);
        }
    }

    @Override
    public String getName() {
        return "Wood";
    }
}
