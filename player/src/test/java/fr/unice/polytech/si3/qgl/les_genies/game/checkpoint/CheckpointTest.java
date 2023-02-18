package fr.unice.polytech.si3.qgl.les_genies.game.checkpoint;

import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckpointTest {
    String TEST_PATTERN = "Checkpoint {" +
            "position = {" +
            "\"x\": 10.0," +
            "\"y\": 20.0," +
            "\"orientation\": 0.0" +
            "},"  +
            "shape = {" +
            "\"type\": \"circle\"," +
            "\"radius\": 0.0}" +
            '}';

    @Test
    void testToString() {
        Checkpoint checkpoint = new Checkpoint(new Position(10,20,0),new Circle(0));
        assertEquals(TEST_PATTERN, checkpoint.toString());
    }
}
