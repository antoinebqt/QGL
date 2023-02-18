package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

public class NodeA {
    private int i;
    private int j;
    private double finalCost;
    private double heuristic;
    private boolean solution;
    private NodeA parent;

    public NodeA(int i, int j, double heuristic) {
        this.i = i;
        this.j = j;
        this.heuristic = heuristic;
        this.solution = false;
    }

    public boolean isSolution() {
        return solution;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }

    public void setSolution(boolean solution) {
        this.solution = solution;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public double getHeuristic() {
        return heuristic;
    }

    public NodeA getParent() {
        return parent;
    }

    public void setParent(NodeA parent) {
        this.parent = parent;
    }
}
