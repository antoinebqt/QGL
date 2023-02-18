package fr.unice.polytech.si3.qgl.les_genies.game.crew;

import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Deck;
import fr.unice.polytech.si3.qgl.les_genies.game.actions.ActMove;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import java.util.ArrayList;
import java.util.List;

public class Shifting {

    private final Sailor[] sailors;

    private final Deck deck;

    public Shifting(Sailor[] sailors, Deck deck){
        this.sailors=sailors;
        this.deck=deck;
    }

    /**
     * take a list of entities that have to be activated, to move the boat
     * then we used to see if there is a sailor on it, if none we take a sailor to move on it
     * if the list is empty, nothing append; there's no shift at all to do
     * @param listEntity list of useful entities that have no sailor on it
     * @return a list of order of shifting
     */
    public List<ActMove> shiftSailorToUsefulEntity(List<Entity> listEntity){
        List<ActMove> shiftOrder = new ArrayList<>();
        if(!listEntity.isEmpty()) {
            List<Sailor> sailorsToShift = new ArrayList<>();
            for (Sailor leS : sailors) {
                if (!leS.isOnEntity()) sailorsToShift.add(leS);
            }
            if(listEntity.size() > sailorsToShift.size()) Cockpit.addLog(Constants.MESSAGE_ERR_ENTITY_OVERFLOW);
            for (Entity entity : listEntity) {
                Sailor marin = entity.muchCloserSailor(sailorsToShift);
                if(marin != null && !marin.isOnEntity()) {
                    shiftOrder.add(goForThisEntity(marin, entity));
                }
            }
        }
        return shiftOrder;
    }

    /**
     * captain tell the sailor to go for this entity
     * its also set the sailor setOnEntity to true so another entity
     * cannot take this sailor even if he is the closest
     * if the sailor is on the entity, it returns a none ActMove
     * @param sailor a sailor
     * @param entity an entity
     */
    public ActMove goForThisEntity(Sailor sailor, Entity entity){
        int xDistance = entity.getX()-sailor.getX();
        int yDistance = entity.getY()-sailor.getY();
        if(xDistance != 0 || yDistance != 0) {
            entity.setFocusedBy(sailor);
            sailor.setOnEntity(true);
            int[] tabXY = sailor.move(xDistance,yDistance, deck);
            if (tabXY.length != 0){
                return new ActMove(tabXY[0],tabXY[1],sailor);
            }
            return new ActMove(0,0,sailor);
        }else return new ActMove(0,0,sailor);
    }
}
