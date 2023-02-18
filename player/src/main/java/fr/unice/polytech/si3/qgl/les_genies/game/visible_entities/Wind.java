package fr.unice.polytech.si3.qgl.les_genies.game.visible_entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Wind {

    private double orientation;

    private double strength;

    public Wind(){}

    @JsonCreator
    public Wind(@JsonProperty("orientation") double or,@JsonProperty("strength") double str){
        this.orientation = or;
        this.strength = str;
    }

    @Override
    public String toString() {
        return "NextRound{" +
                "orientation = " + orientation +
                "strength = " + strength +
                "}";
    }

    public double getOrientation() {
        return orientation;
    }

    public double getStrength() {
        return strength;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }
}
