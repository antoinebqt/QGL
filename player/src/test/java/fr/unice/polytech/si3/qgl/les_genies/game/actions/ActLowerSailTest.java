package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Sail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActLowerSailTest {

    String TEST_PATTERN = "{" +
            "\"sailorId\":" + 2 +
            ", \"type\":\"LOWER_SAIL\"" +
            '}';

    @Test
    void setSailorTest(){
        Sailor sailor = new Sailor(2,"Adam",0,0);
        Sail sail = new Sail(0,0,true);
        ActLowerSail actLowerSail = new ActLowerSail(sailor,sail);
        assertTrue(sailor.isBusy());
        assertEquals(sailor,sail.getSailor());
        assertFalse(sail.isOpenned());
    }

    @Test
    void testToString() {
        Sailor sailor = new Sailor(2, "Adam", 0, 0);
        ActLowerSail actLowerSail = new ActLowerSail(sailor,new Sail(0,0,true));
        assertEquals(TEST_PATTERN,actLowerSail.toString());
    }
}
