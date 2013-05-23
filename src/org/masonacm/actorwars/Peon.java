package org.masonacm.actorwars;

import com.gawdl3y.util.DynamicValue;
import com.gawdl3y.util.ModifiableBoolean;
import com.gawdl3y.util.ModifiableLocation;
import com.gawdl3y.util.ModifiableValue;
import info.gridworld.grid.Location;

import java.util.ArrayList;


public abstract class Peon extends ActiveActor {
    public ArrayList<Action> myactions;

    /**
     * This method is equivalent to act(), extend this when making a Peon. It is called once per tick, just like the act() function
     */
    public abstract void peonAct();

    public Peon() {
        super(100);//Peons have 100 energy to start with.
        myactions = new ArrayList<Action>();
    }

    /**
     * Adds Action [a] to bottom of this Peon's Action que
     * @param a The Action to add
     */
    public final void add(Action a) {
        myactions.add(a);
    }

    /**
     * This method is is called once per tick to do the overhead for the Peon class (it is automatic)
     */
    public final void activeAct() {

        peonAct();
      //  System.out.println("Action Que pre execute:"+myactions);
      //  System.out.println("Action Que hasactedpre:" + hasActed());
        while(myactions.contains(null))
            myactions.remove(null);
        while(myactions.size() > 0) {
            if((!hasActed() && myactions.get(0).isExclusive()) || !myactions.get(0).isExclusive()) {
                //System.out.println("peon.actvact(Action to preform): "+myactions.get(0).getClass().getName());
               // System.out.println("Preforming:" + myactions.get(0));
                perform(myactions.remove(0));
             //   System.out.println("Action Que post execute:"+myactions);
             //   System.out.println("Action Que post hasacted:" + hasActed());
            } else {
                break;
            }
        }
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "\n";
        s = s + "INV:" + this.myinv.toString() + "\nACTS:" + this.myactions.toString() + "\nHEALTH:" + getHealth() + "\nENERGY:" + getEnergy();
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
                if(myc > 0)
                    ((Peon) a).myactions.add(0, this);
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
                //System.out.println("We're pathing");
                if(location == null) return;
                if(a == null) return;
                if(a.getLocation() == null) return;
                if(a.getGrid() == null) return;

                ModifiableLocation target = new ModifiableLocation(location.getValue());
                if(!target.getValue().equals(a.getLocation())) {
                    ArrayList<Location> path = Pathfinder.findPath(a.getDynamicLocation(), target, a.getGrid());
                    if(path == null) return;

                    DynamicValue<Location> temp;
                    ((Peon) a).myactions.add(0, Peon.conditionalAct(Utils.notAtLocation(a, target), Peon.moveToGradual(target)));
                   // ((Peon) a).myactions.add(0, Peon.conditionalAct(Utils.notAtLocation(a, target), Action.move()));
                  //  ((Peon) a).myactions.add(0, Peon.conditionalAct(Utils.notAtLocation(a, target), Action.turn(LocationFinder.dynamicDirectionTo(a.getDynamicLocation(), target))));
                    while(path.size() > 0) {
                        temp = (path.size()>1)? (new ModifiableValue<Location>(path.get(path.size()-2))):(a.getDynamicLocation());
                        //((Peon) a).myactions.add(0, Peon.conditionalAct(Utils.notAtLocation(a, temp), Action.say("I failed")));
                        ((Peon) a).myactions.add(0, Peon.conditionalAct(Utils.atLocation(a, temp), Action.move()));
                       // ((Peon) a).myactions.add(0, Peon.conditionalAct(Utils.atLocation(a, temp), Action.say(":" + path.size())));

                        ((Peon) a).myactions.add(0,Action.turn(LocationFinder.dynamicDirectionTo(a.getDynamicLocation(), new ModifiableValue<Location>(path.get(path.size() - 1)))));

                           path.remove(path.size()-1) ;
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
                    ((Peon) a).myactions.add(0, this);
                    ((Peon) a).myactions.add(0, myact);
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
                    ((Peon) a).myactions.add(0, ifaction);
                }

            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;//b.getValue()&&ifaction.isExclusive();
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
                    ((Peon) a).myactions.add(0, ifaction);
                } else {
                    if(elseaction != null) {
                        ((Peon) a).myactions.add(0, elseaction);
                    }
                }

            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public boolean isExclusive() {
                return false;//(b.getValue()&&ifaction.isExclusive())||(!b.getValue()&&elseaction.isExclusive());
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
                ((Peon) a).myactions.add(0, place(e));
                ((Peon) a).myactions.add(0, turn(LocationFinder.dynamicDirectionTo(a.getDynamicLocation(), new ModifiableValue<Location>(l))));
                ((Peon) a).myactions.add(0, Peon.moveToGradual(LocationFinder.findClosestEmptyAdjacentDynamicLocation(a.getDynamicLocation(), new ModifiableValue<Location>(l), a.getGrid())));
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
