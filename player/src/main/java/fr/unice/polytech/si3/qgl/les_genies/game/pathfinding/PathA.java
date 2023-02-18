package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

import fr.unice.polytech.si3.qgl.les_genies.game.map.MapGrid;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

import java.util.ArrayList;
import java.util.List;

public class PathA extends InitPathA{

    public PathA(MapGrid map, Position shipPos, Position checkpointPos) {
        super(map, shipPos, checkpointPos);
    }

    public List<NodeA> findPath() {
        process();
        List<NodeA> path = getPath();
        simplifyPath(path);
        return path;
    }

    /**
     * Update the final cost of the target node if this cost is lesser with the current node than with de previous parent
     *
     * @param current NodeA
     * @param target NodeA
     */
    void updateCostIfNeeded(NodeA current, NodeA target) {
        if (target == null || closedNodes[target.getI()][target.getJ()])
            return;

        double newFinalCost = target.getHeuristic() + current.getFinalCost() + CalculPathfinding.calculateCost(current, target, map.getBox(target));
        boolean isOpen = openSet.contains(target);

        if (!isOpen || newFinalCost < target.getFinalCost()) {
            target.setFinalCost(newFinalCost);
            target.setParent(current);

            if (!isOpen)
                openSet.add(target);
        }
    }

    /**
     * Run the algorithm A* to find a path in the graph
     */
    void process() {
        // add the start location in the prio queue
        openSet.add(graph[startI][startJ]);
        NodeA current;

        while (true) {
            current = openSet.poll();
            if (current == null)
                break;
            closedNodes[current.getI()][current.getJ()] = true;
            if (current.equals(graph[endI][endJ]))
                return;

            checkAdjacentNodes(current);
        }
    }

    /**
     * For all Node around the given current Node, update the cost if needed
     *
     * @param current NodeA
     */
    void checkAdjacentNodes(NodeA current) {
        NodeA target;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (current.getI() + i >= 0 && current.getJ() + j >= 0 && current.getI() + i < sizeGraph && current.getJ() + j < sizeGraph && !(i == 0 && j == 0)) {
                    target = graph[current.getI() + i][current.getJ() + j];
                    updateCostIfNeeded(current, target);
                }
            }
        }
    }

    /**
     * Return the path found by the execution of the algorithm.
     * The path returned is simplified to only have the important checkpoints
     *
     * @return List<NodeA>
     */
    List<NodeA> getPath() {
        List<NodeA> inversePath = new ArrayList<>();
        List<NodeA> finalPath = new ArrayList<>();

        if (closedNodes[endI][endJ]) {
            NodeA current = graph[endI][endJ];
            current.setSolution(true);

            inversePath.add(current);

            while (current.getParent() != null) {
                graph[current.getParent().getI()][current.getParent().getJ()].setSolution(true);
                inversePath.add(current.getParent());

                current = current.getParent();
            }

            for (int i = inversePath.size()-1; i >= 0; i--) {
                finalPath.add(inversePath.get(i));
            }
        }

        return finalPath;
    }


    /**
     * Simplify a List of NodeA by keeping only the BoxMap used like """junctions"""
     *    => if some NodeA follow each other in diagonal, vertical or horizontal; then juste keep the second
     *    (because the first one is ship BoxMap) and the last one of a line.
     *
     * @param nodeAList List<NodeA>
     */
    void simplifyPath(List<NodeA> nodeAList) {
        List<NodeA> tempPath = new ArrayList<>();
        int sizePath = nodeAList.size();
        int deltaI = -1;
        int deltaJ = -1;

        for (int i = 1; i < sizePath; i++) {
            if (i == 1) {
                deltaI = Math.abs(nodeAList.get(1).getI() - nodeAList.get(0).getI());
                deltaJ = Math.abs(nodeAList.get(1).getJ() - nodeAList.get(0).getJ());
                tempPath.add(nodeAList.get(i));
            } else {
                int tempDeltaI = Math.abs(nodeAList.get(i).getI() - nodeAList.get(i-1).getI());
                int tempDeltaJ = Math.abs(nodeAList.get(i).getJ() - nodeAList.get(i-1).getJ());

                if (deltaI != tempDeltaI || deltaJ != tempDeltaJ) {
                    tempPath.add(nodeAList.get(i - 1));
                    deltaI = tempDeltaI;
                    deltaJ = tempDeltaJ;
                }
            }
        }
        if(sizePath>0)tempPath.add(nodeAList.get(sizePath - 1));

        nodeAList.clear();
        nodeAList.addAll(tempPath);
    }
}
