package fr.unice.polytech.si3.qgl.les_genies.game.shapes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record Point(@JsonIgnore double x, @JsonIgnore double y) {

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" +
                "\"x\": " + getX() +
                ",\"y\": " + getY() +
                "}";
    }
}
