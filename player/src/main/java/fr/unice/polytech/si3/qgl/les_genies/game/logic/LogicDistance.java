package fr.unice.polytech.si3.qgl.les_genies.game.logic;

import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

public class LogicDistance {
    private double x;
    private double y;
    private double norm;

    public LogicDistance(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getNorm() {
        return norm;
    }

    public void setDistanceBetween(Position from, Position to) {
        norm = Math.sqrt((Math.pow(from.getX()-to.getX(),2)+Math.pow(from.getY()-to.getY(),2)));
    }


}
