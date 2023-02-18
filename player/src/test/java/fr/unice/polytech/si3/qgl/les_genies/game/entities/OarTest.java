package fr.unice.polytech.si3.qgl.les_genies.game.entities;

import fr.unice.polytech.si3.qgl.les_genies.game.ship.SideOfBoat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OarTest {

    @Test
    void sideTest_Babord(){
        Oar oar = new Oar(1,0);
        assertEquals(SideOfBoat.BABORD,oar.getSide());
    }

    @Test
    void sideTest_Tribord(){
        Oar oar = new Oar(1,4);
        assertEquals(SideOfBoat.TRIBORD,oar.getSide());
    }

    @Test
    void toStringNotNullTest(){
        Oar oar = new Oar(0,0);
        assertNotEquals("",oar.toString());
    }
}