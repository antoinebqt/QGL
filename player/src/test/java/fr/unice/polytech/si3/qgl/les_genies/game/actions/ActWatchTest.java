package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Watch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActWatchTest {

    String TEST_PATTERN = "{" +
            "\"sailorId\": 2," +
            "\"type\": \"USE_WATCH\"" +
            '}';

    @Test
    void setSailorTest(){
        Sailor sailor = new Sailor(2,"Adam",0,0);
        Watch watch = new Watch(0,0);
        ActWatch actWatch = new ActWatch(sailor,watch);
        assertTrue(sailor.isBusy());
        assertEquals(sailor,watch.getSailor());
    }

    @Test
    void toString_Test(){
        Sailor sailor = new Sailor(2,"Adam",0,0);
        ActWatch actWatch = new ActWatch(sailor,new Watch(0,0));
        assertEquals(TEST_PATTERN,actWatch.toString());
    }

}
