package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Sail;

/**
 * Action pour hisser la voile et se prendre le vent
 */
public class ActLiftSail extends Action{

    public ActLiftSail(Sailor sailor, Entity entity){
        this.type = "LIFT_SAIL";
        setSailor(sailor,entity);
    }
    @Override
    public void setSailor(Sailor sailor, Entity entity) {
        this.sailorId = sailor.getId();
        sailor.work();
        entity.getUsedBy(sailor);
        ((Sail)entity).changeStatesSail(true);
    }

    @Override
    public String toString() {
        return "{" +
                "\"sailorId\":" + sailorId +
                ", \"type\":\"" + type + "\"" +
                '}';
    }
}
