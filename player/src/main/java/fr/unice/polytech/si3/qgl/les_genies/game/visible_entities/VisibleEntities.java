package fr.unice.polytech.si3.qgl.les_genies.game.visible_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

/**
 * Always cast to get the subclass
 * nb : not now, indeed i have changed the way it work hehe
 */
public class VisibleEntities {
    @JsonIgnore
    protected String type;
    @JsonIgnore
    protected Shape shape;
    @JsonIgnore
    protected Position position;

    public VisibleEntities(Position pos,Shape shape){
        this.position = pos;
        this.shape = shape;
    }

    public Position getPosition() {
        return position;
    }

    public Shape getShape() { return shape; }

    public String getType() {
        return type;
    }

    void setType(String newType){
        this.type = newType;
    }
}

