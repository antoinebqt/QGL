package fr.unice.polytech.si3.qgl.les_genies.tooling.moves;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Moves {
    @JsonProperty("sailorId")
    private String sailorId;
    @JsonProperty("xdistance")
    private int xdistance;
    @JsonProperty("ydistance")
    private int ydistance;
    @JsonProperty("type")
    private String type;
    @JsonProperty("rotation")
    private double rotation;

    public Moves() {}

    public String getSailorId() {
        return sailorId;
    }

    public double getRotation(){
        return rotation;
    }

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Moves{" +
                "sailorId='" + sailorId + '\'' +
                ", xdistance=" + xdistance +
                ", ydistance=" + ydistance +
                ", type='" + type + '\'' +
                ", rotation=" + rotation +
                '}';
    }
}
