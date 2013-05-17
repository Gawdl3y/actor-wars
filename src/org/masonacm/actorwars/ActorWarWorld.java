package org.masonacm.actorwars;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;

/**
 * The world for ActorWars
 */
public class ActorWarWorld extends ActorWorld {
    public ActorWarWorld() {
        setGrid(new BoundedGrid<Actor>(42, 42));

        int i;
        for(i = 0; i < 40; i++) add(new Tree());
        for(i = 0; i < 10; i++) add(new Wheat());
        for(i = 0; i < 10; i++) add(new Rock());
        for(i = 0; i < 5; i++) add(new IronOre());
        for(i = 0; i < 2; i++) add(new Quarry());
    }
}
