package fr.unice.polytech.si3.qgl.les_genies.game.checkpoint;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.unice.polytech.si3.qgl.les_genies.game.deserializer.ShapeDeserializer;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;

public record Checkpoint(Position position,
                         Shape shape) {
    @JsonCreator
    public Checkpoint(@JsonProperty("position") Position position,
                      @JsonProperty("shape") @JsonDeserialize(using = ShapeDeserializer.class) Shape shape) {
        this.position = position;
        this.shape = shape;
    }

    public Position getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

    public boolean isOnCheckpoint(Position positionShip) {
        return shape.isOnShape(position, positionShip);
    }

    @Override
    public String toString() {
        return "Checkpoint {" +
                "position = " + position +
                ",shape = " + shape +
                '}';
    }
}
