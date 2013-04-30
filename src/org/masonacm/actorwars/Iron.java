package org.masonacm.actorwars;


public class Iron extends IronOre implements Placeable, Resource {
    public Iron() {
        super(100);
    }

    @Override
    public String getName() {
        return "Iron";
    }
}
