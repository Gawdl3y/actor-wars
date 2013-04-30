package org.masonacm.actorwars;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;

public class Wheat extends Destructible implements Resource, Useable, Placeable {
    private int age = 0;

    public Wheat() {
        super(4);
        setColor(new Color(109, 76, 0));
    }

    @Override
    public void actDestructive() {
        if(age <= 200) age++;
        if(age == 200) {
            if(getGrid().getEmptyAdjacentLocations(getLocation()).size() > 0) {
                Location l = getGrid().getEmptyAdjacentLocations(getLocation()).get((int) (Math.random() * (getGrid().getEmptyAdjacentLocations(getLocation()).size() - 1)));
                (new Wheat()).putSelfInGrid(getGrid(), l);
            }
            age = 0;
        }
    }

    @Override
    public void use(Active a) {
        if(getGrid() == null) {
            a.energy += 200;
            a.removeItem(this.getClass());
        }
    }

    @Override
    public void damage(int d, Active a) {
        super.damage(d, a);
        if(getHealth() <= 0) {
            if(getGrid() != null) removeSelfFromGrid();
            if(age >= 10) a.addItem(Wheat.class);
            if(age >= 20) a.addItem(Wheat.class);
        }
    }

    @Override
    public void place(Grid<Actor> g, Location l) {
        putSelfInGrid(g, l);
    }

    @Override
    public String getName() {
        return "Wheat";
    }

    public int getAge() {
        return age;
    }
}
