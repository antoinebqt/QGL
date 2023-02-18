package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxMap;
import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxStream;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Stream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {
    @Test
    void createAndGetStreamBoxTest(){
        Stream s = new Stream(new Position(10,10,10),new Rectangle(100,100,2), 150);
        BoxStream boxStream = new BoxStream(s);

        assertEquals(s.getStrength(),boxStream.getPower());
        assertEquals(s.getPosition().getOrientation(), boxStream.getOrientation());
    }

    @Test
    void basicTestBoxMap(){
        BoxMap b = new BoxMap(100,2,6);

        assertFalse(b.isStream());

        b.setAsStream(new Stream(new Position(100,100,1),new Circle(10),500));

        assertTrue(b.isStream());

        assertEquals(250, b.getCenter().getX());
        assertEquals(650, b.getCenter().getY());

        assertEquals(500,b.getStreamInfos().getPower());
        assertEquals(1, b.getStreamInfos().getOrientation());

        assertTrue(b.getStatus());

        b.setOccupied();

        assertFalse(b.getStatus());
    }

    @Test
    void setNotOccupiedTest() {
        BoxMap b = new BoxMap(100,2,6);

        assertTrue(b.getStatus());
        b.setOccupied();
        assertFalse(b.getStatus());
    }

    @Test
    void toStringTest() {
        BoxMap b = new BoxMap(100,2,6);

        assertEquals("{isFree=true, pos={\"x\": 250.0,\"y\": 650.0,\"orientation\": 0.0}, i=2, j=6}", b.toString());
    }
}
