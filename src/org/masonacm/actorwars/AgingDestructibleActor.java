package org.masonacm.actorwars;

public abstract class AgingDestructibleActor extends DestructibleActor {
    protected int age = 0;

    public AgingDestructibleActor() {
        super();
    }

    public AgingDestructibleActor(int hp) {
        super(hp);
    }

    /**
     * Increments the age of the AgingDestructibleActor
     */
    protected void age() {
        if(age < Integer.MAX_VALUE) age++; else age = 0;
    }

    /**
     * Gets the age of the AgingDestructibleActor
     * @return The age of the AgingDestructibleActor
     */
    public int getAge() {
        return age;
    }

    @Override
    public void destructibleAct() {
        age();
    }
}
