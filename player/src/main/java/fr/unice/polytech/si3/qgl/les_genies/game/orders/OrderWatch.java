package fr.unice.polytech.si3.qgl.les_genies.game.orders;

import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;

public class OrderWatch extends Order{

    public OrderWatch(Ship ship, int round, int sailors) {
        super(ship);
        Entity theWatch = getFirstEntityByType("watch");
        if (theWatch != null && wantToUseWatch(round) && aSailorIsFree(sailors)) {
            usefulEntity.add(theWatch);
        }
    }

    public boolean wantToUseWatch(int round){
        if (round == 0) return true;
        return round % 10 == 0;
    }
}
