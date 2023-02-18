package fr.unice.polytech.si3.qgl.les_genies.game.tools;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    private double x;
    private double y;
    private double orientation;

    @JsonCreator
    public Position(@JsonProperty("x") double x,
                    @JsonProperty("y") double y,
                    @JsonProperty("orientation") double orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public Position(double x, double y){
        this.x = x;
        this.y = y;
        this.orientation = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getOrientation() {
        return orientation;
    }

    @Override
    public String toString(){
        return "{" +
                "\"x\": " + getX() +
                ",\"y\": " + getY()  +
                ",\"orientation\": " + getOrientation() +
                "}";
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }
}
