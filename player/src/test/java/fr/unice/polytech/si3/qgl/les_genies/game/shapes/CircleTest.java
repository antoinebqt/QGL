package fr.unice.polytech.si3.qgl.les_genies.game.shapes;

import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    void isOnShapeTestInCircle() {
        Circle circle = new Circle(0);
        circle.setRadius(10);
        Position positionShape = new Position(0,0, 0);
        assertTrue(circle.isOnShape(positionShape, new Position(0,9, 0)));
    }

    @Test
    void isOnShapeTestNotInCircle() {
        Circle circle = new Circle(0);
        circle.setRadius(10);
        Position positionShape = new Position(0,0, 0);
        assertFalse(circle.isOnShape(positionShape, new Position(0,11, 0)));
    }
}