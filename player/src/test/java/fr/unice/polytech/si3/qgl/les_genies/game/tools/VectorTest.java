package fr.unice.polytech.si3.qgl.les_genies.game.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void testVectors(){
        Vector v = new Vector(new Position(0,10,0),new Position(10,0,0));
        assertEquals(10, v.getX());
        assertEquals(-10, v.getY());
        assertEquals(-Math.PI/4,v.getAngle());
    }

    @Test
    void getDistance_OK(){
        Vector vect = new Vector(new Position(5,0,0),new Position(2,0,0));
        assertEquals(3,vect.getDistance());
    }

    @Test
    void getDistance_ZERO(){
        Vector vect = new Vector(new Position(0,0,0),new Position(0,0,0));
        assertEquals(0,vect.getDistance());
    }

    @Test
    void getDistance_MauvaiseValeur(){
        Vector vect = new Vector(new Position(1,5,0),new Position(4,3,0));
        // x = 3 / y = -2
        assertEquals(Math.sqrt(13),vect.getDistance());
        assertNotEquals(1,vect.getDistance());
        assertNotEquals(0,vect.getDistance());
    }
}
