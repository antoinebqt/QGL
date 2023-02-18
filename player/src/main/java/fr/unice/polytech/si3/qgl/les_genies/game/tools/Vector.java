package fr.unice.polytech.si3.qgl.les_genies.game.tools;

import static java.lang.Math.atan2;

public class Vector {
    private final double x;
    private final double y;

    public Vector(Position from, Position to){
        x = to.getX()-from.getX();
        y = to.getY()-from.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle(){
        return atan2(y, x);
    }

    public double getDistance(){
        return Math.sqrt(x*x+y*y);
    }
}
