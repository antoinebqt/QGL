package fr.unice.polytech.si3.qgl.les_genies.game.checkpoint;


import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxMap;
import fr.unice.polytech.si3.qgl.les_genies.game.map.MapGrid;
import fr.unice.polytech.si3.qgl.les_genies.game.pathfinding.NodeA;
import fr.unice.polytech.si3.qgl.les_genies.game.pathfinding.PathA;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RegattaGoalTest {

    String TEST_PATTERN = "RegattaGoal{" +
            "mode='race', " +
            "checkpoints=" +
            "[Checkpoint {" +
            "position = " +
            "{\"x\": 0.0," +
            "\"y\": 0.0," +
            "\"orientation\": 0.0" +
            "}," +
            "shape = {" +
            "\"type\": \"circle\"," +
            "\"radius\": 0.0" +
            "}}], " +
            "currentCheckpoint=Checkpoint {" +
            "position = {" +
            "\"x\": 0.0," +
            "\"y\": 0.0," +
            "\"orientation\": 0.0" +
            "}," +
            "shape = {" +
            "\"type\": \"circle\"," +
            "\"radius\": 0.0" +
            "}}, " +
            "indexCurrentCheckpoint=0}";

    RegattaGoal regattaGoal;
    Checkpoint[] checkpoints;

    @BeforeEach
    void setUp() {
        checkpoints = new Checkpoint[0];
        regattaGoal = new RegattaGoal("Test", checkpoints);
    }


    @Test
    void getCheckpointTestV1() {
        Position startPos = new Position(0, 0, 0);
        Position endPos = new Position(2, 2, 0);

        MapGrid mapGrid = mock(MapGrid.class);
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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                when(mapGrid.getBox((NodeA) any())).thenReturn(new BoxMap(1, i, j));
            }
        }

        Checkpoint c = new Checkpoint(endPos, new Circle(35));
        checkpoints = new Checkpoint[]{c};
        regattaGoal = new RegattaGoal("Test", checkpoints);
        Checkpoint res = regattaGoal.getCheckpoint(startPos, mapGrid);

        assertEquals(2.5, res.getPosition().getX());
        assertEquals(2.5, res.getPosition().getY());
        assertEquals(3, regattaGoal.getPathCheckpointsQueue().size());
    }

    @Test
    void generatePathListTest() {
        Position startPos = new Position(0, 0, 0);
        Position endPos = new Position(2, 2, 0);

        MapGrid mapGrid = mock(MapGrid.class);
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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                when(mapGrid.getBox((NodeA) any())).thenReturn(new BoxMap(1, i, j));
            }
        }

        Checkpoint c = new Checkpoint(endPos, new Circle(35));
        checkpoints = new Checkpoint[]{c};
        regattaGoal = new RegattaGoal("Test", checkpoints);
        Checkpoint res = regattaGoal.getCheckpoint(startPos, mapGrid);
        regattaGoal.generatePathList(mapGrid, startPos);

        assertEquals(3, regattaGoal.getPathCheckpointsQueue().size());

        regattaGoal.generatePathList(mapGrid, startPos);
        assertEquals(3, regattaGoal.getPathCheckpointsQueue().size());
    }

    @Test
    void createCheckpointTest() {
        Position p = new Position(0,0,0);
        BoxMap b = mock(BoxMap.class);
        when(b.getCenter()).thenReturn(p);
        Checkpoint c = regattaGoal.createCheckpoint(b);

        assertEquals(c.getPosition(), p);
        assertEquals(225, c.getShape().getRadius());
    }

    @Test
    void getIndexCurrentCheckpointTest() {
        checkpoints = new Checkpoint[]{null, null};
        regattaGoal = new RegattaGoal("Test", checkpoints);

        assertEquals(0, regattaGoal.getIndexCurrentCheckpoint());
        regattaGoal.getNextCheckpoint();
        assertEquals(1, regattaGoal.getIndexCurrentCheckpoint());
        regattaGoal.getNextCheckpoint();
        assertEquals(1, regattaGoal.getIndexCurrentCheckpoint());
    }

    @Test
    void toStringTest(){
        Checkpoint c1 = new Checkpoint(new Position(0,0,0),new Circle(0));
        Checkpoint[] arr = {c1};
        RegattaGoal regattaGoal = new RegattaGoal("race",arr);
        assertEquals(TEST_PATTERN,regattaGoal.toString());
    }

    @Test
    void getNextCheckpointTestNotLast() {
        checkpoints = new Checkpoint[]{null, null};
        regattaGoal = new RegattaGoal("Test", checkpoints);

        assertTrue(regattaGoal.getNextCheckpoint());
        assertFalse(regattaGoal.getNextCheckpoint());
    }

    @Test
    void getNextCheckpointTestLast() {
        checkpoints = new Checkpoint[]{null};
        regattaGoal = new RegattaGoal("Test", checkpoints);

        assertFalse(regattaGoal.getNextCheckpoint());
    }

    @Test
    void getNextCheckpointTestEmpty() {
        assertFalse(regattaGoal.getNextCheckpoint());
    }

    @Test
    void getCheckpointsTest() {
        assertEquals(checkpoints, regattaGoal.getCheckpoints());
    }

    /*
    @Test
    void getNextCheckpoint(){
        Checkpoint c1 = new Checkpoint(new Position(0,0,0),new Circle(0));
        Checkpoint c2 = new Checkpoint(new Position(100,0,0),new Circle(0));
        Checkpoint[] arr = {c1,c2};
        RegattaGoal rg = new RegattaGoal("mode",arr);


        assertEquals(c1,rg.getCurrentCheckpoint());
        assertTrue(rg.getNextCheckpoint());

        assertNotEquals(0, rg.getIndexCurrentCheckpoint());

        assertEquals(c2,rg.getCurrentCheckpoint());
        assertFalse(rg.getNextCheckpoint());

        assertEquals(0,rg.getListOpti().size());
    }

    @Test
    void getterTest(){
        Checkpoint c1 = new Checkpoint(new Position(0,0,0),new Circle(0));
        Checkpoint c2 = new Checkpoint(new Position(100,0,0),new Circle(0));
        Checkpoint[] arr = {c1,c2};
        RegattaGoal regattaGoal = new RegattaGoal("race",arr);
        assertEquals("race",regattaGoal.getMode());
        assertEquals(2,regattaGoal.getCheckpoints().length);
    }

    @Test
    void toStringTest(){
        Checkpoint c1 = new Checkpoint(new Position(0,0,0),new Circle(0));
        Checkpoint[] arr = {c1};
        RegattaGoal regattaGoal = new RegattaGoal("race",arr);
        assertEquals(TEST_PATTERN,regattaGoal.toString());
    }

    @Test
    void getMicroCheckpointTest(){
        Checkpoint c1 = new Checkpoint(new Position(0,0,0),new Circle(20));
        Checkpoint c2 = new Checkpoint(new Position(100,0,0),new Circle(20));
        Checkpoint[] arr = {c1,c2};
        RegattaGoal regattaGoal = new RegattaGoal("race",arr);

        Checkpoint c = regattaGoal.getMicroCurrentCheckpoint();
        assertEquals(10,c.getShape().getRadius());
    }

    @Test
    void getLastMicroCheckpointTest(){
        Checkpoint c = new Checkpoint(new Position(0,0,0),new Circle(20));
        Checkpoint[] arr = {c};
        RegattaGoal regattaGoal = new RegattaGoal("race",arr);

        Checkpoint check = regattaGoal.getMicroCurrentCheckpoint();
        assertEquals(c,check);
    }

    @Test
    void getMicroCheckpointRectangleTest(){
        Checkpoint c = new Checkpoint(new Position(0,0,0),new Rectangle(100,20,0));
        Checkpoint[] arr = {c};
        RegattaGoal regattaGoal = new RegattaGoal("race",arr);

        Checkpoint check = regattaGoal.getMicroCurrentCheckpoint();
        assertEquals(c,check);
    }

    @Test
    void getMicroCheckpointNullPositionTest(){
        Checkpoint c = new Checkpoint(null,new Rectangle(100,20,0));
        Checkpoint[] arr = {c};
        RegattaGoal regattaGoal = new RegattaGoal("race",arr);
        assertEquals(0,regattaGoal.getIndexCurrentCheckpoint());

        Checkpoint check = regattaGoal.getMicroCurrentCheckpoint();
        assertEquals(c,check);
    }

    @Test
    void createRegattaGoalTest(){
        Checkpoint c = new Checkpoint(null,new Rectangle(100,20,0));
        Checkpoint[] arr = {c};
        RegattaGoal regattaGoal = new RegattaGoal("race",arr);

        assertNotNull(regattaGoal.getCurrentCheckpoint());

        RegattaGoal r = new RegattaGoal("race",new Checkpoint[]{});

        assertNull(r.getCurrentCheckpoint());
    }

     */

/*

    @Test
    void getCheckpointTest(){
        Checkpoint c = new Checkpoint(new Position(100,100),new Rectangle(100,20,0));
        Checkpoint[] arr = {c};

        RegattaGoal r = new RegattaGoal("race",arr);

        Checkpoint check = r.getCheckpoint(new Position(0,0,0),new MapGrid(10,arr));
        assertNotNull(check);

        assertEquals(2,r.getPl().getPath().size());
    }

    @Test
    void createCPTest(){
        Checkpoint c = new Checkpoint(new Position(100,100),new Rectangle(100,20,0));
        Checkpoint[] arr = {c};

        RegattaGoal r = new RegattaGoal("race",arr);

        Checkpoint check = r.getCheckpoint(new Position(0,0,0),new MapGrid(10,arr));

        c = r.createCheckpoint();

        assertEquals(15,c.getPosition().getX());
        assertEquals(15,c.getPosition().getY());
        assertEquals(0,c.getPosition().getOrientation());
        assertEquals(10,c.getShape().getRadius());
    }

 */
}
