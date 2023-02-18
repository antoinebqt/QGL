package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Oar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActOarTest {

    String TEST_PATTERN = "{" +
            "\"sailorId\": " + 2 +
            ",\"type\": \"OAR\"" +
            '}';

    @Test
    void setSailorTest(){
        Sailor sailor = new Sailor(2,"Adam",0,0);
        Oar oar = new Oar(0,0);
        ActOar actOar = new ActOar(sailor,oar);
        assertTrue(sailor.isBusy());
        assertEquals(sailor,oar.getSailor());
    }

    @Test
    void testToString() {
        Sailor sailor = new Sailor(2, "Adam", 0, 0);
        ActOar actOar = new ActOar(sailor,new Oar(0,0));
        assertEquals(TEST_PATTERN,actOar.toString());
    }
}
