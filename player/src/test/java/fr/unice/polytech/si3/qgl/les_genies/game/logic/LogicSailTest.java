package fr.unice.polytech.si3.qgl.les_genies.game.logic;

import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicSailTest {

    Ship ship = new Ship();
    Wind wind = new Wind();
    LogicSail ls;

    @Test
    void canOpenSailWindBehind() {

        ship.setPosition(new Position(0,0,0));
        wind.setOrientation(0.0);

        ls = new LogicSail(ship,wind);
        ls.setWindAngle(wind.getOrientation());

        assertTrue(ls.canOpenSail());
    }

    @Test
    void canOpenSailWindForward() {

        ship.setPosition(new Position(0,0,0));
        wind.setOrientation(Math.PI);

        ls = new LogicSail(ship,wind);
        ls.setWindAngle(wind.getOrientation());

        assertFalse(ls.canOpenSail());
    }

    @Test
    void canOpenSailWindPerpendicular() {

        ship.setPosition(new Position(0,0,0));
        wind.setOrientation(Math.PI/2);

        ls = new LogicSail(ship,wind);
        ls.setWindAngle(wind.getOrientation());

        assertFalse(ls.canOpenSail());
    }

    @Test
    void canOpenSailWindNegativeForward() {

        ship.setPosition(new Position(0,0,0));
        wind.setOrientation(-Math.PI);

        ls = new LogicSail(ship,wind);
        ls.setWindAngle(wind.getOrientation());

        assertFalse(ls.canOpenSail());
    }

    @Test
    void canOpenSailWindNegativePerpendicular() {

        ship.setPosition(new Position(0,0,0));
        wind.setOrientation(-Math.PI/2);

        ls = new LogicSail(ship,wind);
        ls.setWindAngle(wind.getOrientation());

        assertFalse(ls.canOpenSail());
    }

    @Test
    void canOpenSailWindSomewhereForward() {

        ship.setPosition(new Position(0,0,0));
        wind.setOrientation(3*Math.PI/4);

        ls = new LogicSail(ship,wind);
        ls.setWindAngle(wind.getOrientation());

        assertFalse(ls.canOpenSail());
    }

    @Test
    void canOpenSailWindSomewhereBehind() {

        ship.setPosition(new Position(0,0,0));
        wind.setOrientation(-Math.PI/4);

        ls = new LogicSail(ship,wind);
        ls.setWindAngle(wind.getOrientation());

        assertTrue(ls.canOpenSail());
    }
}
