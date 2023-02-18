package fr.unice.polytech.si3.qgl.les_genies.game.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sail extends Entity {

    @JsonProperty("openned")
    private boolean openned;

    @Override
    public boolean isOpenned() {
        return openned;
    }

    /**
     * change the state of the sail, to close (false) of open (true)
     * @param b true or false
     */
    public void changeStatesSail(boolean b){
        this.openned = b;
    }

    public Sail(int x,int y,boolean openned){
        super(x,y);
        this.openned = openned;
        setType("sail");
    }

    @Override
    public String toString(){
        return "{" +
                "\"x\": " + getX() +
                ",\"y\": " + getY() +
                ",\"type\": \"" + getType() + "\"" +
                ",\"openned\": " + isOpenned() +
                "}";
    }
}
