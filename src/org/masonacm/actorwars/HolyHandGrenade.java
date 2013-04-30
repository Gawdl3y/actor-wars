package org.masonacm.actorwars;

import info.gridworld.actor.Actor;


public class HolyHandGrenade implements Useable, Resource {
    @Override
    public void use(Active a) {
        if(a.isFacingValidLocation()) {
            if(!a.isFacing(Actor.class)) {
                HolyHandGrenadeBearer h = new HolyHandGrenadeBearer();
                h.putSelfInGrid(a.getGrid(), a.getLocation().getAdjacentLocation(a.getDirection()));
            }
        }
    }

    public static void give(Active a) {
        a.addItem(HolyPin.class);
    }

    @Override
    public String getName() {
        return "The Holy Hand Grenade of Antioch";
    }


}
