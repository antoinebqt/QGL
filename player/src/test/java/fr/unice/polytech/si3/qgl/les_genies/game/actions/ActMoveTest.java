package fr.unice.polytech.si3.qgl.les_genies.game.actions;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActMoveTest {

    String TEST_PATTERN = "{" +
            "\"sailorId\": 2," +
            "\"type\": \"MOVING\"," +
            "\"xdistance\": 1," +
            "\"ydistance\": 0" +
            "}";

    @Test
    void toString_Test(){
        Sailor sailor = new Sailor(2,"Adam",0,0);
        ActMove actMove = new ActMove(1,0,sailor);
        assertEquals(TEST_PATTERN,actMove.toString());
    }
}
