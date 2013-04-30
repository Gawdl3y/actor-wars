package org.masonacm.actorwars;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;

public class Rock extends Destructible implements Movable, Placeable, Resource {
    private boolean secured = false;

    public Rock() {
        super(50);
        setColor(Color.GRAY);
    }

    protected Rock(int hp) {
        super(hp);
    }

    @Override
    public void damage(int d, Active a) {
        super.damage(d, a);
        if(getHealth() <= 0) {
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
    public void actDestructive() {

    }

    @Override
    public void place(Grid<Actor> g, Location l) {
        putSelfInGrid(g, l);

    }

    @Override
    public String getName() {
        return "Stone";
    }
}
