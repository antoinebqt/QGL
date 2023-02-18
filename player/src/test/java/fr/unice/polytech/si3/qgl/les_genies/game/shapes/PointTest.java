package fr.unice.polytech.si3.qgl.les_genies.game.shapes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    @Test
    void setPoint(){
        Point point = new Point(0,0);
        assertEquals(0,point.getX());
        assertEquals(0,point.getY());

        point = new Point(100,10);
        assertEquals(100,point.getX());
        assertEquals(10,point.getY());
    }
}
