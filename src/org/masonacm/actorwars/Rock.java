package org.masonacm.actorwars;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;

public class Rock extends DestructibleActor implements Movable, Placeable, Resource {
    private boolean secured = false;

    public Rock() {
        super(50);
        setColor(Color.GRAY);
    }

    protected Rock(int hp) {
        super(hp);
    }

    @Override
    void damage(int d, ActiveActor a) {
        super.damage(d, a);
        if(getHealth() <= 0) {
            if(getGrid() != null)
                removeSelfFromGrid();
            a.addItem(Rock.class);
        }
    }

    @Override
    public boolean isFree() {
        return !secured;
    }

    @Override
    public void secure() {
        secured = true;
    }

    @Override
    public void destructibleAct() {

    }

    @Override
    public void place(Grid<Actor> grid, Location location) {
        if(getGrid() == null) putSelfInGrid(grid, location);
    }

    @Override
    public String getName() {
        return "Stone";
    }
}
