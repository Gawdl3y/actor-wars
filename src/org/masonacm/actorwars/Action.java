package org.masonacm.actorwars;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

import java.io.InputStream;

/**
 * Something to do
 * <p>Provides several base Action-generating static methods</p>
 * @author Christopher Krueger
 * @author Schuyler Cebulskie
 */
public abstract class Action {
    /**
     * Performs the Action on an ActiveActor
     * @param a The ActiveActor to perform the Action on
     */
    protected abstract void perform(ActiveActor a);

    /**
     * Gets the energy cost of the Action
     * @return The energy cost of the Action
     */
    public abstract int getCost();

    /**
     * Gets whether or not the Action is exclusive
     * <p>An ActiveActor may only perform one exclusive Action per turn.</p>
     * @return Whether or not the Action is exclusive
     */
    public abstract boolean isExclusive();

    /**
     * Gets any relevant data about the Action
     * @return Relevant data, such as moveTo locations, and other data passed into the Action's constructors
     */
    public abstract Object getData();

    /**
     * Returns the name of the Action, and any data for it
     * @return This Action's name
     */
    @Override
    public abstract String toString();


    /**
     * Waits
     * @return The resulting Action to use
     */
    public static Action halt() {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                //wait
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return true;
            }

            @Override
            public Object getData() {
                return null;
            }

