package org.masonacm.actorwars;

import info.gridworld.actor.Actor;

import java.util.ArrayList;

public abstract class ActiveActor extends DestructibleActor {
    protected int energy = 200;
    private boolean acted;
    protected Inventory myinv = new Inventory();

    public abstract void activeAct();

    public ActiveActor() {
        super(15);
        energy = 200;
        myinv = new Inventory();
    }

    public ActiveActor(int ep) {
        super(15);
        energy = ep;
    }

    public ActiveActor(int hp, int ep) {
        super(hp);
        energy = ep;
    }

    //returns true if actor is facing a valid grid location
    public final boolean isFacingValidLocation() {
        return getGrid().isValid(getLocation().getAdjacentLocation(getDirection()));
    }

    //returns the actor that is in front of this actor
    public final Actor getFacing() {
        if(isFacingValidLocation()) {
            return getGrid().get(getLocation().getAdjacentLocation(getDirection()));
        }
        return null;
    }

    //returns true if actor is facing an instance of [e]
    public final boolean isFacing(Class<?> e) {
        if(isFacingValidLocation()) {
            return e.isInstance(getGrid().get(getLocation().getAdjacentLocation(getDirection())));
        }
        return false;
    }

    //adds resource [e] to inventory
    protected void addItem(Class<?> e) {
        //System.out.println("active.addItem(adding to inventory): "+ e.getName());
        myinv.addItem(e);
    }

    protected void addItem(Class<?> e, int i) {
        while(i > 0) {
            myinv.addItem(e);
            i--;
        }
    }

    //removes resource [e] from inventory
    protected void removeItem(Class<?> e) {
        myinv.removeItem(e);
    }

    //removes [i]# of resource [e] from inventory
    protected void removeItem(Class<?> e, int i) {
        while(i > 0) {
            myinv.removeItem(e);
            i--;
        }
    }

    //returns true if actor has [e] in its inventory
    public boolean isHolding(Class<?> e) {
        return myinv.contains(e);
    }

    //returns the number of item [e] in actor's inventory
    public int getItemCount(Class<?> e) {
        return myinv.getItemCount(e);
    }

    //returns true if actor has the resources to craft [e]
    public final boolean canCraft(Class<?> e) {
        Craftable cr = null;
        try {
            cr = (Craftable) e.newInstance();
        } catch(InstantiationException e1) {
            e1.printStackTrace();
        } catch(IllegalAccessException e2) {
            e2.printStackTrace();
        }
        boolean cancraft = true;
        for(Class<?> c : cr.getRecipe().mystuff) {
            if(getItemCount(c) < cr.getRecipe().getItemCount(c)) {
                cancraft = false;
            }
        }
        return cancraft;
    }

    public ArrayList<Actor> scan(int range, Class<?> e) {
        ArrayList<Actor> a = new ArrayList<Actor>();
        int x = getLocation().getCol();
        int y = getLocation().getCol();

        return a;
    }

    //uses the {useable}  in front of this actor
    public final void use() {
        if(getGrid().isValid(getLocation().getAdjacentLocation(getDirection()))) {
            Actor b = getFacing();
            if(b instanceof Useable) {
                ((Useable) b).use(this);
            }
        }
    }

    public final void destructibleAct() {
        acted = false;
        activeAct();
        if(energy < 0) {
            damage(-(energy / 10), this);
        }
    }

    //actor preforms [a] if it has not preformed an exclusive action or if [a] is not excluive
    public final void perform(Action a) {
        if(a == null) return;
        if((a.isExclusive() && !acted) || !a.isExclusive()) {
            a.perform(this);
            if(a.isExclusive()) acted = true;
        }
    }

    public boolean hasActed() {
        return acted;
    }

    public int getEnergy() {
        return energy;
    }
}
