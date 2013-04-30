package org.masonacm.actorwars;


public class Axe implements Useable, Craftable {
    private int damage = 5;

    public Axe() {}

    public void use(Active a) {
        if(a.isFacing(Wood.class)) {
            ((Destructible) a.getFacing()).damage(damage, a);
        }
    }

    @Override
    public String getName() {
        return "Axe";
    }

    @Override
    public Inventory getRecipe() {
        Inventory i = new Inventory();
        i.addItem(Wood.class);
        i.addItem(Wood.class);
        i.addItem(Wood.class);
        return i;
    }
}
