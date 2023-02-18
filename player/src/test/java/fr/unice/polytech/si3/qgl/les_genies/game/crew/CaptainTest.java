package fr.unice.polytech.si3.qgl.les_genies.game.crew;

import fr.unice.polytech.si3.qgl.les_genies.game.actions.*;
import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Sail;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.LogicRotation;
import fr.unice.polytech.si3.qgl.les_genies.game.orders.Order;
import fr.unice.polytech.si3.qgl.les_genies.game.orders.OrderRudder;
import fr.unice.polytech.si3.qgl.les_genies.game.orders.OrderSail;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Deck;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CaptainTest {

    Deck deck;
    Ship ship;
    Entity entity;
    Sailor sailor;
    Captain captain;
    Sailor[] sat;
    List<Entity> entities;
    List<Action> act;

    @BeforeEach
    void setUp(){
        deck = new Deck(2,4);
        ship = new Ship();
        ship.setDeck(deck);
        sailor = new Sailor(1,"conor",2,1);
        sat = new Sailor[]{sailor};
        entities = new ArrayList<>();
        entity = new Entity(1,0);
        entity.setType("oar");
        entities.add(entity);
        ship.setEntities(entities);
        captain = new Captain(ship,sat);
        act = new ArrayList<>();
    }


    @Test
    void resetOrder_OK(){
        act.add(new ActOar(sailor,entity));
        act.add(new ActOar(sailor,entity));
        captain.setOrdersAction(act);
        captain.resetOrder();
        assertTrue(captain.getOrdersAction().isEmpty());
        assertFalse(sailor.isBusy());
        assertFalse(entity.isActive());
    }

    @Test
    void doableUsingOarTest(){
        Position p = new Position(100,100,0);
        Checkpoint c = new Checkpoint(p, new Circle(10));

        ship.setPosition(new Position(0,0,0));

        RegattaGoal goal = mock(RegattaGoal.class);
        when(goal.getCurrentCheckpoint()).thenReturn(c);

        OrderSail os = new OrderSail(ship, goal, new Wind(0,0), 10);

        assertTrue(os.doableUsingOar(goal));
    }

    @Test
    void doableUsingOarTest2(){
        Position p = new Position(200,200,0);
        Checkpoint c = new Checkpoint(p, new Circle(10));

        ship.setPosition(new Position(0,0,0));

        RegattaGoal goal = mock(RegattaGoal.class);
        when(goal.getCurrentCheckpoint()).thenReturn(c);

        OrderSail os = new OrderSail(ship, goal, new Wind(0,0),10);

        assertFalse(os.doableUsingOar(goal));
    }


    @Test
    void radToDegTest0() {
        assertEquals(0, captain.radToDeg(0));
    }

    @Test
    void radToDegTest() {
        assertEquals(74.48451336700703, captain.radToDeg(1.3));
    }

    @Test
    void everyoneReachedTheEntityTestAllReached() {
        Sailor sailor = mock(Sailor.class);
        Entity entity = mock(Entity.class);
        when(entity.getSailor()).thenReturn(sailor);
        when(sailor.checkIfOnEntity(entity)).thenReturn(true);

        List<Entity> entities = new ArrayList<>();
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);

        assertTrue(captain.everyoneReachedTheEntity(entities));
    }

    @Test
    void everyoneReachedTheEntityTestNotAllReached() {
        Sailor sailor = mock(Sailor.class);
        Sailor sailorNotOnEntity = mock(Sailor.class);
        Entity entity1 = mock(Entity.class);
        Entity entity2 = mock(Entity.class);

        when(entity1.getSailor()).thenReturn(sailor);
        when(sailor.checkIfOnEntity(entity)).thenReturn(true);

        when(entity2.getSailor()).thenReturn(sailorNotOnEntity);
        when(sailorNotOnEntity.checkIfOnEntity(entity2)).thenReturn(false);

        List<Entity> entities = new ArrayList<>();
        entities.add(entity1);
        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity1);

        assertFalse(captain.everyoneReachedTheEntity(entities));
    }

    @Test
    void getActionTestOar() {
        Entity entity = mock(Entity.class);
        when(entity.getType()).thenReturn("oar");
        when(entity.getSailor()).thenReturn(new Sailor(0, "Richard", 0, 0));

        assertTrue(captain.getAction(entity) instanceof ActOar);
    }

    @Test
    void getActionTestRudder() {
        Entity entity = mock(Entity.class);
        when(entity.getType()).thenReturn("rudder");
        when(entity.getSailor()).thenReturn(new Sailor(0, "Richard", 0, 0));

        LogicRotation lr = mock(LogicRotation.class);
        when(lr.getAngleRudder()).thenReturn(0.0);
        captain.setLogicRotation(lr);

       assertTrue(captain.getAction(entity) instanceof ActRudder);
    }

    @Test
    void getActionTestSailNotOpened() {
        Entity entity = new Sail(0, 0, false);
        entity.setType("sail");
        entity.setFocusedBy(new Sailor(0, "Richard", 0, 0));

        assertTrue(captain.getAction(entity) instanceof ActLiftSail);
    }

    @Test
    void getActionTestSailOpened() {
        Entity entity = new Sail(0, 0, true);
        entity.setType("sail");
        entity.setFocusedBy(new Sailor(0, "Richard", 0, 0));

        assertTrue(captain.getAction(entity) instanceof ActLowerSail);
    }

    @Test
    void getActionTestRudderNonExist() {
        Entity entity = mock(Entity.class);
        when(entity.getType()).thenReturn("not existing");
        when(entity.getSailor()).thenReturn(new Sailor(0, "Richard", 0, 0));

        assertNull(captain.getAction(entity));
    }

    @Test
    void setLogicRotationAndGetLogicRotationTest() {
        LogicRotation lr = mock(LogicRotation.class);
        captain.setLogicRotation(lr);
        assertEquals(lr, captain.getLogicRotation());
    }

    @Test
    void ordersRudderTest() {
        Order.clearEntities();
        LogicRotation lr = mock(LogicRotation.class);
        when(lr.getAngleRudder()).thenReturn(1.0);
        captain.setLogicRotation(lr);

        Entity rudder = mock(Entity.class);
        when(rudder.getType()).thenReturn("rudder");
        ship.getEntities().add(rudder);

        OrderRudder or = new OrderRudder(ship, captain.getLogicRotation(),10);
        assertEquals(1, or.getList().size());
    }

    @Test
    void ordersRudderTestAngle0() {
        Order.clearEntities();
        LogicRotation lr = mock(LogicRotation.class);
        when(lr.getAngleRudder()).thenReturn(0.0);
        captain.setLogicRotation(lr);

        Entity rudder = mock(Entity.class);
        when(rudder.getType()).thenReturn("rudder");
        ship.getEntities().add(rudder);

        OrderRudder or = new OrderRudder(ship, captain.getLogicRotation(),10);
        assertEquals(0, or.getList().size());
    }

    @Test
    void getTheRudderTest() {
        Entity rudder = mock(Entity.class);
        when(rudder.getType()).thenReturn("rudder");
        ship.getEntities().add(rudder);
        Order o = new Order(ship);

        assertEquals(rudder, o.getFirstEntityByType("rudder"));
    }

    @Test
    void getTheRudderTestNoRudder() {
        Entity oar = mock(Entity.class);
        when(oar.getType()).thenReturn("oar");
        ship.getEntities().add(oar);

        Order o = new Order(ship);

        assertNull(o.getFirstEntityByType("rudder"));
    }

    @Test
    void orderSailsTestNotCloseEnoughCanOpenSailAndClosedSail() {
        ship.setPosition(new Position(0,0,0));

        Entity sail = mock(Entity.class);
        when(sail.getType()).thenReturn("sail");
        when(sail.isOpenned()).thenReturn(false);
        ship.getEntities().add(sail);

        RegattaGoal goal = mock(RegattaGoal.class);
        when(goal.getCurrentCheckpoint()).thenReturn(new Checkpoint(new Position(1000, 0, 0), new Circle(100)));

        OrderSail os = new OrderSail(ship, goal, new Wind(0,0),10);

        assertTrue(os.getList().contains(sail));
    }

    @Test
    void orderSailsTestNotCloseEnoughCanOpenSailAndOpenedSail() {
        ship.setPosition(new Position(0,0,0));

        Entity sail = mock(Entity.class);
        when(sail.getType()).thenReturn("sail");
        when(sail.isOpenned()).thenReturn(true);
        ship.getEntities().add(sail);

        RegattaGoal goal = mock(RegattaGoal.class);
        when(goal.getCurrentCheckpoint()).thenReturn(new Checkpoint(new Position(1000, 0, 0), new Circle(100)));

        OrderSail os = new OrderSail(ship, goal, new Wind(0,10),10);

        assertFalse(os.getList().contains(sail));
    }

    @Test
    void orderSailsTestNotCloseEnoughCantOpenSailAndOpenedSail() {
        ship.setPosition(new Position(0,0,0));

        Entity sail = mock(Entity.class);
        when(sail.getType()).thenReturn("sail");
        when(sail.isOpenned()).thenReturn(true);
        ship.getEntities().add(sail);

        RegattaGoal goal = mock(RegattaGoal.class);
        when(goal.getCurrentCheckpoint()).thenReturn(new Checkpoint(new Position(1000, 0, 0), new Circle(100)));

        OrderSail os = new OrderSail(ship, goal, new Wind(2,10),10);

        assertTrue(os.getList().contains(sail));
    }

    @Test
    void orderSailsTestCloseEnoughAndClosedSail() {
        ship.setPosition(new Position(0,0,0));

        Entity sail = mock(Entity.class);
        when(sail.getType()).thenReturn("sail");
        when(sail.isOpenned()).thenReturn(false);
        ship.getEntities().add(sail);

        RegattaGoal goal = mock(RegattaGoal.class);
        when(goal.getCurrentCheckpoint()).thenReturn(new Checkpoint(new Position(50, 0, 0), new Circle(100)));

        OrderSail os = new OrderSail(ship, goal, new Wind(0,10),10);

        assertFalse(os.getList().contains(sail));
    }

    @Test
    void orderSailsTestCloseEnoughAndOpenedSail() {
        ship.setPosition(new Position(0,0,0));

        Entity sail = mock(Entity.class);
        when(sail.getType()).thenReturn("sail");
        when(sail.isOpenned()).thenReturn(true);
        ship.getEntities().add(sail);

        RegattaGoal goal = mock(RegattaGoal.class);
        when(goal.getCurrentCheckpoint()).thenReturn(new Checkpoint(new Position(50, 0, 0), new Circle(100)));

        OrderSail os = new OrderSail(ship, goal, new Wind(0,10),10);

        assertTrue(os.getList().contains(sail));
    }

    @Test
    void doableUsingOarTestNotCloseEnough() {
        ship.setPosition(new Position(0,0,0));
        RegattaGoal goal = mock(RegattaGoal.class);
        when(goal.getCurrentCheckpoint()).thenReturn(new Checkpoint(new Position(1000, 0, 0), new Circle(100)));

        OrderSail os = new OrderSail(ship, goal, new Wind(0,0),10);

        assertFalse(os.doableUsingOar(goal));
    }

    @Test
    void doableUsingOarTestCloseEnough() {
        ship.setPosition(new Position(0,0,0));
        RegattaGoal goal = mock(RegattaGoal.class);
        when(goal.getCurrentCheckpoint()).thenReturn(new Checkpoint(new Position(50, 0, 0), new Circle(100)));

        OrderSail os = new OrderSail(ship, goal, new Wind(0,0),10);


        assertTrue(os.doableUsingOar(goal));
    }

    @Test
    void getTheSailTestNoSail() {
        Entity noSail = mock(Entity.class);
        when(noSail.getType()).thenReturn("not a sail");
        ship.getEntities().add(noSail);

        Order o = new Order(ship);

        assertNull(o.getFirstEntityByType("sail"));
    }

    @Test
    void getTheSailTestSail() {
        Entity sail = mock(Entity.class);
        when(sail.getType()).thenReturn("sail");
        ship.getEntities().add(sail);

        Order o = new Order(ship);

        assertEquals(sail, o.getFirstEntityByType("sail"));
    }

