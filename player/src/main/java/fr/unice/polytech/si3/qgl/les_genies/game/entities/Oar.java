package fr.unice.polytech.si3.qgl.les_genies.game.entities;

import fr.unice.polytech.si3.qgl.les_genies.game.ship.SideOfBoat;

public class Oar extends Entity{

    private SideOfBoat side;

    public Oar(int x, int y){
        super(x,y);
        setType("oar");
        whichSide();
    }

    /**
     *  Set the side of the entity on the boat
     */
    public void whichSide(){
        this.side = (this.coordinates.getCoordinateY()==0)?SideOfBoat.BABORD:SideOfBoat.TRIBORD;
    }

    public SideOfBoat getSide() {
        return side;
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