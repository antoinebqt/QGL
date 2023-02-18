package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxMap;
import fr.unice.polytech.si3.qgl.les_genies.game.map.MapGrid;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Reef;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Stream;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.VisibleEntities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapGridTest {
    MapGrid map;
    Checkpoint[] checkpoints;
    Position p1;
    Position p2;
    int size = 250;

    @BeforeEach
    void setUp() {
        p1 = new Position(50,50);
        p2 = new Position(200,200);

        checkpoints = new Checkpoint[2];
        checkpoints[0] = new Checkpoint(p1, new Circle(10));
        checkpoints[1] = new Checkpoint(p2, new Circle(10));

        map = new MapGrid(size,checkpoints);
    }

    @Test
    void initTest(){
        checkpoints = new Checkpoint[2];
        checkpoints[0] = new Checkpoint(p1, new Circle(200));
        checkpoints[1] = new Checkpoint(p2, new Circle(200));

        map = new MapGrid(size,checkpoints);

        assertTrue(map.getBox(p1).getStatus());
        assertTrue(map.getBox(p2).getStatus());
    }

    @Test
    void getNbBoxSize() {
        assertEquals(43,map.getNbBox());
    }

    @Test
    void getNbBoxSize2() {
        checkpoints[0] = new Checkpoint(new Position(1400,500), new Circle(10));

        map = new MapGrid(size,checkpoints);
        assertEquals(48,map.getNbBox());
    }

    @Test
    void setOccupiedSpotTest(){
        p1.setX(500);
        p1.setY(500);

        List<VisibleEntities> visibleEntities = new ArrayList<>();
        visibleEntities.add(new Stream(new Position(200,1600), new Circle(10),120));
        visibleEntities.add(new Reef(p1, new Circle(50)));
        visibleEntities.add(new Reef(new Position(50000,50000), new Circle(50)));
        visibleEntities.add(new Stream(new Position(500,1200), new Circle(50),50));

        map.setSpotsOccupied(visibleEntities);

        assertFalse(map.getBox(p1).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()-size, p1.getY()-size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()+size, p1.getY()-size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()-size, p1.getY()+size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()+size, p1.getY()+size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX(), p1.getY()-size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX(), p1.getY()+size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()-size, p1.getY())).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()+size, p1.getY())).getStatus());
    }

    @Test
    void setOccupiedSpot2Test(){
        p1.setX(600);
        p1.setY(600);

        List<VisibleEntities> visibleEntities = new ArrayList<>();
        visibleEntities.add(new Stream(new Position(200,1600), new Circle(200),120));
        visibleEntities.add(new Reef(p1, new Circle(2000)));
        visibleEntities.add(new Reef(new Position(50000,50000), new Circle(50)));
        visibleEntities.add(new Stream(new Position(500,1200), new Circle(50),50));

        map.setSpotsOccupied(visibleEntities);

        assertFalse(map.getBox(p1).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()-size, p1.getY()-size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()+size, p1.getY()-size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()-size, p1.getY()+size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()+size, p1.getY()+size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX(), p1.getY()-size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX(), p1.getY()+size)).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()-size, p1.getY())).getStatus());
        assertFalse(map.getBox(new Position(p1.getX()+size, p1.getY())).getStatus());
    }

    @Test
    void getIdTest(){
        VisibleEntities v = new Stream(new Position(10,10), new Circle(200),120);
        assertEquals(10010, map.getId(v));
    }

    @Test
    void sizeMapTest(){
        assertEquals(10200,map.getSizeMap(checkpoints).getX());
        assertEquals(10200,map.getSizeMap(checkpoints).getY());

        checkpoints[0] = new Checkpoint(new Position(1400,500), new Circle(10));

        assertEquals(11400,map.getSizeMap(checkpoints).getX());
        assertEquals(10500,map.getSizeMap(checkpoints).getY());
    }

    @Test
    void updateTest(){
        assertEquals(0, map.getAllEntities().size());

        List<VisibleEntities> visibleEntities = new ArrayList<>();
        visibleEntities.add(new Stream(new Position(200,1600), new Circle(10),120));
        visibleEntities.add(new Reef(p1, new Circle(350)));
        visibleEntities.add(new Stream(new Position(500,1200), new Circle(50),50));

        int sizeBefore = Cockpit.getLogsSize();

        assertTrue(map.update(visibleEntities));

        assertEquals(3, map.getAllEntities().size());

        visibleEntities.clear();
        visibleEntities.add(new Stream(new Position(200,1600), new Circle(10),120));
        visibleEntities.add(new Reef(p1, new Circle(50)));

        assertFalse(map.update(visibleEntities));
        assertEquals(3, map.getAllEntities().size());

        visibleEntities.clear();
        visibleEntities.add(new Stream(new Position(1200,1900), new Circle(100),100));
        visibleEntities.add(new Reef(new Position(50,890), new Circle(50)));

        assertTrue(map.update(visibleEntities));
        assertEquals(5, map.getAllEntities().size());

        int count = 0;

        for(BoxMap[] b1 : map.getMap()){
            for(BoxMap b : b1){
                if(!b.getStatus())count++;
            }
        }

        assertTrue(count>0);
        assertTrue(Cockpit.getLogsSize()>sizeBefore);
    }

    @Test
    void getMapTest(){
        BoxMap[][] m = map.getMap();

        assertNotEquals(0, m.length);

        assertEquals(m[m.length-1][m.length-1],map.getBox(new Position(50000,50000)));
        assertEquals(m[m.length-1][m.length-1],map.getBox(new Position(10800,10900)));
        assertEquals(m[m.length-1][m.length-1],map.getBox(new Position(459592,456542569)));
        assertEquals(m[m.length-1][m.length-2],map.getBox(new Position(10650,10300)));
    }

    @Test
    void getBoxSize(){
        assertNotEquals(0, MapGrid.getBoxSize());
    }

    @Test
    void getBoxTest(){
        assertNotNull(map.getBox(0,0));

        assertNotNull(map.getBox(new NodeA(0,0,0)));
    }
}
