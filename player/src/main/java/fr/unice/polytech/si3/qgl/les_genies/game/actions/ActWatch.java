package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;

public class ActWatch extends Action {

    public ActWatch(Sailor sailor, Entity entity){
        this.type = "USE_WATCH";
        setSailor(sailor,entity);
    }
}
