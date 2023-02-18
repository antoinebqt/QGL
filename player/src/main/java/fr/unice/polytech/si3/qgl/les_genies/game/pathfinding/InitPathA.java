package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxMap;
import fr.unice.polytech.si3.qgl.les_genies.game.map.MapGrid;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

import java.util.Comparator;
import java.util.PriorityQueue;

public class InitPathA {

    final PriorityQueue<NodeA> openSet;
    final boolean[][] closedNodes;
    int startI;
    int startJ;
    int endI;
    int endJ;
    NodeA[][] graph;
    final MapGrid map;
    int sizeGraph;

    InitPathA(MapGrid map, Position shipPos, Position checkpointPos){
        this.map = map;
        openSet = new PriorityQueue<>(Comparator.comparingDouble(NodeA::getFinalCost));
        closedNodes = new boolean[map.getNbBox()][map.getNbBox()];

        // initialisation of start and end index
        initStartEnd(map, shipPos, checkpointPos);

        // initialisation of the graph
        initGraph(map);
    }

    /**
     * Init the graph with the current state of the map
     *
     * @param map MapGrid
     */
    void initGraph(MapGrid map) {
        // create graph
        graph = new NodeA[map.getNbBox()][map.getNbBox()];
        sizeGraph = map.getNbBox();

        // set NodeA or null
        for (int i = 0; i < map.getNbBox(); i++) {
            for (int j = 0; j < map.getNbBox(); j++) {
                graph[i][j] = getNodeFromBoxMap(map.getBox(i, j));
            }
        }

        // check that the node if the position is in a reef box
        checkNotNullNode(map.getBox(startI, startJ));

        // set the first value of final cost at 0
        this.graph[this.startI][this.startJ].setFinalCost(0);
    }

    /**
     * If the Node in the graph with the index of the given boxmap is null,
     * then create a node in this position od the graph
     *
     * @param boxMap BoxMap
     */
    void checkNotNullNode(BoxMap boxMap) {
        if (this.graph[boxMap.getI()][boxMap.getJ()] == null)
            this.graph[boxMap.getI()][boxMap.getJ()] = new NodeA(boxMap.getI(), boxMap.getJ(), getHeuristicCost(boxMap.getI(), boxMap.getJ()));
    }

    /**
     * Init the value of start and end index
     *
     * @param map MapGrid
     * @param shipPos shipPos
     * @param checkpointPos checkpointPos
     */
    void initStartEnd(MapGrid map, Position shipPos, Position checkpointPos) {
        BoxMap boxStart = map.getBox(shipPos);
        startI = boxStart.getI();
        startJ = boxStart.getJ();

        BoxMap boxEnd = map.getBox(checkpointPos);
        endI = boxEnd.getI();
        endJ = boxEnd.getJ();
    }



    /**
     * Return a node with i and j of the given BoxMap.
     * Return null if the BoxMap isn't free
     *
     * @param boxMap BoxMap
     * @return NodeA or null
     */
    NodeA getNodeFromBoxMap(BoxMap boxMap) {
        if (boxMap.getStatus())
            return new NodeA(boxMap.getI(), boxMap.getJ(), getHeuristicCost(boxMap.getI(), boxMap.getJ()));
        else
            return null;
    }

    /**
     * Return the heuristic cost between given index and end index
     *
     * @param i int
     * @param j int
     * @return double
     */
    double getHeuristicCost(int i, int j) {
        return (double) Math.abs(i - endI) + Math.abs(j - endJ);
    }

    NodeA[][] getGraph() {
        return this.graph;
    }

    boolean[][] getClosedNodes() {
        return this.closedNodes;
    }

    PriorityQueue<NodeA> getOpenSet() {
        return this.openSet;
    }
}
