package fr.unice.polytech.si3.qgl.les_genies.game;

import fr.unice.polytech.si3.qgl.les_genies.game.tools.Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    void translation_test(){
        Coordinates c = new Coordinates(0,0);
        c.translation(5,5);
        assertEquals(5,c.getCoordinateY());
        assertEquals(5,c.getCoordinateX());
        c.translation(-4,0);
        assertEquals(1,c.getCoordinateX());
    }

    @Test
    void distance_Test(){
        Coordinates a = new Coordinates(3,3);
        Coordinates b = new Coordinates(1,0);
        int distance = a.distance(b);
        assertEquals(5,distance);
        assertEquals(5,b.distance(a));
    }
}