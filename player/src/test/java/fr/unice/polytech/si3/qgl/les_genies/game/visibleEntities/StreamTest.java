package fr.unice.polytech.si3.qgl.les_genies.game.visibleEntities;

import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Stream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreamTest {
    @Test
    void createStream(){
        Position p = new Position(0,0,0);
        Shape s = new Shape();
        Stream stream = new Stream(p,s,10);
        assertEquals(10,stream.getStrength());
        assertEquals(p,stream.getPosition());
        assertEquals(s, stream.getShape());
        assertEquals(Constants.STREAM, stream.getType());
        assertNotEquals("", stream.toString());
    }
}
