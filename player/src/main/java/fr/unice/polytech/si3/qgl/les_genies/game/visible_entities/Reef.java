package fr.unice.polytech.si3.qgl.les_genies.game.visible_entities;

import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;

public class Reef extends VisibleEntities{

    public Reef(Position position,Shape shape){
        super(position,shape);
        setType(Constants.REEF);
    }

    @Override
    public String toString() {
        return "{"+
                "\"position\": " + position +
                ",\"type\": \"reef\"" +
                ",\"shape\": " + shape +
                "}";
    }
}