            @Override
            public String toString() {
                return "Halt()";
            }
        };
    }

    /**
     * Gives an amount of an item to the Actor in front of the Actor
     * @param e   The item to give
     * @param num The number to give
     * @return The resulting Action to use
     */
    public static Action pass(final Class<?> e, final int num) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(a.isFacing(ActiveActor.class)) {
                    if(a.isHolding(e)) {
                        if(a.getItemCount(e) >= num) {
                            a.removeItem(e, num);
                            if(a.getFacing() instanceof ActiveActor) ((ActiveActor) a.getFacing()).addItem(e, num);
                        }
                    }
                }
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return e;
            }

            @Override
            public String toString() {
                return "Pass(" + (e != null ? e.toString() : "null") + ")";
            }
        };
    }

    /**
     * Turns the Actor to a direction
     * @param direction The direction to turn to
     * @return The resulting Action to use
     * @see org.masonacm.actorwars.Action#turn(ModifiableInteger)
     */
    public static Action turn(final int direction) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                a.setDirection(direction);
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return direction;
            }

            @Override
            public String toString() {
                return "Turn(" + direction + ")";
            }
        };
    }

    /**
     * Turns the Actor to a direction
     * @param direction The direction to turn to
     * @return The resulting Action to use
     */
    public static Action turn(final ModifiableInteger direction) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                a.setDirection(direction.getValue());
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return direction;
            }

            @Override
            public String toString() {
                return "Turn(" + (direction != null ? direction.toString() : "null") + ")";
            }
        };
    }

    /**
     * Moves the Actor forward
     * @return The resulting Action to use
     */
    public static Action move() {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(a.canMove()) {
                    a.move();
                }
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return true;
            }

            @Override
            public Object getData() {
                return null;
            }

            @Override
            public String toString() {
                return "Move()";
            }
        };
    }

    /**
     * Does scaled damage to the DestructibleActor in front of the Actor
     * @param energy Scale for the damage
     * @return The resulting Action to use
     */
    public static Action attack(final int energy) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(a.getGrid().isValid(a.getLocation().getAdjacentLocation(a.getDirection()))) {
                    Actor b = a.getGrid().get(a.getLocation().getAdjacentLocation(a.getDirection()));
                    if(b instanceof DestructibleActor) {
                        if(a.getHealth() * 10 + a.getEnergy() >= energy) {
                            ((DestructibleActor) b).damage((int) (Math.pow(energy + 4, .5) - 2), a);

                            a.energy -= a.energy - getCost();
                        }
                    }
                }
            }

            @Override
            public int getCost() {
                return energy;
            }

            @Override
            public boolean isExclusive() {
                return true;
            }

            @Override
            public Object getData() {
                return energy;
            }

            @Override
            public String toString() {
                return "Attack(" + energy + ")";
            }
        };
    }

    /**
     * Heals the Actor in front of the Actor
     * @param scalar Scale to heal by
     * @return The resulting Action to use
     */
    public static Action heal(final int scalar) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(a.getGrid().isValid(a.getLocation().getAdjacentLocation(a.getDirection()))) {
                    Actor b = a.getGrid().get(a.getLocation().getAdjacentLocation(a.getDirection()));
                    if(b instanceof DestructibleActor) {

                        ((DestructibleActor) b).damage(-scalar, a);
                        a.energy = a.energy - getCost();
                    }
                }
            }

            @Override
            public int getCost() {
                return (5 + (5 * scalar)) * scalar;
            }

            @Override
            public boolean isExclusive() {
                return true;
            }

            @Override
            public Object getData() {
                return scalar;
            }

            @Override
            public String toString() {
                return "HealOther(" + scalar + ")";
            }
        };
    }

    /**
     * Heals the Actor
     * @param scalar Scale to heal by
     * @return The resulting Action to use
     */
    public static Action healSelf(final int energy) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(energy <= a.energy) {
                    a.damage(-((int) ((Math.pow((12 * energy) + 49, 0.5) - 7) / 6)), a);
                    a.energy = a.energy - getCost();
                } else {
                    a.damage(-((int) ((Math.pow((12 * a.energy) + 49, 0.5) - 7) / 6)), a);
                    a.energy = 0;
                }

            }

            @Override
            public int getCost() {
                return energy;
            }

            @Override
            public boolean isExclusive() {
                return true;
            }

            @Override
            public Object getData() {
                return energy;
            }

            @Override
            public String toString() {
                return "HealSelf(" + energy + ")";
            }
        };
    }

    /**
     * Places an object in front of the Actor
     * @param e The object to place
     * @return The resulting Action to use
     */
    public static Action place(final Class<?> e) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(a.isFacingValidLocation()) {
                    if(!a.isFacing(Actor.class)) {
                        if(a.isHolding(e)) {
                            try {
                                ((Placeable) e.newInstance()).place(a.getGrid(), a.getLocation().getAdjacentLocation(a.getDirection()));
                                a.removeItem(e);
                            } catch(InstantiationException e) {
                                e.printStackTrace();
                            } catch(IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                }
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return true;
            }

            @Override
            public Object getData() {
                return e;
            }

            @Override
            public String toString() {
                return "Place(" + (e != null ? e.toString() : "null") + ")";
            }
        };
    }

    /**
     * Moves the Movable in front of the Actor forward, and moves the Actor forward
     * @return The resulting Action to use
     */
    public static Action push() {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(a.getGrid().isValid(a.getLocation().getAdjacentLocation(a.getDirection()))) {
                    Actor b = a.getGrid().get(a.getLocation().getAdjacentLocation(a.getDirection()));
                    if(b instanceof Movable && b instanceof DestructibleActor) {
                        DestructibleActor c = (DestructibleActor) b;
                        c.setDirection(a.getDirection());
                        if(c.canMove()) {
                            c.move();
                            a.move();
                            a.energy = a.energy - getCost();
                        }

                    }
                }
            }

            @Override
            public int getCost() {
                return 10;
            }

            @Override
            public boolean isExclusive() {
                return true;
            }

            @Override
            public Object getData() {
                return null;
            }

            @Override
            public String toString() {
                return "Push()";
            }
        };
    }

    /**
     * Moves the Movable in front of the Actor backwards and moves the Actor backwards
     * @return The resulting Action to use
     */
    public static Action pull() {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(a.getGrid().isValid(a.getLocation().getAdjacentLocation(a.getDirection()))) {
                    Actor b = a.getGrid().get(a.getLocation().getAdjacentLocation(a.getDirection()));
                    if(b instanceof Movable && b instanceof DestructibleActor) {
                        DestructibleActor c = (DestructibleActor) b;
                        a.setDirection(a.getDirection() + 180);
                        c.setDirection(a.getDirection());
                        if(c.canMove()) {
                            a.move();
                            c.move();
                            a.setDirection(a.getDirection() + 180);
                            a.energy = a.energy - getCost();
                        }
                    }
                }
            }

            @Override
            public int getCost() {
                return 10;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return null;
            }

            @Override
            public String toString() {
                return "Pull()";
            }
        };
    }

    /**
     * Uses the Useable in front of the Actor
     * @return The resulting Action to use
     * @see org.masonacm.actorwars.Action#use(Class)
     */
    public static Action use() {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(a.isFacing(Useable.class)) {
                    ((Useable) a.getFacing()).use(a);
                }
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return true;
            }

            @Override
            public Object getData() {
                return null;
            }

            @Override
            public String toString() {
                return "Use()";
            }
        };
    }

    /**
     * Uses either an item that an Actor is holding, or uses the item in front of the Actor
     * @param e The class of the item/object
     * @return The resulting Action to use
     * @see org.masonacm.actorwars.Action#use()
     */
    public static Action use(final Class<?> e) {
        if(!Utils.isImplemented(e, Useable.class)) {
            throw new IllegalArgumentException("Class " + e.getName() + " Not member of " + Useable.class.getName());
        }

        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(a.isHolding(e)) {
                    try {
                        ((Useable) e.newInstance()).use(a);
                    } catch(InstantiationException r) {
                        r.printStackTrace();
                    } catch(IllegalAccessException r) {
                        r.printStackTrace();
                    }
                }
                if(a.isFacing(e)) {
                    ((Useable) a.getFacing()).use(a);
                }
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return true;
            }

            @Override
            public Object getData() {
                return e;
            }

            @Override
            public String toString() {
                return "Use(" + (e != null ? e.toString() : "null") + ")";
            }
        };
    }

    /**
     * Crafts an item and loses the resources required for the item, if the resources are available
     * @param e The type of item to craft
     * @return The resulting Action to use
     */
    public static Action craft(final Class<?> e) {
        if(!Utils.isImplemented(e, Craftable.class)) {
            throw new IllegalArgumentException("Class " + e.getName() + " Not member of " + Craftable.class.getName());
        }

        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                try {
                    Craftable cr = (Craftable) e.newInstance();
                    boolean cancraft = true;
                    for(Class<?> c : cr.getRecipe().mystuff) {
                        if(a.getItemCount(c) < cr.getRecipe().getItemCount(c)) {
                            cancraft = false;
                        }
                    }
                    if(cancraft) {
                        for(Class<?> c : cr.getRecipe().mystuff) {
                            a.removeItem(c, cr.getRecipe().getItemCount(c));
                        }
                        a.addItem(e);
                    }
                } catch(InstantiationException r) {
                    r.printStackTrace();
                } catch(IllegalAccessException r) {
                    r.printStackTrace();
                }
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return e;
            }

            @Override
            public String toString() {
                return "Craft(" + (e != null ? e.toString() : "null") + ")";
            }
        };
    }

    /**
     * Creates a new instance of an Actor in front of the Actor
     * @param e The type of Actor to spawn
     * @return The resulting Action to use
     */
    public static Action spawn(final Class<?> e) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(a.isFacingValidLocation() && a.getFacing() == null) {
                    if(a.energy >= getCost()) {
                        try {
                            Actor b = (Actor) e.newInstance();
                            b.putSelfInGrid(a.getGrid(), a.getLocation().getAdjacentLocation(a.getDirection()));
                        } catch(InstantiationException r) {
                            r.printStackTrace();
                        } catch(IllegalAccessException r) {
                            r.printStackTrace();
                        }
                        a.energy = a.energy - 400;
                    }
                }
            }

            @Override
            public int getCost() {
                return 400;
            }

            @Override
            public boolean isExclusive() {
                return true;
            }

            @Override
            public Object getData() {
                return e;
            }

            @Override
            public String toString() {
                return "Spawn(" + (e != null ? e.toString() : "null") + ")";
            }
        };
    }

    /**
     * Actor speech
     * @param text The text to say
     * @return The resulting Action to use
     */
    public static Action say(final String text) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                String[] parts = a.getClass().getName().split("\\.");
                System.out.println(parts[parts.length - 1] + " says: \"" + text + "\"");
                if(text.toLowerCase().contains("spanish inquisition") && !text.toLowerCase().contains("nobody expects")) {
                    Inquisition i = new Inquisition(a.getLocation());
                    i.putSelfInGrid(a.getGrid(), new Location(a.getLocation().getRow(), 0));
                }
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return text.toLowerCase().contains("spanish inquisition") && !text.toLowerCase().contains("nobody expects");
            }

            @Override
            public Object getData() {
                return text;
            }

            @Override
            public String toString() {
                return "Say(\"" + text + "\")";
            }
        };
    }

    /**
     * Play an MP3 sound
     * @param sound The stream of the sound to play
     * @return The resulting Action to use
     */
    public static Action playSound(final InputStream sound) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                //MP3.play(sound);
            }

            @Override
            public int getCost() {
                return 5;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return sound;
            }

            @Override
            public String toString() {
                return "PlaySound(" + (sound != null ? sound.toString() : "null") + ")";
            }
        };
    }

    /**
     * Inverts a ModifiableBoolean
     * @param value The value to invert
     * @return The resulting Action to use
     */
    public static Action toggle(final ModifiableBoolean value) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                value.invert();
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return value;
            }

            @Override
            public String toString() {
                return "Toggle(" + (value != null ? value.toString() : "null") + ")";
            }
        };
    }

    /**
     * Sets the value of a ModifiableBoolean
     * @param value    The value to change
     * @param newValue The Value to change to
     * @return The resulting Action to use
     */
    public static Action set(final ModifiableBoolean value, final ModifiableBoolean newValue) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                value.setValue(newValue);
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return new ModifiableBoolean[]{value, newValue};
            }

            @Override
            public String toString() {
                return "Set(" + (value != null ? value.toString() : "null")
                        + ", " + (newValue != null ? newValue.toString() : "null") + ")";
            }
        };
    }

    /**
     * Increments a ModifiableInteger
     * @param value The value to increment
     * @return The resulting Action to use
     * @see org.masonacm.actorwars.Action#decrement(ModifiableInteger)
     */
    public static Action increment(final ModifiableInteger value) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                value.increment();
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return value;
            }

            @Override
            public String toString() {
                return "Increment(" + (value != null ? value.toString() : "null") + ")";
            }
        };
    }

    /**
     * Decrements a ModifiableInteger
     * @param value The value to increment
     * @return The resulting Action to use
     * @see org.masonacm.actorwars.Action#increment(ModifiableInteger)
     */
    public static Action decrement(final ModifiableInteger value) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                value.decrement();
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return value;
            }

            @Override
            public String toString() {
                return "Decrement(" + (value != null ? value.toString() : "null") + ")";
            }
        };
    }

    /**
     * Sets the value of a ModifiableInteger
     * @param value    The value to change
     * @param newValue The Value to change to
     * @return The resulting Action to use
     */
    public static Action set(final ModifiableInteger value, final ModifiableInteger newValue) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                value.setValue(newValue);
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;
            }

            @Override
            public Object getData() {
                return new ModifiableInteger[]{value, newValue};
            }

            @Override
            public String toString() {
                return "Set(" + (value != null ? value.toString() : "null")
                        + ", " + (newValue != null ? newValue.toString() : "null") + ")";
            }
        };
    }
}
