package org.masonacm.actorwars;

import info.gridworld.actor.Actor;

import java.awt.*;

public class HolyHandGrenadeBearer extends Peon {
    boolean charging = true;

    public HolyHandGrenadeBearer() {
        setColor(Color.BLACK);
    }

    @Override
    public void peonAct() {
        if(charging && isFacingValidLocation()) {
            if(!isFacing(Actor.class)) addAction(Action.move());
            else charging = false;
        }

        if(isFacing(DestructibleActor.class)) {
            getFacing().removeSelfFromGrid();
            removeSelfFromGrid();
            System.out.println("Boom!");
        } else if(!isFacingValidLocation()) {
            clearActions();
        } else if(getActionCount() == 0) {
            setDirection(getDirection() + 45);
        }
    }
}
