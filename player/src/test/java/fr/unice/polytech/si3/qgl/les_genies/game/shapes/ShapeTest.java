package fr.unice.polytech.si3.qgl.les_genies.game.shapes;

import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {

    @Test
    void isOnShapeTestV2() {
        Shape shape = new Shape();
        Position positionShape = new Position(500,500, 0);
        assertTrue(shape.isOnShape(positionShape, new Position(500,500, 0)));
        assertFalse(shape.isOnShape(positionShape, new Position(400,500, 0)));
        assertFalse(shape.isOnShape(positionShape, new Position(600,500, 0)));
        assertFalse(shape.isOnShape(positionShape, new Position(500,400, 0)));
        assertFalse(shape.isOnShape(positionShape, new Position(500,600, 0)));
    }

    @Test
    void isOnShapeTest() {
        Shape shape = new Shape();
        Position positionShape = new Position(1,1, 0);
        assertTrue(shape.isOnShape(positionShape, new Position(1,1, 0)));
        assertFalse(shape.isOnShape(positionShape, new Position(1000,0, 0)));
    }

    @Test
    void getTest(){
        Shape shape = new Shape();
        assertEquals(0,shape.getHeight());
        assertEquals(0,shape.getOrientation());
        assertEquals(0,shape.getRadius());
        assertEquals(0,shape.getWidth());
        assertEquals(0,shape.getVertices().length);
        assertEquals(0.0,shape.getHeight());
        assertEquals(0.0,shape.getOrientation());
        assertEquals(0.0,shape.getRadius());
        assertEquals(0.0,shape.getWidth());
        assertEquals(0.0,shape.getVertices().length);
    }
}
