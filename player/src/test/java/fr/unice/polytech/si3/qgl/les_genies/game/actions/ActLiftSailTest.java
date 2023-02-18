package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Sail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActLiftSailTest {

    String TEST_PATTERN = "{" +
            "\"sailorId\":" + 2 +
            ", \"type\":\"LIFT_SAIL\"" +
            '}';

    @Test
    void setSailorTest(){
        Sailor sailor = new Sailor(2,"Adam",0,0);
        Sail sail = new Sail(0,0,false);
        ActLiftSail actLiftSail = new ActLiftSail(sailor,sail);
        assertTrue(sailor.isBusy());
        assertEquals(sailor,sail.getSailor());
        assertTrue(sail.isOpenned());
    }

    @Test
    void testToString() {
        Sailor sailor = new Sailor(2, "Adam", 0, 0);
        ActLiftSail actLiftSail = new ActLiftSail(sailor,new Sail(0,0,false));
        assertEquals(TEST_PATTERN,actLiftSail.toString());
    }
}
