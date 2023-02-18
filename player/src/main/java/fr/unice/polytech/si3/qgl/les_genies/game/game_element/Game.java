package fr.unice.polytech.si3.qgl.les_genies.game.game_element;

import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_genies.game.map.MapGrid;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.VisibleEntities;
import fr.unice.polytech.si3.qgl.les_genies.game.actions.Action;
import fr.unice.polytech.si3.qgl.les_genies.game.crew.Captain;
import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;
import java.util.Arrays;
import java.util.List;

public class Game {

    private RegattaGoal goal;
    private final Ship ship;
    private Sailor[] sailors;
    private int shipCount;
    private List<VisibleEntities> visibleEntities;
    private final Captain captain;
    private Wind wind;
    private MapGrid map;
    private int distanceX = 0;
    private int distanceY = 0;
    private final boolean isPositive;

    public Game(InitGame init, boolean setPositive){
        this.goal = init.getGoal();
        this.ship = init.getShip();
        this.sailors = init.getSailors();
        this.shipCount = init.getShipCount();
        this.captain = new Captain(this);
        this.wind = null;
        this.isPositive = setPositive;
        if(ship!=null){
            if(this.isPositive)replaceEntitiesOnPositiveCoordinates();
            this.map = new MapGrid(80, goal.getCheckpoints());
        }
    }

    void replaceEntitiesOnPositiveCoordinates() {
        getMinCoordinates(ship.getPosition());
        for(Checkpoint c:goal.getCheckpoints()) getMinCoordinates(c.getPosition());
        for(Checkpoint c:goal.getCheckpoints()) addDistance(c.getPosition());

        actualiseShipPosition();
    }

    public void getMinCoordinates(Position position) {

        if (position.getX() < distanceX) distanceX = (int) position.getX();
        if (position.getY() < distanceY) distanceY = (int) position.getY();
    }

    private void actualiseShipPosition(){
        addDistance(ship.getPosition());
    }

    private void actualiseEntitiesPositions(){
        for(VisibleEntities v : visibleEntities) addDistance(v.getPosition());
    }

    public void addDistance(Position p) {
        p.setX(p.getX()-distanceX+1000);
        p.setY(p.getY()-distanceY+5000);
    }

    public Ship getShip(){
        return this.ship;
    }
    public List<VisibleEntities> getVisibleEntities(){
        return this.visibleEntities;
    }
    /**
     * @param goal define in Init
     */
    public void setGoal(RegattaGoal goal) {
        this.goal = goal;
    }

    /**
     * @param sailors define in Init or NextRound
     */
    public void setSailors(Sailor[] sailors) {
        this.sailors = sailors;
    }

    /**
     * @param shipCount define in init
     */
    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setVisibleEntities(List<VisibleEntities> visibleEntities) {
        this.visibleEntities = visibleEntities;
    }

    /**
     * update Game with new information get from NextRound
     *
     * @param next Object json create by the read of game state
     */
    public void updateGame(NextRound next){
        this.ship.updateShip(next.getShip());
        if(isPositive)addDistance(ship.getPosition());
        this.wind = next.getWind();
        setVisibleEntities(next.getVisibleEntities());
        actualiseEntitiesPositions();
        if (map.update(visibleEntities))
            goal.generatePathList(map, ship.getPosition());
    }

    public Sailor[] getSailors() {
        return sailors;
    }

    public RegattaGoal getGoal() {
        return goal;
    }

    /**
     * @return String of all actions
     */
    public String nextRound() {
        captain.resetOrder();
        captain.execution();
        StringBuilder actionsStr = new StringBuilder();
        for (Action action : captain.getOrdersAction()) {
            if (actionsStr.length() == 0) actionsStr.append(action.toString());
            else actionsStr.append(",").append(action.toString());
        }
        return "[" + actionsStr + "]";
    }

    public Position getCurrentCheckpointPosition(){
        return goal.getCurrentCheckpoint().getPosition();
    }

    @Override
    public String toString() {
        return "Game{" +
                "goal=" + goal +
                ", ship=" + ship +
                ", sailors=" + Arrays.toString(sailors) +
                ", shipCount=" + shipCount +
                ", visibleEntities=" + (visibleEntities==null?null:Arrays.toString(visibleEntities.toArray())) +
                ", wind=" + wind +
                '}';
    }

    public MapGrid getMap() {
        return map;
    }

    public int getDistanceX() {
        return distanceX;
    }

    public int getDistanceY() {
        return distanceY;
    }
}
