package fr.unice.polytech.si3.qgl.les_genies.game.visible_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

public class OtherShip extends VisibleEntities{

    @JsonIgnore
    public int life;

    public OtherShip(Position pos, Shape shape, int life) {
        super(pos, shape);
        this.life = life;
        setType(Constants.SHIP);
    }

    @Override
    public void setType(String type){
        this.type = type;
    }

    public int getLife() {
        return life;
    }

    @Override
    public String toString() {
        return "{"+
                "\"type\": \"ship\"" +
                ",\"position\": " + position +
                ",\"shape\": " + shape +
                ",\"life\": " + life +
                "}";
    }
}
