package fr.unice.polytech.si3.qgl.les_genies.game.tools;

import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

class ToolTest {

    Tool t;

    @BeforeEach
    void ToolTest(){
        t = new Tool(new Position(0,0,0));
    }

    @Test
    void distanceOarTest(){
        double d1 = t.oarSpeed(2,2,4);
        double d2 = t.oarAngle(2,2,4);

        assertEquals(165.0,d1);
        assertEquals(0.0,d2);

        Position newPosition = t.distanceOar(2,2,4);

        assertEquals(165,Math.round(newPosition.getX()));
        assertEquals(0, newPosition.getY());
        assertEquals(0, newPosition.getOrientation());
    }

    @Test
    void distanceOarAndRudderTest(){
        Position newPosition = t.distanceOarAndRudder(2,2,4, Math.PI/4);

        assertEquals(149,Math.round(newPosition.getX()));
        assertEquals(62, Math.round(newPosition.getY()));

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(5);


        assertEquals(df.format(Math.PI/4), df.format(newPosition.getOrientation()));
    }

    @Test
    void distanceOarAndRudderAndSailTest(){
        Position newPosition = t.distanceOarAndRudderAndSail(2,2,4, 0, 1,2, new Wind(0,10));

        assertEquals(170,Math.round(newPosition.getX()));
        assertEquals(0, Math.round(newPosition.getY()));
        assertEquals(0, newPosition.getOrientation());
    }

    @Test
    void distanceSailTest(){
        Wind w = new Wind(0,100);

        double d = t.sailSpeed(1,2,w,0.0);

        assertEquals(50, d);
    }

    @Test
    void sailSpeedNull(){
        double speed = t.sailSpeed(5,5,null,0);
        assertEquals(0,speed);
    }

    @Test
    void sailSpeedOK(){
        double speed = t.sailSpeed(4,5,new Wind(0,80),0);
        assertEquals(64,speed);
    }

    @Test
    void angleBetween_ok(){
        assertEquals(3,t.angleBetween(6,3));
    }

    @Test
    void oarAngle_ok(){
        assertEquals(Math.PI/4,t.oarAngle(2,1,4));
    }
}
