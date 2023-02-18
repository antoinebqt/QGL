package fr.unice.polytech.si3.qgl.les_genies.game.crew;

import fr.unice.polytech.si3.qgl.les_genies.game.entities.Sail;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Deck;
import fr.unice.polytech.si3.qgl.les_genies.game.actions.ActMove;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShiftingTest {

    Entity entity;
    Sailor sailor1;
    Sailor sailor2;
    Shifting shift;
    Sailor[] sat;
    List<Entity> entities;
    Deck deck;
    Entity ent;

    @BeforeEach
    void setUp(){
        deck = new Deck(2,4);
        sailor1 = new Sailor(1,"conor",2,1);
        sailor2 = new Sailor(2,"ckr",0,1);
        entity = new Entity(1,0);
        entities = new ArrayList<>();
        ent = new Entity(0,0);
        entities.add(entity);
        entities.add(ent);
        sat = new Sailor[]{sailor1,sailor2};
        shift=new Shifting(sat,deck);
    }

    @Test
    void goForThisEntity_ok(){
        ActMove movi = shift.goForThisEntity(sailor1,entity);
        assertTrue(sailor1.checkIfOnEntity(entity));
        assertTrue(entity.isFocus());
        assertSame(entity.getSailor(), sailor1);
        assertEquals(new ActMove(-1,-1,sailor1).toString(),movi.toString());
    }
    @Test
    void goForThisEntity_deja_dessus() {
        assertNotNull(shift.goForThisEntity(new Sailor(1, "riko", 1, 0), entity));
        assertEquals(new ActMove(0, 0, sailor1).toString(), shift.goForThisEntity(new Sailor(1, "riko", 1, 0), entity).toString());
    }
    @Test
    void goForThisEntity_pas_deja_dessus() {
        assertNotEquals(new ActMove(0, 0, sailor1).toString(), shift.goForThisEntity(new Sailor(1, "riko", 0, 0), entity).toString());
    }
    @Test
    void shiftSailorToUsefullEntity_null(){
        entities.clear();
        List<ActMove> emptyList = new ArrayList<>();
        assertEquals(emptyList,shift.shiftSailorToUsefulEntity(entities));
    }

    @Test
    void shiftSailorToUsefullEntity_OK(){
        List<ActMove> mov = shift.shiftSailorToUsefulEntity(entities);
        assertEquals(new ActMove(-1,-1,sailor1).toString(),mov.get(0).toString());
        assertEquals(new ActMove(0,-1,sailor2).toString(),mov.get(1).toString());
    }

    @Test
    void shiftSailorToUsefullEntity_Cas_Sailor_Sur_Entite(){
        Sailor sus = new Sailor(5,"jeff",0,0);
        Sailor suss = new Sailor(5,"leff",1,0);
        Sailor[] tab = new Sailor[]{suss,sus};
        Shifting shoft = new Shifting(tab,deck);
        List<ActMove> mov = shoft.shiftSailorToUsefulEntity(entities);
        assertFalse(mov.isEmpty());
        assertEquals(new ActMove(0,0,sus).toString(),mov.get(0).toString());
        assertEquals(new ActMove(0,0,suss).toString(),mov.get(1).toString());

    }

    @Test
    void shiftSailorToUSEfullEntity_CAS_Liste_VIDE(){
        Sailor sus = new Sailor(5,"jeff",0,0);
        Sailor suss = new Sailor(5,"leff",1,0);
        Sailor[] tab = new Sailor[]{suss,sus};
        Shifting shoft = new Shifting(tab,deck);
        List<Entity> listVide = new ArrayList<>();

        List<ActMove> mov = shoft.shiftSailorToUsefulEntity(listVide);
        assertEquals(0,mov.size());

    }
    @Test
    void goForThisEntity_shiftSup5_GetAction() {
        Sailor sailtest = new Sailor(1, "test", 0, 0);
        Entity entity = new Entity(5, 5);
        Sailor[] tab = new Sailor[]{sailtest};
        Deck dock = new Deck(5, 5);
        Shifting shifting = new Shifting(tab, dock);
        ActMove mov = shifting.goForThisEntity(sailtest, entity);
        assertEquals(0, sailtest.getX());
        assertEquals(5, sailtest.getY());
        assertEquals("{\"sailorId\": 1,\"type\": \"MOVING\",\"xdistance\": 0,\"ydistance\": 5}", mov.toString());
    }
    @Test
    void shiftSailor_3sailor_1dejaSurEntity(){
        Sailor sett = new Sailor(15,"sett",0,2);
        sett.setOnEntity(true);
        Entity entett = new Entity(0,2);
        entities.add(entett);
        Sailor[] sat2 = new Sailor[]{sailor1,sailor2,sett};
        Shifting shift2 =new Shifting(sat2,deck);

        List<ActMove> movs = shift2.shiftSailorToUsefulEntity(entities);
        assertEquals(2,movs.size());
        assertNotEquals(new ActMove(0,0,sett).toString(),movs.get(0).toString());
        assertNotEquals(new ActMove(0,0,sett).toString(),movs.get(1).toString());
    }
}
