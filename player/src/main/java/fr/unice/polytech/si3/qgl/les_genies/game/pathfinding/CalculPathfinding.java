package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxMap;

public class CalculPathfinding {
    static double RV_COST = 1;
    static double DIAGONAL_COST = 1.4;


    private CalculPathfinding(){}
    /**
     * Calculate the cost to travel from the current node to the target node
     *
     * @param current NodeA
     * @param target NodeA
     * @return double
     */
    static double calculateCost(NodeA current, NodeA target, BoxMap targetBox) {
        double xDiff = Math.abs(current.getI() - target.getI());
        double yDiff = Math.abs(current.getJ() - target.getJ());
        double res;
        if ((xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1)) res = RV_COST;
        else if (xDiff == 1 && yDiff == 1) res = DIAGONAL_COST;
        else res = Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff,2));
        res = calculateStreamCostAugmentation(targetBox, res, getPathOrientation(current, target));

        return res;
    }

    /**
     * Return the given cost modify if the pathOrientation and streamOrientation are in the same or different direction
     *
     * @param boxMap BoxMap
     * @param currentCost double
     * @param pathOrientation double
     * @return double new Cost
     */
    static double calculateStreamCostAugmentation(BoxMap boxMap, double currentCost, double pathOrientation) {
        if (boxMap.isStream()) {
            double streamOrientation = boxMap.getStreamInfos().getOrientation();
            if ((pathOrientation < -2 || pathOrientation > 2) && (streamOrientation < -2 || streamOrientation > 2)
                    || (pathOrientation < streamOrientation + 1 && pathOrientation > streamOrientation - 1)) {
                currentCost = currentCost/(boxMap.getStreamInfos().getPower()/10);
            } else if (!(pathOrientation < streamOrientation + 1.8 && pathOrientation > streamOrientation - 1.8)) {
                currentCost = currentCost*(boxMap.getStreamInfos().getPower()/10);
            }
        }
        return currentCost;
    }

    /**
     * Calculate the orientation of NodeA target from NodeA current.
     * The orientation value returned is between -Pi and PI.
     *
     * @param current NodeA
     * @param target NodeA
     * @return double
     */
    static double getPathOrientation(NodeA current, NodeA target) {
        double i = (double) target.getI() - current.getI();
        double j = (double) target.getJ() - current.getJ(); // WARNING : j axe is reversed

        if (i == 0) return j * (Math.PI / 2);

        if (i == 1) i = 0;

        if (j == 0) return Math.abs(Math.PI * i);
        return (j * (Math.abs(Math.PI * i) + Math.PI / 2)) / 2;
    }
}
