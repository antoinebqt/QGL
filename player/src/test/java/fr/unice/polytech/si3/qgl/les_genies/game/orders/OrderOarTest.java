package fr.unice.polytech.si3.qgl.les_genies.game.orders;

import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Oar;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.LogicRotation;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.OarConfig;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Deck;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderOarTest {

    Ship ship;
    RegattaGoal rg;
    ArrayList<Entity> e;

    @BeforeEach
    void setUp(){
        ship = new Ship();
        ship.setDeck(new Deck(2,3));
        Checkpoint c = new Checkpoint(new Position(1000,0,0),new Circle(100));
        Checkpoint[] checks = new Checkpoint[]{c};
        rg = new RegattaGoal("race",checks);

        ship.setPosition(new Position(0,0));

        e = new ArrayList<>();
        e.add(new Oar(0,0));
        e.add(new Oar(1,0));
        e.add(new Oar(2,0));
        e.add(new Oar(0,1));
        e.add(new Oar(1,1));
        e.add(new Oar(2,1));

        ship.setEntities(e);
    }

    @Test
    void OrderOarConstructorTest(){
        Order.clearEntities();
        LogicRotation lr =  new LogicRotation(ship);
        OrderOar oa = new OrderOar(ship,lr,rg,10);
        assertEquals(3,oa.getLeftOar());
        assertEquals(3,oa.getRightOar());
        assertEquals(6,Order.getList().size());
    }

}