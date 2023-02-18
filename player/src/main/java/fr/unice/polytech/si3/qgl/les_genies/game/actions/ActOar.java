package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;

public class ActOar extends Action{
    public ActOar(Sailor sailor, Entity entity){
        this.type = "OAR";
        setSailor(sailor,entity);
    }
}
