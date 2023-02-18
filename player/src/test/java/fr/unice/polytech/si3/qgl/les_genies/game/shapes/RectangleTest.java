package fr.unice.polytech.si3.qgl.les_genies.game.shapes;

import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    Rectangle r;

    @BeforeEach
    void RectangleTest(){
        r = new Rectangle(10,10,0);
    }

    @Test
    void modifRect(){
        assertEquals(10,r.getHeight());
        assertEquals(10,r.getWidth());
        assertEquals(0,r.getOrientation());

        r.setHeight(50);
        r.setWidth(110);

        assertEquals(50,r.getHeight());
        assertEquals(110,r.getWidth());
    }

    @Test
    void isOnShape(){
        Position zero = new Position(0,0,0);
        assertTrue(r.isOnShape(zero,zero));

        Position p = new Position(1000,1000,0);
        assertFalse(r.isOnShape(zero, p));
    }

    @Test
    void createRect(){
        Shape s = new Rectangle(1000,200);

        assertEquals(0,s.getOrientation());
        assertEquals(1000, s.getWidth());
        assertEquals(200,s.getHeight());
        assertEquals(Constants.RECTANGLE, s.getType());
    }
}
