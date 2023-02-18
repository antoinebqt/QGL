package fr.unice.polytech.si3.qgl.les_genies.game.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanonTest {
    @Test
    void createCanon() {
        Canon canon = new Canon(10, 100, true, 1000);
        assertTrue(canon.isLoaded());
        assertEquals(1000, canon.getAngleEntity());
        assertNotEquals("", canon.toString());
        assertEquals("canon", canon.getType());
    }

    @Test
    void createOtherCanon() {
        Canon canon = new Canon(10, 100, false, 1000);
        assertFalse(canon.isLoaded());
    }
}
