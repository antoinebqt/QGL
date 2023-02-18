package fr.unice.polytech.si3.qgl.les_genies.game.logic;

import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;

public class LogicSail {

    Ship ship;
    double windAngle;

    public LogicSail(Ship ship, Wind wind){
        this.ship = ship;
        this.windAngle = wind.getOrientation();
    }


    public boolean canOpenSail() {
        double shipAngle = ship.getPosition().getOrientation();
        double maxWindAngle = shipAngle + Math.PI/2;
        double minWindAngle = shipAngle - Math.PI/2;

        return windAngle < maxWindAngle && windAngle > minWindAngle;

    }

    public void setWindAngle(double orientation) {
        this.windAngle = orientation;
    }
}
