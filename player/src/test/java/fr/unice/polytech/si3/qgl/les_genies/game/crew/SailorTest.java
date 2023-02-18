package fr.unice.polytech.si3.qgl.les_genies.game.crew;

import fr.unice.polytech.si3.qgl.les_genies.game.ship.Deck;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SailorTest {
    Deck deck;
    Sailor sailor;
    @BeforeEach
    void setUp(){
        deck = new Deck(3,2);
        sailor = new Sailor(1,"leo",1,0);
    }
    @Test
    void move_Test_ok() throws Exception{
        sailor.move(1,1,deck);//valide
        assertEquals(2,sailor.getX());//change
        assertEquals(1,sailor.getY());
    }
    @Test
    void move_Test_pbAbscisse(){
        try {
            sailor.move(5, 1, deck);//invalide
        }catch (Exception e){
            assertNotNull(e);
            assertEquals(Constants.MESSAGE_ERR_ON_MOVE,e.getMessage());
        }finally {
            assertEquals(1,sailor.getX());//pas changer
            assertEquals(0,sailor.getY());
        }


    }
    @Test
    void move_Test_pbOrdonne(){
        try {
            sailor.move(1,5,deck);//invalide
        }catch (Exception e){
            assertNotNull(e);
            assertEquals(Constants.MESSAGE_ERR_ON_MOVE,e.getMessage());
        }finally {
            assertEquals(1,sailor.getX());//pas changer
            assertEquals(0,sailor.getY());
        }
    }
    @Test
    void move_Test_pbAbsNegatif(){
        try {
            sailor.move(-5,0,deck);//invalide
        }catch (Exception e){
            assertNotNull(e);
            assertEquals(Constants.MESSAGE_ERR_ON_MOVE,e.getMessage());
        }finally {
            assertEquals(1,sailor.getX());//pas changer
            assertEquals(0,sailor.getY());
        }
    }

    @Test
    void checkIfOnEntity_ok(){
        Entity isOn = new Entity(1,0);
        Entity notOn = new Entity(2,0);
        assertTrue(sailor.checkIfOnEntity(isOn));
        assertFalse(sailor.checkIfOnEntity(notOn));
    }

    @Test
    void remakeSailorShift_OOk(){
        int[] tab = sailor.remakeSailorShift(3,4);
        assertEquals(1,tab[0]);
        assertEquals(4,tab[1]);

    }

    @Test
    void move_Test_ToooFast_So_itsCorrected(){
        deck = new Deck(4,5);
        sailor = new Sailor(12,"aigri",0,0);
        sailor.move(3,3,deck);
        assertEquals(2,sailor.getX());
        assertEquals(3,sailor.getY());
    }

    @Test
    void remakeSailorShift_cas_limit(){
        int x = 10;
        int y = 0;
        int[] tab = sailor.remakeSailorShift(x,y);
        assertEquals(5,tab[0]);
        assertEquals(0,tab[1]);
    }
    @Test
    void remakeSailorShift_cas_limit_negatif(){
        int x = -10;
        int y = 4;
        int[] tab = sailor.remakeSailorShift(x,y);
        assertEquals(-1,tab[0]);
        assertEquals(4,tab[1]);
    }
    @Test
    void remakeSailorShift_cas_limit_surY(){
        int x = 0;
        int y = 8;
        int[] tab = sailor.remakeSailorShift(x,y);
        assertEquals(0,tab[0]);
        assertEquals(5,tab[1]);
    }
    @Test
    void remakeSailorShift_cas_pasUtil(){
        int x = 3;
        int y = 2;
        int[] tab = sailor.remakeSailorShift(x,y);
        assertEquals(3,tab[0]);
        assertEquals(2,tab[1]);
    }


}