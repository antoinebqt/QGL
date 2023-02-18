package fr.unice.polytech.si3.qgl.les_genies.game.shapes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

public class Circle extends Shape {

    @JsonIgnore
    private double radius;

    public Circle(double radius){
        setType(Constants.CIRCLE);
        this.radius = radius;
    }

    @JsonSetter
    public void setRadius(double theRadius) {
        this.radius = theRadius;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public boolean isOnShape(Position positionShape, Position positionShip) {
        return Math.pow(positionShape.getX() - positionShip.getX(), 2)
                + Math.pow(positionShape.getY() - positionShip.getY(), 2)
                < Math.pow(radius, 2);
    }

    @Override
    public String toString(){
        return "{" +
                "\"type\": \"" + getType() + "\"" +
                ",\"radius\": " + getRadius() +
                "}";
    }
}
