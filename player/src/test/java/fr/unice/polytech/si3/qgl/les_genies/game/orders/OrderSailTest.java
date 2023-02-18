package fr.unice.polytech.si3.qgl.les_genies.game.orders;

import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Oar;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Sail;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.LogicRotation;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderSailTest {

    OrderSail os;
    Ship ship;
    Wind wind;
    ArrayList<Entity> e;

    @BeforeEach
    void setUp(){

        Sail a1 = new Sail(0,0,false);
        Sail a2 = new Sail(0,0,true);
        ship = new Ship();
        e = new ArrayList<>();
        e.add(a1);
        e.add(a2);
        ship.setEntities(e);
        wind = new Wind();
        ship.setPosition(new Position(0,0,0));

    }

    @Test
    void doableUsingOarTestWithFarCheckpoint(){

        Checkpoint c = new Checkpoint(new Position(1000,1000,0),new Circle(100));
        Checkpoint[] checks = new Checkpoint[]{c};
        RegattaGoal g = new RegattaGoal("race",checks);
        os = new OrderSail(ship,g,wind,10);

        assertFalse(os.doableUsingOar(g));
    }

    @Test
    void doableUsingOarTestWithCloseCheckpoint(){

        Checkpoint c = new Checkpoint(new Position(10,10,0),new Circle(100));
        Checkpoint[] checks = new Checkpoint[]{c};
        RegattaGoal g = new RegattaGoal("race",checks);
        os = new OrderSail(ship,g,wind,10);

        assertTrue(os.doableUsingOar(g));
    }

}