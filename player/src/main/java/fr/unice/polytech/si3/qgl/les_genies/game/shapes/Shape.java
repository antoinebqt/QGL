package fr.unice.polytech.si3.qgl.les_genies.game.shapes;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

public class Shape {

    double zero = 0.0;

    @JsonProperty("type")
    protected String type;

    public String getType() {
        return type;
    }

    public double getRadius(){
        return zero;
    }

    public double getOrientation() {
        return zero;
    }

    public void setType(String type){
        this.type = type;
    }

    public Point[] getVertices() {
        return new Point[]{};
    }

    public double getWidth() {
        return zero;
    }

    public double getHeight() {
        return zero;
    }

    public boolean isOnShape(Position positionShape, Position positionShip) {
        return positionShape.getX() - 100 < positionShip.getX() && positionShape.getX() + 100 > positionShip.getX() && positionShape.getY() - 100 < positionShip.getY() && positionShape.getY() + 100 > positionShip.getY();
    }
}
