package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;

/**
 * represent the act of moving for a sailor
 * this action is done only if the sailor has moved through the move methode
 */
public class ActMove extends Action {

    /**
     * shifting distance for x
     */
    private final int xDistance;
    /**
     * shifting distance for y
     */
    private final int yDistance;

    public ActMove(int xDistance, int yDistance, Sailor sailor){
        this.sailorId = sailor.getId();
        this.type = "MOVING";
        this.xDistance =xDistance;
        this.yDistance =yDistance;
    }

    @Override
    public String toString() {
        return "{" +
                "\"sailorId\": " + sailorId +
                ",\"type\": \"" + type + "\"" +
                ",\"xdistance\": " + xDistance +
                ",\"ydistance\": " + yDistance +
                '}';
    }
}
