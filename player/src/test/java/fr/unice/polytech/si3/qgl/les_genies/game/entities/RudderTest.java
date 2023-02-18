package fr.unice.polytech.si3.qgl.les_genies.game.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RudderTest {
    @Test
    void createRudder(){
        Rudder rudder = new Rudder(10,100);
        assertNotEquals("",rudder.toString());
    }
}
