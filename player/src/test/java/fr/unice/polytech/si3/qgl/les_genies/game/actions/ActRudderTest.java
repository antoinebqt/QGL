package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Rudder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActRudderTest {

    String TEST_PATTERN = "{" +
            "\"sailorId\": 2," +
            "\"type\": \"TURN\"," +
            "\"rotation\": 0.5" +
            '}';

    @Test
    void setSailorTest(){
        Sailor sailor = new Sailor(2,"Adam",0,0);
        Rudder rudder = new Rudder(0,0);
        ActRudder actRudder = new ActRudder(sailor,rudder,0.5);
        assertTrue(sailor.isBusy());
        assertEquals(sailor,rudder.getSailor());
        assertEquals(0.5,actRudder.getOrientation());
    }

    @Test
    void toString_Test(){
        Sailor sailor = new Sailor(2,"Adam",0,0);
        ActRudder actRudder = new ActRudder(sailor,new Rudder(0,0),0.5);
        assertEquals(TEST_PATTERN,actRudder.toString());
    }
}
