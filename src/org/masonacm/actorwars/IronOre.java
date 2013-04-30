package org.masonacm.actorwars;

import java.awt.*;


public class IronOre extends Rock {
    public IronOre() {
        setColor(new Color(100, 30, 0));
    }

    protected IronOre(int hp) {
        super(hp);
    }

    @Override
    public void damage(int d, Active a) {
        super.damage(d, a);
        if(getHealth() <= 0) {
            removeSelfFromGrid();
            a.addItem(Iron.class);
        }
    }
}
