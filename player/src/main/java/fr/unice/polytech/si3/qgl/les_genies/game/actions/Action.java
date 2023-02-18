package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;

public abstract class Action {

    /**
     * action effectuer par un marin
     */
    protected int sailorId;

    /**
     * type de l'action
     */
    @JsonIgnore
    protected String type;

    /**
     * set un marin a l'action en verifiant que ces coordonnée match avec l'entité
     * @param sailor a sailor
     */
    public void setSailor(Sailor sailor, Entity entity) {
        this.sailorId = sailor.getId();
        sailor.work();
        entity.getUsedBy(sailor);
    }
    /**
     * return un String qui reprente au format JSON
     * une action
     * @return Json de l'action
     */
    @Override
    public String toString() {
        return "{" +
                "\"sailorId\": " + sailorId +
                ",\"type\": \"" + type + "\"" +
                '}';
    }
}
