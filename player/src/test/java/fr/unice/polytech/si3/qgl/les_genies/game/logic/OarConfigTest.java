package fr.unice.polytech.si3.qgl.les_genies.game.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OarConfigTest {
    @Test
    void removeOarEachSideTest(){
        int r = 5;
        int l = 6;
        OarConfig oc = new OarConfig(l,r);

        oc.removeOarEachSide();

        assertEquals(l-1,oc.getOarLeftSide());
        assertEquals(r-1,oc.getOarRightSide());
    }
}