/*
    @Test
    void selectOarToUseTestLeftOarY0() {
        Entity oar = mock(Entity.class);
        when(oar.getType()).thenReturn("oar");
        when(oar.getY()).thenReturn(0);
        entities.clear();
        entities.add(oar);

        OarConfig oarConfig = new OarConfig(1,0);

        captain.selectOarToUse(oarConfig);

        assertTrue(captain.getUsefulEntity().contains(oar));
        assertEquals(0, oarConfig.getOarLeftSide());
    }

    @Test
    void selectOarToUseTestLeftOarY2() {
        Entity oar = mock(Entity.class);
        when(oar.getType()).thenReturn("oar");
        when(oar.getY()).thenReturn(2);
        entities.clear();
        entities.add(oar);

        OarConfig oarConfig = new OarConfig(1,0);

        captain.selectOarToUse(oarConfig);

        assertFalse(captain.getUsefulEntity().contains(oar));
        assertEquals(1, oarConfig.getOarLeftSide());
    }

    @Test
    void selectOarToUseTestRightOarY0() {
        Entity oar = mock(Entity.class);
        when(oar.getType()).thenReturn("oar");
        when(oar.getY()).thenReturn(0);
        entities.clear();
        entities.add(oar);

        OarConfig oarConfig = new OarConfig(0,1);

        captain.selectOarToUse(oarConfig);

        assertFalse(captain.getUsefulEntity().contains(oar));
        assertEquals(1, oarConfig.getOarRightSide());
    }

    @Test
    void selectOarToUseTestRightOarY2() {
        Entity oar = mock(Entity.class);
        when(oar.getType()).thenReturn("oar");
        when(oar.getY()).thenReturn(2);
        entities.clear();
        entities.add(oar);

        OarConfig oarConfig = new OarConfig(0,1);

        captain.selectOarToUse(oarConfig);

        assertTrue(captain.getUsefulEntity().contains(oar));
        assertEquals(0, oarConfig.getOarRightSide());
    }*/

    @Test
    void resetOrderTest() {
        captain.getOrdersAction().add(mock(ActOar.class));
        captain.getUsefulEntity().add(mock(Entity.class));
        sailor.setOnEntity(true);
        entity.setFocusedBy(sailor);

        captain.resetOrder();

        assertEquals(0, captain.getOrdersAction().size());
        assertEquals(0, captain.getUsefulEntity().size());
        assertFalse(sailor.isOnEntity());
        assertFalse(entity.isFocus());
    }
}
