package fr.unice.polytech.si3.qgl.les_genies.game.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SailTest {
    @Test
    void createSail(){
        Sail sail = new Sail(10,100,true);
        assertTrue(sail.isOpenned());
        assertNotEquals("",sail.toString());
        assertEquals("sail", sail.getType());
    }

    @Test
    void createOtherSail(){
        Sail sail = new Sail(10,100,false);
        assertFalse(sail.isOpenned());
    }
}
