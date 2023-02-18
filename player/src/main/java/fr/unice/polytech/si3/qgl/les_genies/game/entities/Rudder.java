package fr.unice.polytech.si3.qgl.les_genies.game.entities;

public class Rudder extends Entity{

    public Rudder(int x,int y){
        super(x,y);
        setType("rudder");
    }

    @Override
    public String toString(){
        return "{" +
                "\"x\": " + getX() +
                ",\"y\": " + getY() +
                ",\"type\": \"" + getType() + "\"" +
                "}";
    }
}
