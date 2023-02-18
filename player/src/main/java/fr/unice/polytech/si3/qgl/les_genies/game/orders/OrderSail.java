package fr.unice.polytech.si3.qgl.les_genies.game.orders;

import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.LogicDistance;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.LogicSail;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;

import java.util.Objects;

public class OrderSail extends Order{

    public OrderSail(Ship ship, RegattaGoal goal, Wind wind, int sailors) {
        super(ship);

        LogicSail logicSail = new LogicSail(ship,wind);
        boolean canOpenSail = logicSail.canOpenSail();

        for (Entity entity : ship.getEntities()) {
            if (Objects.equals(entity.getType(), "sail") && aSailorIsFree(sailors)){
                if (!doableUsingOar(goal)){
                    /**
                     * Si on peut pas utiliser les rames
                     * Si il faut ouvrir la voile et que celle ci est fermé
                     * OU si il faut pas ouvrir la voile et que celle ci est ouverte
                     */
                    if (needSwitch(canOpenSail,entity)) usefulEntity.add(entity);
                } else if (entity.isOpenned()) {
                    //Si on peut utiliser les rames et que les voiles sont ouvertes -> on les fermes
                    usefulEntity.add(entity);
                }
            }
        }
    }

    private boolean needSwitch (boolean canOpenSail, Entity entity){
        return ((canOpenSail && !entity.isOpenned()) || (!canOpenSail && entity.isOpenned()));
    }

    /**
     * Return true if the ship can go in the checkpoint only with the oars
     * Else return false
     *
     * @return boolean
     */
    public boolean doableUsingOar(RegattaGoal goal) {
        LogicDistance ld = new LogicDistance(0,0);
        ld.setDistanceBetween(ship.getPosition(),goal.getCurrentCheckpoint().getPosition());
        return (165.0) > ld.getNorm();
    }
}
