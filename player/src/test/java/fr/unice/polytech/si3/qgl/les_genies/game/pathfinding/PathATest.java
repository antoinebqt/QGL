package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxMap;
import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxStream;
import fr.unice.polytech.si3.qgl.les_genies.game.map.MapGrid;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PathATest {
    PathA pathA;
    MapGrid mapGrid;

    @BeforeEach
    void setUp() {
        mapGrid = mock(MapGrid.class);
        when(mapGrid.getNbBox()).thenReturn(10);
        when(mapGrid.getBox(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new BoxMap(10, 0, 0));
        when(mapGrid.getBox(Mockito.any(Position.class))).thenReturn(new BoxMap(10, 9, 9));


        pathA = new PathA(mapGrid, new Position(0, 0, 0), new Position(9, 9, 0));
    }

    @Test
    void findPathTestV1() {
        Position startPos = new Position(0, 0, 0);
        Position endPos = new Position(2, 2, 0);

        mapGrid = mock(MapGrid.class);
        when(mapGrid.getNbBox()).thenReturn(3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BoxMap b = new BoxMap(1, i, j);
                // set obstacles
                if ((i == 1 && j == 0) || (i == 0 && j == 1)) b.setOccupied();
                when(mapGrid.getBox(i, j)).thenReturn(b);
            }
        }

        when(mapGrid.getBox(startPos)).thenReturn(new BoxMap(1, 0, 0));
        when(mapGrid.getBox(endPos)).thenReturn(new BoxMap(1, 2, 2));

        pathA = new PathA(mapGrid, startPos, endPos);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                when(mapGrid.getBox((NodeA) any())).thenReturn(new BoxMap(1, i, j));
            }
        }

        List<NodeA> correctPath = new ArrayList<>();
        correctPath.add(pathA.getGraph()[1][1]);
        correctPath.add(pathA.getGraph()[2][2]);

        List<NodeA> foundPath = pathA.findPath();

        assertEquals(correctPath.size(), foundPath.size());
        assertTrue(foundPath.containsAll(correctPath));
    }

    @Test
    void processAndGetPathTestV1() {
        Position startPos = new Position(0, 0, 0);
        Position endPos = new Position(2, 2, 0);

        mapGrid = mock(MapGrid.class);
        when(mapGrid.getNbBox()).thenReturn(3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BoxMap b = new BoxMap(1, i, j);
                // set obstacles
                if ((i == 1 && j == 0) || (i == 0 && j == 1)) b.setOccupied();
                when(mapGrid.getBox(i, j)).thenReturn(b);
            }
        }

        when(mapGrid.getBox(startPos)).thenReturn(new BoxMap(1, 0, 0));
        when(mapGrid.getBox(endPos)).thenReturn(new BoxMap(1, 2, 2));

        pathA = new PathA(mapGrid, startPos, endPos);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                when(mapGrid.getBox((NodeA) any())).thenReturn(new BoxMap(1, i, j));
            }
        }

        List<NodeA> correctPath = new ArrayList<>();
        correctPath.add(pathA.getGraph()[0][0]);
        correctPath.add(pathA.getGraph()[1][1]);
        correctPath.add(pathA.getGraph()[2][2]);

        pathA.process();
        List<NodeA> foundPath = pathA.getPath();

        assertEquals(correctPath.size(), foundPath.size());
        assertTrue(foundPath.containsAll(correctPath));
        foundPath.forEach(n -> assertTrue(n.isSolution()));
    }

    @Test
    void processAndGetPathTestV2() {
        Position startPos = new Position(0, 0, 0);
        Position endPos = new Position(0, 2, 0);

        mapGrid = mock(MapGrid.class);
        when(mapGrid.getNbBox()).thenReturn(3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BoxMap b = new BoxMap(1, i, j);
                // set obstacles
                if ((i == 0 && j == 1) || (i == 1 && j == 1) || (i == 2 && j == 2)) b.setOccupied();
                when(mapGrid.getBox(i, j)).thenReturn(b);
            }
        }

        when(mapGrid.getBox(startPos)).thenReturn(new BoxMap(1, 0, 0));
        when(mapGrid.getBox(endPos)).thenReturn(new BoxMap(1, 0, 2));

        pathA = new PathA(mapGrid, startPos, endPos);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                when(mapGrid.getBox((NodeA) any())).thenReturn(new BoxMap(1, i, j));
            }
        }

        List<NodeA> correctPath = new ArrayList<>();
        correctPath.add(pathA.getGraph()[0][0]);
        correctPath.add(pathA.getGraph()[1][0]);
        correctPath.add(pathA.getGraph()[2][1]);
        correctPath.add(pathA.getGraph()[1][2]);
        correctPath.add(pathA.getGraph()[0][2]);

        pathA.process();
        List<NodeA> foundPath = pathA.getPath();

        assertEquals(correctPath.size(), foundPath.size());
        assertTrue(foundPath.containsAll(correctPath));
        foundPath.forEach(n -> assertTrue(n.isSolution()));
    }

    @Test
    void updateCostIfNeededTestGreaterNewFinalCostButNotInOpenSet() {
        when(mapGrid.getBox((NodeA) Mockito.any())).thenReturn(new BoxMap(10, 0, 0));

        NodeA current = new NodeA(0, 0, 0);
        current.setFinalCost(10);

        NodeA target = new NodeA(1, 1, 20);
        target.setFinalCost(1);
        target.setParent(null);

        pathA.updateCostIfNeeded(current, target);

        assertEquals(31.4, target.getFinalCost());
        assertNotNull(target.getParent());
        assertTrue(pathA.getOpenSet().contains(target));
    }

    @Test
    void updateCostIfNeededTestGreaterNewFinalCostV2() {
        when(mapGrid.getBox((NodeA) Mockito.any())).thenReturn(new BoxMap(10, 0, 0));

        NodeA current = new NodeA(0, 0, 0);
        current.setFinalCost(10);

        NodeA target = new NodeA(1, 1, 20);
        target.setFinalCost(31.3);
        target.setParent(null);

        pathA.getOpenSet().add(target);

        pathA.updateCostIfNeeded(current, target);

        assertEquals(31.3, target.getFinalCost());
        assertNull(target.getParent());
    }

    @Test
    void updateCostIfNeededTestGreaterNewFinalCost() {
        when(mapGrid.getBox((NodeA) Mockito.any())).thenReturn(new BoxMap(10, 0, 0));

        NodeA current = new NodeA(0, 0, 0);
        current.setFinalCost(10);

        NodeA target = new NodeA(1, 1, 20);
        target.setFinalCost(1);
        target.setParent(null);

        pathA.getOpenSet().add(target);

        pathA.updateCostIfNeeded(current, target);

        assertEquals(1, target.getFinalCost());
        assertNull(target.getParent());
    }

    @Test
    void updateCostIfNeededTestLesserNewFinalCostV2() {
        when(mapGrid.getBox((NodeA) Mockito.any())).thenReturn(new BoxMap(10, 0, 0));

        NodeA current = new NodeA(0, 0, 0);
        current.setFinalCost(10);

        NodeA target = new NodeA(1, 1, 20);
        target.setFinalCost(31.5);
        target.setParent(null);

        pathA.getOpenSet().add(target);

        pathA.updateCostIfNeeded(current, target);

        assertEquals(31.4, target.getFinalCost());
        assertNotNull(target.getParent());
    }

    @Test
    void updateCostIfNeededTestLesserNewFinalCost() {
        when(mapGrid.getBox((NodeA) Mockito.any())).thenReturn(new BoxMap(10, 0, 0));

        NodeA current = new NodeA(0, 0, 0);
        current.setFinalCost(10);

        NodeA target = new NodeA(1, 1, 20);
        target.setFinalCost(1000);
        target.setParent(null);

        pathA.getOpenSet().add(target);

        pathA.updateCostIfNeeded(current, target);

        assertEquals(31.4, target.getFinalCost());
        assertNotNull(target.getParent());
    }

    @Test
    void updateCostIfNeededTestIsClosed() {
        when(mapGrid.getBox((NodeA) Mockito.any())).thenReturn(new BoxMap(10, 0, 0));
        pathA.getClosedNodes()[1][1] = true;

        NodeA current = new NodeA(0, 0, 0);
        current.setFinalCost(10);

        NodeA target = new NodeA(1, 1, 20);
        target.setFinalCost(1000);
        target.setParent(null);

        pathA.updateCostIfNeeded(current, target);

        assertEquals(1000, target.getFinalCost());
        assertNull(target.getParent());
    }

    @Test
    void updateCostIfNeededTestNullTarget() {
        NodeA current = mock(NodeA.class);
        NodeA target = null;

        pathA.updateCostIfNeeded(current, target);

        assertNull(target);
    }

    @Test
    void getHeuristicCostV1() {
        pathA = new PathA(mapGrid, new Position(0, 0, 0), new Position(9, 9, 0));
        assertEquals(18.0, pathA.getHeuristicCost(0, 0));
    }

    @Test
    void getHeuristicCostV2() {
        when(mapGrid.getBox(Mockito.any(Position.class))).thenReturn(new BoxMap(10, 0, 0));
        when(mapGrid.getBox(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new BoxMap(10, 9, 9));
        pathA = new PathA(mapGrid, new Position(0, 0, 0), new Position(0, 0, 0));
        assertEquals(18.0, pathA.getHeuristicCost(9, 9));
    }

    @Test
    void getHeuristicCostV3() {
        when(mapGrid.getBox(Mockito.any(Position.class))).thenReturn(new BoxMap(10, 3, 5));
        when(mapGrid.getBox(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new BoxMap(10, 3, 9));
        pathA = new PathA(mapGrid, new Position(0, 0, 0), new Position(3, 5, 0));
        assertEquals(4, pathA.getHeuristicCost(3, 9));
    }

    @Test
    void getNodeFromBoxMapTestIsFreeV1() {
        BoxMap bm = new BoxMap(10, 0, 0);
        NodeA node = pathA.getNodeFromBoxMap(bm);
        assertNotNull(node);
        assertEquals(0, node.getI());
        assertEquals(0, node.getJ());
    }

    @Test
    void getNodeFromBoxMapTestIsFreeV2() {
        BoxMap bm = new BoxMap(10, 3, 7);
        NodeA node = pathA.getNodeFromBoxMap(bm);
        assertNotNull(node);
        assertEquals(3, node.getI());
        assertEquals(7, node.getJ());
    }

    @Test
    void getNodeFromBoxMapTestNotFree() {
        BoxMap bm = new BoxMap(10, 3, 7);
        bm.setOccupied();
        NodeA node = pathA.getNodeFromBoxMap(bm);
        assertNull(node);
    }

    @Test
    void checkNotNullNodeTest() {
        NodeA[][] graph = pathA.getGraph();
        graph[0][0] = null;

        pathA.checkNotNullNode(new BoxMap(10, 0, 0));
        assertNotNull(graph[0][0]);
    }

    @Test
    void initGraphTestStartWhenReef() {
        BoxMap startBoxMap = new BoxMap(10, 0, 0);
        startBoxMap.setOccupied();

        mapGrid = mock(MapGrid.class);
        when(mapGrid.getNbBox()).thenReturn(10);
        when(mapGrid.getBox(Mockito.anyInt(), Mockito.anyInt())).thenReturn(startBoxMap);
        when(mapGrid.getBox(Mockito.any(Position.class))).thenReturn(new BoxMap(10, 0, 0));


        pathA = new PathA(mapGrid, new Position(0, 0, 0), new Position(9, 9, 0));

        assertNotNull(pathA.getGraph()[0][0]);
    }

    @Test
    void initGraphTestStartFinalCost() {
        assertEquals(0, pathA.getGraph()[0][0].getFinalCost());
    }

    @Test
    void initGraphTest() {
        assertEquals(10, pathA.getGraph().length);
    }

    @Test
    void simplifyPathListTestHorizontal() {
        List<NodeA> nodeAList = new ArrayList<>();

        NodeA bm1 = new NodeA(0, 0, 0);
        NodeA bm2 = new NodeA(1, 0, 0);
        NodeA bm3 = new NodeA(2, 0, 0);
        NodeA bm4 = new NodeA(3, 0, 0);
        NodeA bm5 = new NodeA(4, 0, 0);

        nodeAList.add(bm1);
        nodeAList.add(bm2);
        nodeAList.add(bm3);
        nodeAList.add(bm4);
        nodeAList.add(bm5);

        pathA.simplifyPath(nodeAList);

        assertEquals(2, nodeAList.size());
        assertTrue(nodeAList.contains(bm2));
        assertTrue(nodeAList.contains(bm5));
    }

    @Test
    void simplifyPathListTestVertical() {
        List<NodeA> nodeAList = new ArrayList<>();

        NodeA bm1 = new NodeA(0, 0, 0);
        NodeA bm2 = new NodeA(0, 1, 0);
        NodeA bm3 = new NodeA(0, 2, 0);
        NodeA bm4 = new NodeA(0, 3, 0);
        NodeA bm5 = new NodeA(0, 4, 0);

        nodeAList.add(bm1);
        nodeAList.add(bm2);
        nodeAList.add(bm3);
        nodeAList.add(bm4);
        nodeAList.add(bm5);

        pathA.simplifyPath(nodeAList);

        assertEquals(2, nodeAList.size());
        assertTrue(nodeAList.contains(bm2));
        assertTrue(nodeAList.contains(bm5));
    }

    @Test
    void simplifyPathListTestDiagonal() {
        List<NodeA> nodeAList = new ArrayList<>();

        NodeA bm1 = new NodeA( 0, 0, 0);
        NodeA bm2 = new NodeA( 1, 1, 0);
        NodeA bm3 = new NodeA( 2, 2, 0);
        NodeA bm4 = new NodeA( 3, 3, 0);
        NodeA bm5 = new NodeA( 4, 4, 0);

        nodeAList.add(bm1);
        nodeAList.add(bm2);
        nodeAList.add(bm3);
        nodeAList.add(bm4);
        nodeAList.add(bm5);

        pathA.simplifyPath(nodeAList);

        assertEquals(2, nodeAList.size());
        assertTrue(nodeAList.contains(bm2));
        assertTrue(nodeAList.contains(bm5));
    }

    @Test
    void simplifyPathListTestWithJunctions() {
        List<NodeA> nodeAList = new ArrayList<>();

        NodeA bm1 = new NodeA( 0, 0, 0);
        NodeA bm2 = new NodeA( 1, 0, 0);
        NodeA bm3 = new NodeA( 2, 0, 0);
        NodeA bm4 = new NodeA( 3, 0, 0);
        NodeA bm5 = new NodeA( 4, 1, 0);
        NodeA bm6 = new NodeA( 5, 2, 0);
        NodeA bm7 = new NodeA( 6, 3, 0);

        nodeAList.add(bm1);
        nodeAList.add(bm2);
        nodeAList.add(bm3);
        nodeAList.add(bm4);
        nodeAList.add(bm5);
        nodeAList.add(bm6);
        nodeAList.add(bm7);

        pathA.simplifyPath(nodeAList);

        assertEquals(3, nodeAList.size());
        assertTrue(nodeAList.contains(bm2));
        assertTrue(nodeAList.contains(bm4));
        assertTrue(nodeAList.contains(bm7));
    }

    @Test
    void simplifyPathListTestInverse() {
        List<NodeA> nodeAList = new ArrayList<>();

        NodeA bm1 = new NodeA(10, 0, 0);
        NodeA bm2 = new NodeA(9, 1, 0);
        NodeA bm3 = new NodeA(8, 2, 0);
        NodeA bm4 = new NodeA(7, 3, 0);
        NodeA bm5 = new NodeA(6, 4, 0);

        nodeAList.add(bm1);
        nodeAList.add(bm2);
        nodeAList.add(bm3);
        nodeAList.add(bm4);
        nodeAList.add(bm5);

        pathA.simplifyPath(nodeAList);

        assertEquals(2, nodeAList.size());
        assertTrue(nodeAList.contains(bm2));
        assertTrue(nodeAList.contains(bm5));
    }
}