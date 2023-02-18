package fr.unice.polytech.si3.qgl.les_genies.game.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WatchTest {
    @Test
    void createWatch(){
        Watch watch = new Watch(10,100);
        assertNotEquals("",watch.toString());
        assertEquals("watch", watch.getType());
    }
}
