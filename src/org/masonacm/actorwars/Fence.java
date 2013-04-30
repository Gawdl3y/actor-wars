package org.masonacm.actorwars;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;


public class Fence extends Destructible implements Craftable, Placeable {
    public Fence() {
        super(20); //fence has 20 hp
        setColor(new Color(109, 36, 0));
    }

    @Override
    public String getName() {
        return "Fence";
    }

    @Override
    public void place(Grid<Actor> g, Location l) {
        putSelfInGrid(g, l);

    }

    @Override
    public Inventory getRecipe() {
        Inventory i = new Inventory();
        i.addItem(Wood.class);
        i.addItem(Wood.class);
        i.addItem(Wood.class);
        return i;
    }

    @Override
    public void actDestructive() {

    }
}
