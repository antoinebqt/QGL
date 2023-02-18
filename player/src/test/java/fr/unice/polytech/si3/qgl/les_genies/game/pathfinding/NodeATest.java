package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeATest {
    NodeA node;

    @BeforeEach
    void setUp() {
        node = new NodeA(2, 3, 10);
    }

    @Test
    void isSolutionTestTrue() {
        assertFalse(node.isSolution());
    }

    @Test
    void isSolutionTestFalse() {
        node.setSolution(true);
        assertTrue(node.isSolution());
    }

    @Test
    void getHeuristicTest() {
        assertEquals(10, node.getHeuristic());
    }

    @Test
    void getParent() {
        NodeA parent = new NodeA(0, 0, 0);
        node.setParent(parent);
        assertEquals(parent, node.getParent());
    }
}