package fr.unice.polytech.si3.qgl.les_genies.game.orders;

import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Oar;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {


    ArrayList<Entity> e;
    Ship ship;

    @BeforeEach
    void setUp(){
        ship = new Ship();
        Oar a1 = new Oar(0,0);
        Oar a2 = new Oar(1,0);
        Oar a3 = new Oar(2,0);
        Oar a4 = new Oar(0,0);
        Oar a5 = new Oar(0,1);
        Oar a6 = new Oar(0,2);

        e = new ArrayList<>();
        e.add(a1);
        e.add(a2);
        e.add(a3);
        e.add(a4);
        e.add(a5);
        e.add(a6);

        ship.setEntities(e);

    }

    @Test
    void clearEntitiesTest(){
        Order.usefulEntity.add(new Oar(0,0));
        Order.clearEntities();
        assertEquals(0,Order.getList().size());
    }

    @Test
    void getNumberOfOarTest(){
        Order o = new Order(ship);
        assertEquals(6,o.getNumberOf("oar"));
    }

    @Test
    void getNumberOfSailTest(){
        Order o = new Order(ship);
        assertEquals(0,o.getNumberOf("sail"));
    }

    @Test
    void aSailorIsFreeTestWithOneSailor(){
        Order o = new Order(ship);
        Order.clearEntities();
        OrderSail.usefulEntity.add(new Oar(0,0));
        assertFalse(o.aSailorIsFree(1));
    }

    @Test
    void aSailorIsFreeTestWithTwoSailor(){
        Order o = new Order(ship);
        Order.clearEntities();
        OrderSail.usefulEntity.add(new Oar(0,0));
        assertTrue(o.aSailorIsFree(2));
    }

}
