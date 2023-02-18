package fr.unice.polytech.si3.qgl.les_genies.game.logic;

import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicDistanceTest {

    LogicDistance ld = new LogicDistance(5,6);

    @Test
    void getXTest() {
        assertEquals(5,ld.getX());
    }

    @Test
    void setX() {
        ld.setX(4);
        assertEquals(4,ld.getX());
    }

    @Test
    void getY() {
        assertEquals(6,ld.getY());
    }

    @Test
    void setY() {
        ld.setY(3);
        assertEquals(3,ld.getY());
    }

    @Test
    void setDistanceBetweenTest() {
        ld.setDistanceBetween(new Position(5,7,0),new Position(-2,13,0));
        assertEquals(9.219544457292887,ld.getNorm());
    }

}
