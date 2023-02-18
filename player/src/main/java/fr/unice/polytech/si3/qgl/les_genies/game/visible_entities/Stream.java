package fr.unice.polytech.si3.qgl.les_genies.game.visible_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;

public class Stream extends VisibleEntities {

    @JsonIgnore
    private final double strength;

    public Stream(Position pos, Shape shape,double strength){
        super(pos, shape);
        this.strength = strength;
        setType(Constants.STREAM);
    }

    public double getStrength() {
        return strength;
    }

    @Override
    public String toString(){
        return "{" +
                "\"type\": \"" + getType() + "\"" +
                ",\"position\":" + getPosition() +
                ",\"shape\":" + getShape() +
                ",\"strength\": " + getStrength() +
                "}";
    }
}
