package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxMap;
import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static fr.unice.polytech.si3.qgl.les_genies.game.pathfinding.CalculPathfinding.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalculPathfindingTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getPathOrientationTestV1() {
        NodeA current = new NodeA(5, 4, 0);
        NodeA target = new NodeA(6, 4, 0);

        assertEquals(0, getPathOrientation(current, target));
    }

    @Test
    void getPathOrientationTestV2() {
        NodeA current = new NodeA(5, 4, 0);
        NodeA target = new NodeA(4, 4, 0);

        assertEquals(Math.PI, getPathOrientation(current, target));
    }

    @Test
    void getPathOrientationTestV3() {
        NodeA current = new NodeA(5, 4, 0);
        NodeA target = new NodeA(5, 5, 0);

        assertEquals(Math.PI/2, getPathOrientation(current, target));
    }

    @Test
    void getPathOrientationTestV4() {
        NodeA current = new NodeA(5, 4, 0);
        NodeA target = new NodeA(5, 3, 0);

        assertEquals(-Math.PI/2, getPathOrientation(current, target));
    }

    @Test
    void getPathOrientationTestV5() {
        NodeA current = new NodeA(5, 4, 0);
        NodeA target = new NodeA(6, 3, 0);

        assertEquals(-Math.PI/4, getPathOrientation(current, target));
    }

    @Test
    void getPathOrientationTestV6() {
        NodeA current = new NodeA(5, 4, 0);
        NodeA target = new NodeA(6, 5, 0);

        assertEquals(Math.PI/4, getPathOrientation(current, target));
    }

    @Test
    void getPathOrientationTestV7() {
        NodeA current = new NodeA(5, 4, 0);
        NodeA target = new NodeA(4, 3, 0);

        assertEquals(-3*Math.PI/4, getPathOrientation(current, target));
    }

    @Test
    void getPathOrientationTestV8() {
        NodeA current = new NodeA(5, 4, 0);
        NodeA target = new NodeA(4, 5, 0);

        assertEquals(3*Math.PI/4, getPathOrientation(current, target));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV24() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(-1.8);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(100, calculateStreamCostAugmentation(b, 10, 0));
        assertEquals(10, calculateStreamCostAugmentation(b, 10, -0.1));
        assertEquals(10, calculateStreamCostAugmentation(b, 10, -3.1));
        assertEquals(100, calculateStreamCostAugmentation(b, 10, 0.1));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV23() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(1.8);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(100, calculateStreamCostAugmentation(b, 10, 0));
        assertEquals(10, calculateStreamCostAugmentation(b, 10, 0.2));
        assertEquals(10, calculateStreamCostAugmentation(b, 10, 3.1));
        assertEquals(100, calculateStreamCostAugmentation(b, 10, -0.1));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV22() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(-1.9);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(100, calculateStreamCostAugmentation(b, 10, 2.1));
        assertEquals(1, calculateStreamCostAugmentation(b, 10, -2.1));
        assertEquals(100, calculateStreamCostAugmentation(b, 10, 1.9));
        assertEquals(1, calculateStreamCostAugmentation(b, 10, -1.9));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV21() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(-2.1);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, 2.1));
        assertEquals(1, calculateStreamCostAugmentation(b, 10, -2.1));
        assertEquals(100, calculateStreamCostAugmentation(b, 10, 1.9));
        assertEquals(1, calculateStreamCostAugmentation(b, 10, -1.9));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV20() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(1.9);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, 2.1));
        assertEquals(100, calculateStreamCostAugmentation(b, 10, -2.1));
        assertEquals(1, calculateStreamCostAugmentation(b, 10, 1.9));
        assertEquals(100, calculateStreamCostAugmentation(b, 10, -1.9));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV19() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(2.1);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, 2.1));
        assertEquals(1, calculateStreamCostAugmentation(b, 10, -2.1));
        assertEquals(1, calculateStreamCostAugmentation(b, 10, 1.9));
        assertEquals(100, calculateStreamCostAugmentation(b, 10, -1.9));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV18() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(Math.PI/2);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(100, calculateStreamCostAugmentation(b, 10, -Math.PI/2));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV17() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(-Math.PI/2);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, -Math.PI/2));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV16() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(-Math.PI/2);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(100, calculateStreamCostAugmentation(b, 10, Math.PI/2));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV15() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(Math.PI/2);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, Math.PI/2));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV14() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(-3.0);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, 3.0));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV13() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(3.0);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, -3.0));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV12() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(Math.PI);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, -Math.PI));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV11() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(Math.PI);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, Math.PI));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV10() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(-0.9);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, -1));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV9() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(0.0);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(100, calculateStreamCostAugmentation(b, 10, -Math.PI));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV8() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(0.0);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(100, calculateStreamCostAugmentation(b, 10, Math.PI));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV7() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(0.0);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(100, calculateStreamCostAugmentation(b, 10, -2));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV6() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(0.0);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(100, calculateStreamCostAugmentation(b, 10, 2));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV5() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(0.0);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(10, calculateStreamCostAugmentation(b, 10, -1.4));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV4() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(0.0);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(10, calculateStreamCostAugmentation(b, 10, 1.4));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV3() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(-0.9);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, 0.05));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV2() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(0.0);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, -0.9));
    }

    @Test
    void calculateStreamCostAugmentationTestStreamV1() {
        BoxStream s = mock(BoxStream.class);
        when(s.getOrientation()).thenReturn(0.0);
        when(s.getPower()).thenReturn(100.0);

        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(true);
        when(b.getStreamInfos()).thenReturn(s);

        assertEquals(1, calculateStreamCostAugmentation(b, 10, 0));
    }

    @Test
    void calculateStreamCostAugmentationTestNoStream() {
        BoxMap b = mock(BoxMap.class);
        when(b.isStream()).thenReturn(false);

        assertEquals(10, calculateStreamCostAugmentation(b, 10, 0));
    }

    @Test
    void calculateCostV1() {
        BoxMap b = new BoxMap(10, 0, 0);
        NodeA current = new NodeA(0, 0, 0);
        NodeA target = new NodeA(1, 1, 20);

        assertEquals(1.4, calculateCost(current, target, b));
    }

    @Test
    void calculateCostV2() {
        BoxMap b = new BoxMap(10, 0, 0);
        NodeA current = new NodeA(0, 0, 0);
        NodeA target = new NodeA(0, 0, 20);

        assertEquals(0, calculateCost(current, target, b));
    }

    @Test
    void calculateCostV3() {
        BoxMap b = new BoxMap(10, 0, 0);
        NodeA current = new NodeA(0, 0, 0);
        NodeA target = new NodeA(1, 0, 20);

        assertEquals(1, calculateCost(current, target, b));
    }

    @Test
    void calculateCostV4() {
        BoxMap b = new BoxMap(10, 0, 0);
        NodeA current = new NodeA(3, 4, 0);
        NodeA target = new NodeA(1, 8, 20);

        assertEquals(4.47213595499958, calculateCost(current, target, b));
    }
}