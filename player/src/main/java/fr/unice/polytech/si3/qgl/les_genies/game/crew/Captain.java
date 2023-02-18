package fr.unice.polytech.si3.qgl.les_genies.game.crew;

import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.orders.*;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.actions.*;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.Game;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.LogicRotation;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Captain class use as the brain of the boat, it ll gave all instructions
 */
public class Captain {

    private final Ship ship;
    private final Sailor[] sailors;
    private Game game;
    private List<Action> ordersAction;
    private final List<Entity> usefulEntity;
    private LogicRotation logicRotation;
    private boolean endExecution;
    private int round;

    /**
     * constructor that is used for test
     */
    public Captain(Ship ship,Sailor[] sailors) {
        this.ship = ship;
        this.sailors = sailors;
        this.ordersAction = new ArrayList<>();
        this.usefulEntity = new ArrayList<>();
        this.endExecution = false;
        this.round = 0;
        this.logicRotation = new LogicRotation(this.ship);
    }

    /**
     * Main constructor
     * @param game is used to set all parameters
     */
    public Captain(Game game){
        this(game.getShip(),game.getSailors());

        this.game = game;
    }

    /**
     * Method that will create actions of sailors, add actions to the action list
     * depending on captain logic.
     */
    public void execution(){
        if (!endExecution && game.getGoal().getCurrentCheckpoint() != null) {
            if (game.getGoal().getCurrentCheckpoint().isOnCheckpoint(ship.getPosition()) && (!game.getGoal().getNextCheckpoint())) {
                endExecution = true;
            }
            logicRotation.computeRotationData(game.getGoal().getCheckpoint(ship.getPosition(), game.getMap()));
            Cockpit.addLog("Angle : " + logicRotation.getAngle() + " ( " + radToDeg(logicRotation.getAngle()) + " degrees) to be aligned checkpoint : " + game.getGoal().getIndexCurrentCheckpoint());

            generateUsefulEntity();

            everyOneAtHisPlace();

            if (everyoneReachedTheEntity(usefulEntity)){
                usefulEntity.forEach(entity -> ordersAction.add(getAction(entity)));
            }
            round++;
        }
    }

    /**
     * Generate all entities that'll be used on the boat
     */
    public void generateUsefulEntity(){
        new OrderWatch(ship, round, getSailors().length);
        new OrderRudder(ship, logicRotation, getSailors().length);
        new OrderSail(ship, game.getGoal(), game.getWind(), getSailors().length);
        new OrderOar(ship,logicRotation, game.getGoal(),game.getSailors().length);
        usefulEntity.addAll(Order.getList());
        Order.clearEntities();
    }

    /**
     * Transform a radian angle in a degree angle
     * @param rad angle in radian
     * @return angle in degree
     */
    public double radToDeg(double rad) {
        return (rad * 180) / Math.PI;
    }

    /**
     * Condition to test if everyone is on the entity that he was aiming for
     * @param usefulEntity list of entity
     * @return boolean
     */
    public boolean everyoneReachedTheEntity(List<Entity> usefulEntity) {
        for (Entity entity : usefulEntity){
            Sailor s = entity.getSailor();
            if (s==null || !s.checkIfOnEntity(entity)) return false;
        }
        return true;
    }

    /**
     * Creates an action depending on the entity passed
     * @param entity Entity that will be transformed into an action
     * @return the Action as an Object
     */
    public Action getAction(Entity entity) {
        switch (entity.getType()) {
            case "oar":
                return new ActOar(entity.getSailor(), entity);
            case "rudder":
                return new ActRudder(entity.getSailor(), entity, logicRotation.getAngleRudder());
            case "sail":
                if (entity.isOpenned()){
                    return new ActLowerSail(entity.getSailor(), entity);
                } else {
                    return new ActLiftSail(entity.getSailor(), entity);
                }
            case "watch":
                return new ActWatch(entity.getSailor(), entity);
            default:
                return null;
        }
    }

    /**
     * methode that setup sailor's on the entity that need to be activated
     * note that mov can be null if there's no need to move for any sailor
     */
    public void everyOneAtHisPlace(){
        if (usefulEntity.size() > sailors.length) Cockpit.addLog(Constants.MESSAGE_ERR_MORE_ENTITY);
        List<Entity> listEntity = new ArrayList<>(usefulEntity);
        listEntity.removeIf(entity -> {
            for (Sailor sailor: sailors) {
                if (sailor.checkIfOnEntity(entity) && !entity.isFocus()){
                    entity.setFocusedBy(sailor);
                    sailor.setOnEntity(true);
                    return true;
                }
            }
            return false;
        });
        Shifting shift = new Shifting(sailors, ship.getDeck());
        List<ActMove> mov = shift.shiftSailorToUsefulEntity(listEntity);
        if(mov != null && !mov.isEmpty()){
            this.ordersAction.addAll(mov);
        }
    }

    /**
     * empty the order list
     * free all sailors
     * free all entities
     */
    public void resetOrder(){
        ordersAction.clear();
        usefulEntity.clear();
        if (sailors != null) {
            for(Sailor sailor: sailors){
                sailor.free();
                sailor.setOnEntity(false);
            }
        }
        if (ship.getEntities() != null) {
            for(Entity entity: ship.getEntities()){
                entity.liberation();
                entity.disFocusing();
            }
        }
    }


    public List<Action> getOrdersAction() {
        return ordersAction;
    }

    public void setLogicRotation(LogicRotation lr) {
        this.logicRotation = lr;
    }

    public LogicRotation getLogicRotation() {
        return logicRotation;
    }

    public List<Entity> getUsefulEntity() {
        return usefulEntity;
    }

    public void setOrdersAction(List<Action> act) {
        this.ordersAction = act;
    }

    public void setGame(Game g) {
        this.game = g;
    }

    public Sailor[] getSailors() {
        return sailors;
    }
}
