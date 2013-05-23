package org.masonacm.actorwars;

import com.gawdl3y.util.ModifiableLocation;
import info.gridworld.grid.Location;

import java.awt.*;

public class Inquisition extends Peon {
    public Inquisition(Location l) {
        setColor(Color.RED);
        setDirection(90);
        addAction(Peon.moveToGradual(LocationFinder.findClosestEmptyAdjacentDynamicLocation(getDynamicLocation(), new ModifiableLocation(l), getGrid())));
        addAction(Action.halt());
        addAction(Action.say("Nobody expects the Spanish Inquisition!"));
    }

    @Override
    public void peonAct() {

    }
}
