package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessBoxTest {

    @Test
    void processBoxTrue() {
        ProcessBox processBox = new ProcessBox(true);
        assertTrue(processBox.isProcess());
    }

    @Test
    void processBoxFalse() {
        ProcessBox processBox = new ProcessBox(false);
        assertFalse(processBox.isProcess());
    }

    @Test
    void processBoxSetFalse() {
        ProcessBox processBox = new ProcessBox(true);
        processBox.setProcess(false);
        assertFalse(processBox.isProcess());
    }

    @Test
    void processBoxSetTrue() {
        ProcessBox processBox = new ProcessBox(false);
        processBox.setProcess(true);
        assertTrue(processBox.isProcess());
    }

}