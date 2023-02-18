package fr.unice.polytech.si3.qgl.les_genies.game.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinatesTests {
    @Test
    void changeCoordinates(){
        Coordinates c = new Coordinates(0,0);
        assertEquals(0, c.getCoordinateX());
        assertEquals(0, c.getCoordinateY());

        c.setCoordinateX(10);
        c.setCoordinateY(20);

        assertEquals(10, c.getCoordinates()[0]);
        assertEquals(20, c.getCoordinates()[1]);
    }
}
