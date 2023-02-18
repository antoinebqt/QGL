package fr.unice.polytech.si3.qgl.les_genies.game.shapes;

import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolygoneTest {
    Polygone p;
    Point[] vertices;

    @BeforeEach
    void PolygoneTest(){
        vertices = new Point[6];
        vertices[0] = new Point(0,0);
        vertices[1] = new Point(10,0);
        vertices[2] = new Point(0,10);
        vertices[3] = new Point(100,0);
        vertices[4] = new Point(0,100);
        vertices[5] = new Point(1000,1000);

        p = new Polygone(vertices);
    }

    @Test
    void valueTest(){
        assertArrayEquals(vertices, p.getVertices());

        assertEquals(0,p.getOrientation());
    }

    @Test
    void isOnShape(){
        Position zero = new Position(0,0,0);
        assertTrue(p.isOnShape(zero,zero));

        Position pos = new Position(1000,1000,0);
        assertFalse(p.isOnShape(zero, pos));
    }

}
