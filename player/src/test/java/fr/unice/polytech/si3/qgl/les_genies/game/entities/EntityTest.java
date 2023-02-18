package fr.unice.polytech.si3.qgl.les_genies.game.entities;

import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {
    Sailor sailor1;
    Sailor sailor2;
    Sailor sailor3;
    Entity entity;
    List<Sailor> liste;
    @BeforeEach
    void setUp(){
        sailor1 = new Sailor(1,"coco",0,0);
        sailor2 = new Sailor(2,"kiki",2,1);
        sailor3 = new Sailor(3,"koik",3,1);
        entity = new Entity(3,0);
        liste = new ArrayList<>();
        liste.add(sailor1);
        liste.add(sailor2);
        liste.add(sailor3);
    }
    @Test
    void distanceTest(){
        Oar oar = new Oar(2,0);
        Sailor sailor = new Sailor(1,"keke",4,4);
        assertEquals(6,oar.distanceFromSailor(sailor));
    }
    @Test
    void muchCloserIfNoOne(){
        liste.clear();
        assertNull(entity.muchCloserSailor(liste));
    }
    @Test
    void muchCloserTest(){
        assertEquals(sailor3,entity.muchCloserSailor(liste));
    }
    @Test
    void muchCloserIfSomeoneIsOnIt(){
        Entity entity1 = new Entity(0,0);
        assertEquals(sailor1,entity1.muchCloserSailor(liste));
    }
    @Test
    void muchCloserIfNearEntityIsTaken(){
        sailor3.setOnEntity(true);
        assertEquals(sailor2,entity.muchCloserSailor(liste));
    }

    @Test
    void muchCloserIfEveryoneOnAnEntity(){
        liste.forEach(sailor -> sailor.setOnEntity(true));
        assertNull(entity.muchCloserSailor(liste));
    }

    @Test
    void getTest(){
        entity.setX(10);
        entity.setY(100);
        assertEquals(10,entity.getX());
        assertEquals(100,entity.getY());
        assertFalse(entity.isActive());
        assertFalse(entity.isLoaded());
        assertEquals(0,entity.getAngleEntity());
        assertFalse(entity.isOpenned());
    }
}
