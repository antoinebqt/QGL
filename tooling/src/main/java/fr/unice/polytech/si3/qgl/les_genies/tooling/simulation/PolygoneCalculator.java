package fr.unice.polytech.si3.qgl.les_genies.tooling.simulation;

import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Tool;

import java.awt.*;

public class PolygoneCalculator {
    private final Position position;
    private final Shape shape;

    private int[] Xs;
    private int[] Ys;
    private int size;


    public PolygoneCalculator(Position position, Shape shape) {
        this.position = position;
        this.shape = shape;
        calculPosition();
    }

    private void calculPosition(){
        Tool t = new Tool(position);
        Polygon p;
        if(shape.getType().equals(Constants.RECTANGLE)) p = t.createRectangle(shape, position);
        else p = t.createPolygone(shape, position);
        Xs = p.xpoints;
        Ys = p.ypoints;
        size = p.npoints;
    }

    public int[] getXs() {
        return Xs;
    }

    public int[] getYs() {
        return Ys;
    }

    public int getSize() {
        return size;
    }
}
