package fr.unice.polytech.si3.qgl.les_genies.tooling.simulation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.unice.polytech.si3.qgl.les_genies.game.deserializer.VisibleEntityDeserializer;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.OtherShip;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.VisibleEntities;

import java.util.ArrayList;
import java.util.List;

public class Map {

    @JsonIgnore
    private List<VisibleEntities> visibleEntities;
    @JsonIgnore
    private Position position;
    @JsonIgnore
    private boolean watch;

    @JsonIgnore
    public void setShipPosition(Position position){this.position = position;}

    @JsonProperty("visibleEntities")
    @JsonDeserialize(using = VisibleEntityDeserializer.class)
    public void setVisibleEntities(List<VisibleEntities> visibleEntities) {
        this.visibleEntities = visibleEntities;
        System.out.println("la taille :" + visibleEntities.size());
    }

    @Override
    public String toString() {
        if(visibleEntities == null)return "map is null";
        StringBuilder text = new StringBuilder();

        List<VisibleEntities> list = getNearEntities(visibleEntities);

        for (VisibleEntities e : list) {
                if (text.length() != 0) text.append(",");
                text.append(e);
        }

        return "[" + text + "]";
    }

    private List<VisibleEntities> getNearEntities(List<VisibleEntities> visibleEntities) {
        List<VisibleEntities> list = new ArrayList<>();
        int r = 1000;
        if(watch) r = 3000;

        Circle c = new Circle(r);
        List<Position> l = getPoints(position, c.getRadius());

        for(VisibleEntities ve: visibleEntities){
            for(Position p : l){
                if(ve.getShape().isOnShape(ve.getPosition(),p)){
                    list.add(ve);
                    break;
                }
            }
        }

        list.add(new OtherShip(new Position(10,10,0), new Circle(10),100));

        return list;
    }

    /**
     * Use in the simulation
     * @param position of the shape
     * @return a list of point that is used to check if a sea entity is near enough to be seen
     */
    public List<Position> getPoints(Position position, double radius){
        List<Position> p = new ArrayList<>();

        double angle = Math.PI/20;
        for(double a = 0; a < 2*Math.PI; a+=angle){
            p.add(new Position((radius*Math.cos(a) - radius*Math.sin(a)) + position.getX(), (radius*Math.sin(a) + radius*Math.cos(a))+position.getY()));
            p.add(new Position((radius*2/3*Math.cos(a) - radius*2/3*Math.sin(a)) + position.getX(), (radius*2/3*Math.sin(a) + radius*2/3*Math.cos(a))+position.getY()));
            p.add(new Position((radius*1/3*Math.cos(a) - radius*1/3*Math.sin(a)) + position.getX(), (radius*1/3*Math.sin(a) + radius*1/3*Math.cos(a))+position.getY()));
        }
        p.add(position);
        return p;
    }

    public List<VisibleEntities> getVisibleEntities() {
        return visibleEntities;
    }

    public void setWatch(boolean b) {
        watch = b;
    }
}
