package fr.unice.polytech.si3.qgl.les_genies.game.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Canon extends Entity {

    @JsonProperty("loaded")
    private boolean loaded;
    @JsonProperty("angle")
    private double angle;

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public double getAngleEntity() {
        return angle;
    }

    public Canon(int x,int y,boolean isLoaded,double angle){
        super(x,y);
        setType("canon");
        this.loaded = isLoaded;
        this.angle=angle;
    }

    @Override
    public String toString(){
        return "{" +
                "\"x\": " + getX() +
                ",\"y\": " + getY() +
                ",\"type\": \"" + getType() + "\"" +
                ",\"loaded\": " + isLoaded() +
                ",\"angle\": " + getAngleEntity() +
                "}";
    }
}
