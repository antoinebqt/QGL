package fr.unice.polytech.si3.qgl.les_genies.game.orders;

import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.LogicRotation;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;

public class OrderRudder extends Order{

    public OrderRudder(Ship ship, LogicRotation logicRotation, int sailors) {
        super(ship);
        if (logicRotation.getAngleRudder() != 0) {
            Entity theRudder = getFirstEntityByType("rudder");
            if(theRudder != null && aSailorIsFree(sailors)) {
                usefulEntity.add(theRudder);
            }
        }
    }
}
