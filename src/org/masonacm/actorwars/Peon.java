package org.masonacm.actorwars;

import com.gawdl3y.util.DynamicValue;
import com.gawdl3y.util.ModifiableBoolean;
import com.gawdl3y.util.ModifiableLocation;
import com.gawdl3y.util.ModifiableValue;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Peon extends ActiveActor {
    private ArrayList<Action> actions;

    /**
     * This method is equivalent to act(), extend this when making a Peon. It is called once per tick, just like the act() function
     */
    public abstract void peonAct();

    public Peon() {
        super(100);//Peons have 100 energy to start with.
        actions = new ArrayList<Action>();
    }

    /**
     * Adds an Action to the end of the Action queue
     * @param action The Action to add
     * @return true
     */
    protected final boolean addAction(Action action) {
        return actions.add(action);
    }

    /**
     * Adds an Action at a specific position in the Action queue
     * @param index  The position to place the Action at
     * @param action The Action to add
     */
    protected final void addAction(int index, Action action) {
        actions.add(index, action);
    }

    /**
     * Adds a Collection of Actions to the end of the Action queue
     * @param actions The Collection of Actions to add
     * @return true if the Action queue changed
     */
    protected final boolean addActions(Collection<Action> actions) {
        return this.actions.addAll(actions);
    }

    /**
     * Adds a Collection of Actions at a specified position in the Action queue
     * @param index   The position to add the actions at
     * @param actions The Collection of Actions to add
     * @return true if the Action queue changed
     */
    protected final boolean addActions(int index, Collection<Action> actions) {
        return this.actions.addAll(index, actions);
    }

    /**
     * Removes an Action from the Action queue
     * @param action The Action to remove
     * @return true if the Action queue contained the Action
     */
    protected final boolean removeAction(Action action) {
        return actions.remove(action);
    }

    /**
     * Removes an Action from the Action queue
     * @param index The position of the Action to remove
     * @return The removed Action
     */
    protected final Action removeAction(int index) {
        return actions.remove(index);
    }

    /**
     * Removes a Collection of Actions from the Action queue
     * @param actions The Collection of Actions to remove
     * @return true if the Action queue contained the Actions
     */
    protected final boolean removeActions(Collection<Action> actions) {
        return this.actions.removeAll(actions);
    }

    /**
     * Clears the Action queue
     */
    protected final void clearActions() {
        actions.clear();
    }

    /**
     * Tests if the Action queue contains an Action
     * @param action The Action to test for
     * @return true if the Action queue contains the specified Action instance
     */
    protected final boolean containsAction(Action action) {
        return actions.contains(action);
    }

    /**
     * Tests if the Action queue contains any Actions
     * @return true if the Action queue isn't empty
     */
    protected final boolean hasActions() {
        return !actions.isEmpty();
    }

    /**
     * Gets the Action at the specified position of the Action queue
     * @param index The position of the Action to retrieve
     * @return
     */
    protected final Action getAction(int index) {
        return actions.get(index);
    }

    /**
     * Sets the Action at a specified position in the Action queue
     * @param index  The position of the Action to set
     * @param action The Action to replace it with
     * @return The old Action
     */
    protected final Action setAction(int index, Action action) {
        return actions.set(index, action);
    }

    /**
     * Gets the number of Actions in the Action queue
     * @return The number of Actions in the Action queue
     */
    protected final int getActionCount() {
        return actions.size();
    }

    /**
     * Gets the Action queue
     * @return The Action queue
     */
    protected final ArrayList<Action> getActions() {
        return actions;
    }

    /**
     * This method is is called once per tick to do the overhead for the Peon class (it is automatic)
     */
    public final void activeAct() {
        peonAct();
        while(actions.contains(null)) actions.remove(null);
        while(actions.size() > 0) {
            if((!hasActed() && actions.get(0).isExclusive()) || !actions.get(0).isExclusive()) {
                perform(actions.remove(0));
            } else {
                break;
            }
        }
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "\n";
        s = s + "INV:" + this.myinv.toString() + "\nACTS:" + this.actions.toString() + "\nHEALTH:" + getHealth() + "\nENERGY:" + getEnergy();
        return s;
    }


    /**
     * Actor pauses and waits [c] turns before continuing
     * @param c The number of turns to wait
     */
    public static final Action count(final int c) {
        return new Action() {
            int myc = 0;
            boolean set = false;

            @Override
            protected void perform(ActiveActor a) {
                if(!set) {
                    set = true;
                    myc = c;
                }
                myc--;
                System.out.println("Counting:" + (c - myc));
                if(myc > 0) ((Peon) a).actions.add(0, this);
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
                return myc;
            }

            @Override
            public String toString() {
                return "Count(" + c + ")";
            }
        };
    }

    /**
     * Assembles a list of movement and turning orders to pathfind to location [l]
     * @param location The location to pathfind to
     */
    public static final Action moveToGradual(final DynamicValue<Location> location) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(location == null) return;
                if(a == null) return;
                if(a.getLocation() == null) return;
                if(a.getGrid() == null) return;

                ModifiableLocation target = new ModifiableLocation(location.getValue());
                if(!target.getValue().equals(a.getLocation())) {
                    ArrayList<Location> path = Pathfinder.findPath(a.getDynamicLocation(), target, a.getGrid());
                    if(path == null) return;

                    DynamicValue<Location> temp;
                    ((Peon) a).actions.add(0, Peon.conditionalAct(Utils.notAtLocation(a, target), Peon.moveToGradual(target)));
                    while(path.size() > 0) {
                        temp = (path.size() > 1)? (new ModifiableValue<Location>(path.get(path.size()-2))):(a.getDynamicLocation());
                        ((Peon) a).actions.add(0, Peon.conditionalAct(Utils.atLocation(a, temp), Action.move()));
                        ((Peon) a).actions.add(0, Action.turn(LocationFinder.dynamicDirectionTo(a.getDynamicLocation(), new ModifiableValue<Location>(path.get(path.size() - 1)))));
                        path.remove(path.size() - 1) ;
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
                return location;
            }

            @Override
            public String toString() {
                return "MoveToGradual(" + (location != null ? location.toString() : "null") + ")";
            }
        };
    }

    /**
     * Repeats action [myact] until [b].getValue() returns false
     * @param myact The Action to repeat
     * @param b     The flag for controlling the loop
     */
    public static final Action repeat(final Action myact, final ModifiableBoolean b) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(b.getValue()) {
                    ((Peon) a).actions.add(0, this);
                    ((Peon) a).actions.add(0, myact);
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
                return myact;
            }

            @Override
            public String toString() {
                return "Repeat(" + (myact != null ? myact.toString() : "null") + ", "
                        + (b != null ? b.toString() : "null") + ")";
            }
        };
    }

    /**
     * Performs action [ifaction] if [b] is true
     * @param b        The condition for executing [ifaction]
     * @param ifaction The Action to perform
     */
    public static final Action conditionalAct(final DynamicValue<Boolean> b, final Action ifaction) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(b.getValue()) {
                    ((Peon) a).actions.add(0, ifaction);
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
                return new Object[]{b, ifaction};
            }

            @Override
            public String toString() {
                return "ConditionalAct(" + (b != null ? b.toString() : "null") + ", "
                        + (ifaction != null ? ifaction.toString() : "null") + ")";
            }
        };
    }

    /**
     * Preforms action [ifaction] if [b] is true else preforms [elseaction]
     * @param b          The condition for executing [ifaction]
     * @param ifaction   The Action to perform if [b].getValue() returns true
     * @param elseaction The Action to perform if [b].getValue() returns false
     */
    public static Action conditionalAct(final DynamicValue<Boolean> b, final Action ifaction, final Action elseaction) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                if(b.getValue()) {
                    ((Peon) a).actions.add(0, ifaction);
                } else {
                    if(elseaction != null) {
                        ((Peon) a).actions.add(0, elseaction);
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
                return new Object[]{b, ifaction, elseaction};
            }

            @Override
            public String toString() {
                return "ConditionalAct(" + (b != null ? b.toString() : "null") + ", "
                        + (ifaction != null ? ifaction.toString() : "null") + ", "
                        + (elseaction != null ? elseaction.toString() : "null") + ")";
            }
        };
    }

    /**
     * Moves to an adjacent location of [l] then places [e] at location [l]
     * @param e The object to place at [l]
     * @param l The location to place [e] at
     */
    public static Action placeAt(final Class<?> e, final Location l) {
        return new Action() {
            @Override
            protected void perform(ActiveActor a) {
                ((Peon) a).actions.add(0, place(e));
                ((Peon) a).actions.add(0, turn(LocationFinder.dynamicDirectionTo(a.getDynamicLocation(), new ModifiableValue<Location>(l))));
                ((Peon) a).actions.add(0, Peon.moveToGradual(LocationFinder.findClosestEmptyAdjacentDynamicLocation(a.getDynamicLocation(), new ModifiableValue<Location>(l), a.getGrid())));
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
                return new Object[]{e, l};
            }

            @Override
            public String toString() {
                return "PlaceAt(" + (e != null ? e.toString() : "null") + ", "
                        + (l != null ? l.toString() : "null") + ")";
            }
        };
    }
}
