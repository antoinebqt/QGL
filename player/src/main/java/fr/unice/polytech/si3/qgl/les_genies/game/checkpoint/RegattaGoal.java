package fr.unice.polytech.si3.qgl.les_genies.game.checkpoint;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxMap;
import fr.unice.polytech.si3.qgl.les_genies.game.map.MapGrid;
import fr.unice.polytech.si3.qgl.les_genies.game.pathfinding.*;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

import java.util.*;


public class RegattaGoal {
    private final String mode;
    private final Checkpoint[] checkpoints;
    @JsonIgnore
    private Checkpoint currentCheckpoint;
    @JsonIgnore
    private int indexCurrentCheckpoint;
    @JsonIgnore
    private Checkpoint plCheckpoint = null;
    @JsonIgnore
    private final LinkedList<Checkpoint> pathCheckpointsQueue;
    @JsonIgnore
    private static final int CHECKPOINT_BOX_SIZE = 150;
    @JsonIgnore
    private final List<Checkpoint> checkpointsForDisplay;

    @JsonCreator
    public RegattaGoal(@JsonProperty("mode") String mode,
                       @JsonProperty("checkpoints") Checkpoint[] checkpoints) {
        this.mode = mode;
        this.checkpoints = checkpoints;
        this.indexCurrentCheckpoint = 0;
        this.pathCheckpointsQueue = new LinkedList<>();
        this.checkpointsForDisplay = new ArrayList<>();
        if (checkpoints.length > 0) {
            currentCheckpoint = checkpoints[0];
        }
    }

    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }

    private boolean isLastCheckpoint() {
        return checkpoints.length <= indexCurrentCheckpoint + 1;
    }

    public int getIndexCurrentCheckpoint() {
        return indexCurrentCheckpoint;
    }

    /**
     * Return the current checkpoint
     *
     * @return Checkpoint
     */
    public Checkpoint getCurrentCheckpoint() {
        return currentCheckpoint;
    }

    /**
     * Change the value of currentCheckpoint.
     * If return true : currentCheckpoint has successfully taken the value of the next checkpoint in the list
     * If return false : there is no more available checkpoint in the list. The value of currentCheckpoint will not change.
     *
     * @return boolean
     */
    public boolean getNextCheckpoint() {
        if (!isLastCheckpoint()) {
            indexCurrentCheckpoint++;
            currentCheckpoint = checkpoints[indexCurrentCheckpoint];

            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "RegattaGoal{" +
                "mode='" + mode + '\'' +
                ", checkpoints=" + Arrays.toString(checkpoints) +
                ", currentCheckpoint=" + currentCheckpoint +
                ", indexCurrentCheckpoint=" + indexCurrentCheckpoint +
                '}';
    }

    /**
     * Return the checkpoint in head of the queue.
     * If the queue is empty or the ship on the current checkpoint, then generate the new path list.
     * If in a mini checkpoint, then get next mini checkpoint in the queue
     *
     * @param shipPosition ShipPosition
     * @param map MapGrid
     * @return Checkpoint
     */
    public Checkpoint getCheckpoint(Position shipPosition, MapGrid map){
        if(pathCheckpointsQueue.isEmpty() || currentCheckpoint.getShape().isOnShape(currentCheckpoint.getPosition(),shipPosition)) {
            generatePathList(map, shipPosition);
        } else if (pathCheckpointsQueue.peek().getShape().isOnShape(pathCheckpointsQueue.peek().getPosition(), shipPosition)) {
            checkpointsForDisplay.add(pathCheckpointsQueue.poll());
            return getCheckpoint(shipPosition, map);
        }
        return pathCheckpointsQueue.peek();
    }

    /**
     * Execute the pathfinding PathA and add the result in the Checkpoints queue
     *
     * @param map MapGrid
     * @param shipPosition ShipPosition
     */
    public void generatePathList(MapGrid map, Position shipPosition){
        plCheckpoint = getCurrentCheckpoint();
        PathA pathA = new PathA(map, shipPosition, plCheckpoint.getPosition());
        List<NodeA> path = pathA.findPath();
        pathCheckpointsQueue.clear();

        for (NodeA node : path) {
            pathCheckpointsQueue.add(createCheckpoint(map.getBox(node)));
        }
        pathCheckpointsQueue.add(plCheckpoint);
    }

    /**
     * Create a new Checkpoint with the given BoxMap
     *
     * @param boxMap BoxMap
     * @return Checkpoint
     */
    Checkpoint createCheckpoint(BoxMap boxMap){
        return new Checkpoint(boxMap.getCenter(), new Circle(CHECKPOINT_BOX_SIZE*1.5));
    }

    public List<Checkpoint> getListOpti() {
        return checkpointsForDisplay;
    }

    public LinkedList<Checkpoint> getPathCheckpointsQueue() {
        return pathCheckpointsQueue;
    }
}
