package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;

public class ActRudder extends Action{

    private final double orientation;

    public ActRudder(Sailor sailor,Entity entity,double orientation){
        this.type = "TURN";
        this.orientation = orientation;
        setSailor(sailor,entity);
    }

    public double getOrientation(){
        return orientation;
    }

    @Override
    public void setSailor(Sailor sailor, Entity entity) {
        this.sailorId = sailor.getId();
        sailor.work();
        entity.getUsedBy(sailor);
    }

    @Override
    public String toString() {
        return "{" +
                "\"sailorId\": " + sailorId +
                ",\"type\": \"" + type + "\"" +
                ",\"rotation\": " + orientation +
                '}';
    }
}
