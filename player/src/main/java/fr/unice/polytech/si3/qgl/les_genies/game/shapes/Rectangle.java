package fr.unice.polytech.si3.qgl.les_genies.game.shapes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Tool;
import java.awt.*;

public class Rectangle extends Shape {

    @JsonIgnore
    private double width;
    @JsonIgnore
    private double height;
    @JsonIgnore
    private final double orientation;

    @JsonIgnore
    private Polygon polygon;

    public Rectangle(double width,double height, double orientation){
        this.width = width;
        this.height = height;
        this.orientation = orientation;
        setType(Constants.RECTANGLE);
    }

    public Rectangle(double width,double height){
        this.width = width;
        this.height = height;
        this.orientation = 0;
        setType(Constants.RECTANGLE);
    }

    @JsonSetter
    public void setWidth(double width) {
        this.width = width;
    }

    @JsonSetter
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getOrientation() {
        return this.orientation;
    }


    @Override
    public String toString(){
        return "{" +
                "\"type\": \""+ getType() + "\"" +
                ",\"width\": " + getWidth() +
                ",\"height\": " + getHeight() +
                ",\"orientation\": " + getOrientation() +
                "}";
    }

    private void createPolygon(Position positionShape){
        if(polygon == null){
            Tool tool = new Tool();
            polygon = tool.createRectangle(this, positionShape);
        }
    }

    @Override
    public boolean isOnShape(Position positionShape, Position positionShip){
        createPolygon(positionShape);

        return polygon.contains(positionShip.getX(), positionShip.getY());
    }
}
