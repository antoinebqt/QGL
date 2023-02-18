package fr.unice.polytech.si3.qgl.les_genies.game.shapes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Tool;
import java.awt.*;
import java.util.Arrays;

public class Polygone extends Shape {
    @JsonIgnore
    private final double orientation;
    @JsonIgnore
    private final Point[] vertices;
    @JsonIgnore
    private Polygon polygon;

    public Polygone(Point[] vertices){
        setType(Constants.POLYGONE);
        this.orientation = 0;
        this.vertices = vertices;
    }

    @Override
    public double getOrientation() {
        return orientation;
    }

    @Override
    public Point[] getVertices() {
        return vertices;
    }

    @Override
    public String toString(){
        return "{" +
                "\"type\": \"" + getType() + "\"" +
                ",\"vertices\": " + String.join(",", Arrays.toString(getVertices())) +
                "}";
    }

    private void createPolygon(Position positionShape){
        if(polygon == null){
            Tool tool = new Tool();
            polygon = tool.createPolygone(this, positionShape);
        }
    }

    @Override
    public boolean isOnShape(Position positionShape, Position positionShip){
        createPolygon(positionShape);
        return polygon.contains(positionShip.getX(), positionShip.getY());
    }
}
