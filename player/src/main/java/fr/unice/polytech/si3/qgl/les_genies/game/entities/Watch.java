package fr.unice.polytech.si3.qgl.les_genies.game.entities;

public class Watch extends Entity{

    public Watch(int x,int y){
        super(x,y);
        setType("watch");
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
