package fr.unice.polytech.si3.qgl.les_genies.game.orders;

import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Watch;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderWatchTest {

    ArrayList<Entity> e;
    Ship ship;

    @BeforeEach
    void setUp() {

        ship = new Ship();
        Watch a1 = new Watch(0,0);
        Watch a2 = new Watch(1,0);

        e = new ArrayList<>();
        e.add(a1);
        e.add(a2);

        ship.setEntities(e);
    }

    @Test
    void addWatchTestFirstRound(){
        Order.usefulEntity.clear();
        OrderWatch ow = new OrderWatch(ship,0,10);
        assertEquals(1,Order.usefulEntity.size());
    }

    @Test
    void addWatchTestRound2(){
        Order.usefulEntity.clear();
        OrderWatch ow = new OrderWatch(ship,2,10);
        assertEquals(0,Order.usefulEntity.size());
    }

    @Test
    void addWatchTestRound10(){
        Order.usefulEntity.clear();
        OrderWatch ow = new OrderWatch(ship,10,10);
        assertEquals(1,Order.usefulEntity.size());
    }

    @Test
    void addWatchTestButZeroSailor(){
        Order.usefulEntity.clear();
        OrderWatch ow = new OrderWatch(ship,10,0);
        assertEquals(0,Order.usefulEntity.size());
    }
}