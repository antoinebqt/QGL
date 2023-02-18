package fr.unice.polytech.si3.qgl.les_genies.game;

import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckpointTest {

    @Test
    void isOnCheckpointTestOnCheckpoint() {
        Circle circle = new Circle(10);
        circle.setRadius(10);
        Checkpoint checkpoint = new Checkpoint(new Position(0, 0, 0), circle);
        assertTrue(checkpoint.isOnCheckpoint(new Position(5,0,0)));
    }

    @Test
    void isOnCheckpointTestNotOnCheckpoint() {
        Circle circle = new Circle(10);
        circle.setRadius(10);
        Checkpoint checkpoint = new Checkpoint(new Position(0, 0, 0), circle);
        assertFalse(checkpoint.isOnCheckpoint(new Position(15,0,0)));
    }
}